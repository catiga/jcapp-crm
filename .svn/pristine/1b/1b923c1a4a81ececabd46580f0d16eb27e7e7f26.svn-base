package com.jeancoder.crm.entry.h5.gucci_with_us

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.JsapiTicket
import com.jeancoder.crm.ready.dto.WxAccessToken
import com.jeancoder.crm.ready.dto.WxJsObj
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.crm.ready.service.QuesService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.EncodeUtils
import com.jeancoder.crm.ready.util.MD5Util


def app_id = 'wxd0569da27f196643';
def app_secret = 'b460fed551021499df11b228960353e0';
def back_url = 'http://gucci.92youpin.com/crm/h5/gucci/check_code';

println app_id + '======' + app_secret;

//首先判断用户的登录状态
JCRequest request = JC.request.get();
//def token = null;
//def openid = null;
//request.getCookies()?.each({
//	if(it.name=='_lac_k_') {
//		token = it.value;
//	} else if(it.name=='_lac_op_') {
//		openid = it.value;
//	}
//});

def token = JC.request.param('token')?.trim();
def openid = JC.request.param('openid')?.trim();

AccountSession session = token==null?null:SessionService.INSTANCE().flush_session(token, 0);
if(session==null) {
	//需要重定向
	back_url = URLEncoder.encode(back_url, "UTF-8");
	def redirect_url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + app_id + '&redirect_uri=' + back_url + '&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect';
	
	ResponseSource.getResponse().sendRedirect(redirect_url);
	return;
}


QuesService ques_service = QuesService.INSTANCE();

Questionnaire pack = ques_service.get_questionnaire(1);

Result view = new Result();
view.setView('h5/gucci/index');
if(pack!=null) {
	def ques_items = ques_service.get_ques_items(pack);
	view.addObject('pack', pack);
	view.addObject('items', ques_items);
}

//开始jsticket

def _access_token_ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + app_id + "&secret=" + app_secret;
WxAccessToken access_token = JC.remote.http_call(WxAccessToken, _access_token_, null);
if(access_token==null) {
	return SimpleAjax.notAvailable('wx_init_error');
}

def _jsapi_ticket_ = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token.access_token + "&type=jsapi";
JsapiTicket js_ticket_obj = JC.remote.http_call(JsapiTicket, _jsapi_ticket_, null);
def jsapi_ticket = js_ticket_obj.ticket;

String noncestr = MD5Util.getMD5(new Random().nextFloat() + "");
String ts = System.currentTimeSeconds() + "";
String url = request.getRequestURL().toString();
String param = request.getQueryString();

if(param!=null) {
	url = url + "?" + param;
}
println url;
String original_str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + ts + "&url=" + url;

println 'original_str=====' + original_str;
String sign_str = EncodeUtils.SHA1(original_str);
println 'sign_str=' + sign_str;

WxJsObj js_obj = new WxJsObj(app_id, ts, noncestr, sign_str);
view.addObject('js_obj', js_obj);

view.addObject('openid', openid);
view.addObject('token', token);

//目标
def aim_openid = 'o-0NLswCGGbqU4VoiG2GHdBdnpGc';		//真的

if(openid==aim_openid) {
	//需要进行特殊数据获取
	view.addObject('ne', '1');
} else {
	view.addObject('ne', '0');
}

println 'current visit user openid=' + openid + ', and aim_openid=' + aim_openid;

return view;




