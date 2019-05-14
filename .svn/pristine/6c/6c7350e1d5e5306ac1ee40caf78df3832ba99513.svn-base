package com.jeancoder.crm.entry.h5.gucci_with_us

import javax.servlet.http.Cookie

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService

import groovy.json.JsonSlurper

def code = JC.request.param('code')?.trim();

def app_id = 'wxd0569da27f196643';
def app_secret = 'b460fed551021499df11b228960353e0';

def url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+app_id+"&secret="+app_secret+"&code="+code+"&grant_type=authorization_code";

String json = JC.remote.http_call(url, null);
println 'url=' + url;
println 'json=' + json;
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


if(openid==null) {
	return SimpleAjax.notAvailable('未获取到授权信息，请重试');
}

GeneralUserService gu_service = GeneralUserService.INSTANCE();
AccountThirdBind third_bind = gu_service.get_third_account(app_id, openid, null);
if(third_bind==null) {
	return SimpleAjax.notAvailable('未获取到授权信息，请退出重试');
}
/** 不需要登录
if(third_bind.account_id==null) {
	//操作成功需要初始化账户
	return SimpleAjax.available('init_success');
}
//开始构建登录信息
GeneralUser gu = gu_service.get(third_bind.account_id);
if(gu==null) {
	//操作成功需要初始化账户
	return SimpleAjax.available('init_success');
}
**/

def validate_period = 15*24*60*60*1000l; //默认有效期 15天

SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.login_session(null, null, third_bind, validate_period, "0");

Cookie cookie = new Cookie('_lac_k_', session.token);
cookie.setPath('/');
ResponseSource.getResponse().addCookie(new JCCookie(cookie))

Cookie _op_cookie_ = new Cookie('_lac_op_', openid);
_op_cookie_.setPath('/');
ResponseSource.getResponse().addCookie(new JCCookie(_op_cookie_))

ResponseSource.getResponse().sendRedirect('/crm/h5/gucci/index?token=' + session.token + '&openid=' + openid);


