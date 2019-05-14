package com.jeancoder.crm.entry.h5.gucci;

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

def openid = JC.request.param('openid')?.trim();

def app_id = 'wx18ca8d88c8070ff4';

if(openid==null) {
	return SimpleAjax.notAvailable('未获取到授权信息，请退出重试');
}

GeneralUserService gu_service = GeneralUserService.INSTANCE();
AccountThirdBind third_bind = gu_service.get_third_account(app_id, openid, null);
if(third_bind==null) {
	return SimpleAjax.notAvailable('初始化账户信息失败，请重试');
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
//AccountSession session = session_service.login_session(gu.mobile, gu.password, third_bind, validate_period, "0");

AccountSession session = session_service.login_session(null, null, third_bind, validate_period, "0");

//return SimpleAjax.available('', session.token);

Cookie cookie = new Cookie('_lac_k_', session.token);
cookie.setPath('/');
//cookie.setMaxAge(15*24*60*60);
ResponseSource.getResponse().addCookie(new JCCookie(cookie))

Cookie _op_cookie_ = new Cookie('_lac_op_', openid);
_op_cookie_.setPath('/');
//_op_cookie_.setMaxAge(15*24*60*60);
ResponseSource.getResponse().addCookie(new JCCookie(_op_cookie_))

ResponseSource.getResponse().sendRedirect('/crm/h5/gucci/index');


