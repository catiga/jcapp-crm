package com.jeancoder.crm.internal.h5.p

import javax.servlet.http.Cookie

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.ProjectFrontConfig
import com.jeancoder.crm.ready.dto.h5.AccountInfoDto
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.GlobalHolder

import groovy.json.JsonSlurper

JCLogger logger = JCLoggerFactory.getLogger('');
def code = JC.internal.param('code')?.toString()?.trim();
def domain = JC.internal.param('domain');
def type = JC.internal.param('type');

//获取项目支付信息配置
SimpleAjax supp_config = JC.internal.call(SimpleAjax, 'project', '/incall/frontconfig_by_domain', [domain:domain, app_type:'20']);
if(!supp_config.available) {
	return supp_config;
}
def pid = supp_config.data['project_id'];
def app_id = supp_config.data['app_id'];
def app_secret = supp_config.data['app_key'];
def url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+app_id+"&secret="+app_secret+"&code="+code+"&grant_type=authorization_code";

String json = JC.remote.http_call(url, null);

/**
 * wrong json format
 * {"errcode":40029,"errmsg":"invalid code, hints: [ req_id: JXWgvA05584679 ]"}
 */

/**
 * {"access_token":"12_xsZCBRftgMPujiwdwg-K9Ah0vQYPt2L51HnqEafyLjjn_WAQ_fCGbWubDPjHUaAH6uKNr5tc20ETf0KPjcM4bw",
 * "expires_in":7200,
 * "refresh_token":"12_kiOM9P0-ixWbdX7ZeqOzVfi7Log76J5Ev7EIkWsEGpMa-IDIdV-AI0Io4ZOhte1x1et1XahQd71bpyFpFlJZEQ",
 * "openid":"o-0NLs9Mw76UbYJmb_AtaKeRnMTc",
 * "scope":"snsapi_userinfo",
 * "unionid":"oQmSgjhXJdu-SaRUeRqu-HtrSm2Q"}
 */

def jsonSlurper = new JsonSlurper()

//获取到的是Map对象
def map = jsonSlurper.parseText(json)

def errcode = map['errcode'];
def errmsg = map['errmsg'];
def access_token = map['access_token'];
def openid = map['openid'];
def unionid = map['unionid'];
def scope = map['scope'];

if(errcode!=null) {
	return SimpleAjax.notAvailable(errcode + "," + errmsg);
}

if(openid==null) {
	return SimpleAjax.notAvailable('param_error,未获取到授权信息，请退出重试');
}

def info_map = null;

if(scope=='snsapi_userinfo') {
	//开始获取用户微信资料
	def get_info_url = 'https://api.weixin.qq.com/sns/userinfo?access_token=' + access_token + '&openid=' + openid + '&lang=zh_CN';
	def info_json = JC.remote.http_call(get_info_url, null);
	
	info_map = jsonSlurper.parseText(info_json);
}

GeneralUserService gu_service = GeneralUserService.INSTANCE();
AccountThirdBind third_bind = gu_service.get_third_account(app_id, openid, unionid, pid);
if(third_bind==null) {
	return SimpleAjax.notAvailable('param_error,初始化账户信息失败，请重试');
}

AccountInfoDto dto = null;

//if(third_bind.account_id==null) {
//	//操作成功需要初始化账户
//	dto = new AccountInfoDto(null);
//	dto.ap_id = third_bind.id;
//	dto.part_id = third_bind.part_id;
//	return SimpleAjax.available('', dto);
//}

//开始构建微信资料信息
if(info_map!=null) {
	GeneralUserService user_service = GeneralUserService.INSTANCE();
	AccountInfo account = new AccountInfo();
	account.head = info_map['headimgurl'];
	account.nickname = info_map['nickname'];
	account.sex = info_map['sex'];
	if(!account.sex) {
		account.sex = 0;	//设置为未知
	}
	try {
		account.nickname = filterEmoji(account.nickname, '');
		account = user_service.update_account_info(third_bind.id, account);
	}catch(any) {
		account.nickname = '未设置';
		account = user_service.update_account_info(third_bind.id, account);
	}
	dto = new AccountInfoDto(account);
} else {
	dto = new AccountInfoDto(null);
	dto.ap_id = third_bind.id;
	dto.part_id = third_bind.part_id;
}
	
//开始构建登录信息
//在需要构建账户并且未构建情况
logger.info('type======' + type + ', ness create base account?');
if(type=='account' && third_bind.account_id==null) {
	//操作成功需要初始化账户
	dto = new AccountInfoDto(null);
	dto.ap_id = third_bind.id;
	dto.part_id = third_bind.part_id;
	return SimpleAjax.available('', dto);
}

GeneralUser gu = gu_service.get(third_bind.account_id);
def validate_period = 15*24*60*60*1000l; //默认有效期 15天

def basic_name = null;
def basic_pass = null;
if(gu!=null) {
	basic_name = gu.mobile;
	basic_pass = gu.password;
}

SessionService session_service = SessionService.INSTANCE();
AccountSession session = session_service.login_session(basic_name, basic_pass, third_bind, validate_period, "0", pid);

dto.token = session.token;
return SimpleAjax.available('', dto);

def filterEmoji(String source,String slipStr) {
	if(source){
		return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
	}else{
		return source;
	}
}


