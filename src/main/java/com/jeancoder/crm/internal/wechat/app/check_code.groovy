package com.jeancoder.crm.internal.wechat.app

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.ProjectFrontConfig
import com.jeancoder.crm.ready.dto.h5.AccountInfoDto
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService

import groovy.json.JsonSlurper

def code = JC.internal.param('code');
def pid = JC.internal.param('pid');

JCLogger logger = LoggerSource.getLogger();

pid = new BigInteger(pid + '');

//获取项目支付信息配置，对应查找小程序的配置信息
ProjectFrontConfig supp_config = JC.internal.call(ProjectFrontConfig, 'project', '/incall/frontconfig', [pid:pid, app_type:'40']);
logger.info('check_code: pid=' + pid + ', and config=' + supp_config);
if(supp_config==null) {
	return SimpleAjax.notAvailable('front_config_error');
}
def app_id = supp_config.app_id;
def app_secret = supp_config.app_key;
def url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + app_id + "&secret=" + app_secret + "&js_code=" + code + "&grant_type=authorization_code"

String json = JC.remote.http_call(url, null);
println json;

def jsonSlurper = new JsonSlurper()

//获取到的是Map对象
def map = jsonSlurper.parseText(json)

def errcode = map['errcode'];
def errmsg = map['errmsg'];
def access_token = map['access_token'];
def openid = map['openid'];
def unionid = map['unionid'];
def scope = map['scope'];
def session_key = map['session_key'];

if(errcode!=null) {
	return SimpleAjax.notAvailable(errcode + ":" + errmsg);
}

if(openid==null) {
	return SimpleAjax.notAvailable('openid_get_error,未获取到授权信息，请退出重试');
}

def info_map = null;
println 'scope======' + scope;
if(scope=='snsapi_userinfo') {
	//开始获取用户微信资料
	def get_info_url = 'https://api.weixin.qq.com/sns/userinfo?access_token=' + access_token + '&openid=' + openid + '&lang=zh_CN';
	def info_json = JC.remote.http_call(get_info_url, null);
	println 'json_info===' + info_json;
	info_map = jsonSlurper.parseText(info_json);
}

GeneralUserService gu_service = GeneralUserService.INSTANCE();

AccountThirdBind third_bind = gu_service.get_third_account(app_id, openid, unionid, pid);
if(third_bind==null) {
	return SimpleAjax.notAvailable('param_error,初始化账户信息失败，请重试');
}

AccountInfoDto dto = null;

//开始构建微信资料信息
if(info_map!=null) {
	GeneralUserService user_service = GeneralUserService.INSTANCE();
	AccountInfo account = new AccountInfo();
	account.head = info_map['headimgurl'];
	account.nickname = info_map['nickname'];
	//account.sex = info_map['sex'];
	user_service.update_account_info(third_bind.id, account);
	dto = new AccountInfoDto(account);
} else {
	dto = new AccountInfoDto(null);
	dto.ap_id = third_bind.id;
	dto.part_id = third_bind.part_id;
}
	
//开始构建登录信息
if(third_bind.account_id==null) {
	//操作成功需要初始化账户
	dto = new AccountInfoDto(null);
	dto.ap_id = third_bind.id;
	dto.part_id = third_bind.part_id;
	dto.session_key = session_key;
	return SimpleAjax.available('', dto);
}

GeneralUser gu = gu_service.get(third_bind.account_id);

def validate_period = 15*24*60*60*1000l; //默认有效期 15天

SessionService session_service = SessionService.INSTANCE();
AccountSession session = session_service.login_session(gu.mobile, gu.password, third_bind, validate_period, "0", pid);

dto.token = session.token;
dto.session_key = session_key;
dto.mobile = gu.mobile;

return SimpleAjax.available('', dto);




