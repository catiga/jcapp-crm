package com.jeancoder.crm.entry.h5.doc

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.dto.WxJsObj
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.DocReader
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.crm.ready.service.QuesService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.EncodeUtils
import com.jeancoder.crm.ready.util.MD5Util
import com.jeancoder.jdbc.JcTemplate


def app_id = 'wx18ca8d88c8070ff4';
def gucci_key = 'h5201711';
def back_url = 'http://gucci.92youpin.com/crm/h5/doc/check_code';

def _sdf_ = new SimpleDateFormat('yyyyMMddHHmmssSSS');

//首先判断用户的登录状态
JCRequest request = JC.request.get();
def token = null;
def openid = null;
request.getCookies()?.each({
	if(it.name=='_lac_k_') {
		token = it.value;
	} else if(it.name=='_lac_op_') {
		openid = it.value;
	}
});

AccountSession session = token==null?null:SessionService.INSTANCE().flush_session(token, 0);
if(session==null) {
	//需要重定向
	back_url = URLEncoder.encode(back_url, "UTF-8");
	//def redirect_url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + app_id + '&redirect_uri=' + back_url + '&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
	
	//计算签名
	def timestamp = _sdf_.format(Calendar.getInstance().getTime());
	def sign = MD5Util.getMD5(gucci_key + '|' + timestamp).toLowerCase();
	def redirect_url = 'http://gucciwx.nedigitals.com/weixin/oauth/api_weixin_login.jsp?callback_url=' + back_url + '&timestamp=' + timestamp + '&sign=' + sign;
	
	ResponseSource.getResponse().sendRedirect(redirect_url);
	return;
}


QuesService ques_service = QuesService.INSTANCE();

Questionnaire pack = ques_service.get_questionnaire(1);

Result view = new Result();
view.setView('h5/doc/index');
if(pack!=null) {
	def ques_items = ques_service.get_ques_items(pack);
	view.addObject('pack', pack);
	view.addObject('items', ques_items);
}

//开始获取jsticket
def timestamp = _sdf_.format(Calendar.getInstance().getTime());
def sign = MD5Util.getMD5(gucci_key + '|' + timestamp).toLowerCase();
def jsticket_url = 'http://gucciwx.nedigitals.com/weixin/api/api_jsapi_ticket.jsp?timestamp=' + timestamp + '&sign=' + sign;
println jsticket_url;
def jsapi_ticket = JC.remote.http_call(jsticket_url, null);
println jsapi_ticket;
def s = jsapi_ticket.trim();
s = s.trim().substring(s.indexOf('ticket')+'ticket'.length());
StringBuffer buff = new StringBuffer();
def start = false;
for (int i=0; i<s.toString().length(); i++) {
	if(s.charAt(i).toString()!=':'&&s.charAt(i).toString()!=''&&s.charAt(i).toString()!='"') {
		buff.append(s.charAt(i));
		start = true;
	}
	if(start&&s.charAt(i+1).toString()=='"') {
		break;
	}
}
println buff.toString();
jsapi_ticket = buff.toString();


String noncestr = MD5Util.getMD5(new Random().nextFloat() + "");
String ts = System.currentTimeSeconds() + "";
String url = request.getRequestURL().toString();
String param = request.getQueryString();

if(param!=null) {
	url = url + "?" + param;
}
String original_str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + ts + "&url=" + url;
String sign_str = EncodeUtils.SHA1(original_str);

WxJsObj js_obj = new WxJsObj(app_id, ts, noncestr, sign_str);
view.addObject('js_obj', js_obj);

println 'openid=' + openid;

//保存读取记录
DocReader doc_read = new DocReader();
doc_read.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
doc_read.ap_id = session?.ap_id;
doc_read.doc_id = BigInteger.valueOf(0L);
doc_read.flag = 0;
doc_read.ip_addr = JC.request.get().getRemoteHost();
doc_read.user_id = session?.basic_id;
doc_read.user_key = openid;
doc_read.app_id = app_id;
JcTemplate.INSTANCE().save(doc_read);

return view;




