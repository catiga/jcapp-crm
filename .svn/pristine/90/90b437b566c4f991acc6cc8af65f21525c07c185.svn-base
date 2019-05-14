package com.jeancoder.crm.entry.h5.gucci;

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.QuestionChoise
import com.jeancoder.crm.ready.entity.QuestionItem
import com.jeancoder.crm.ready.entity.QuestionResult
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.crm.ready.service.QuesService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MD5Util


//首先判断用户的登录状态
JCRequest request = JC.request.get();
def token = null;
request.getCookies()?.each({
	if(it.name=='_lac_k_') {
		token = it.value;
		return;
	}
});

def app_id = 'wx18ca8d88c8070ff4';
def gucci_key = 'h5201711';
def back_url = 'http://gucci.92youpin.com/crm/h5/gucci/check_code';
def _sdf_ = new SimpleDateFormat('yyyyMMddHHmmssSSS');

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

if(pack==null) {
	return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
}

List<QuestionItem> items = ques_service.get_ques_items(pack);
if(items==null||items.empty) {
	return SimpleAjax.notAvailable('obj_not_found,问卷尚未设置问题');
}

List<QuestionChoise> item_choises = ques_service.get_ques_choise(pack.id);

/**
 *	1050:1050|哈哈哈;1051:5472|star;
 */
def answ = JC.request.param('answ')?.trim()?.split(';');

println 'answ======' + answ;

def latitude = JC.request.param('latitude')?.trim();
def longitude = JC.request.param('longitude')?.trim();

// 
def result = [];

for(x in answ) {
	def ans_item = x.split(':');
	QuestionItem real_item = null;
	
	for(QuestionItem item : items) {
		if(ans_item[0].toString().trim()==item.id.toString().trim()) {
			real_item = item; break;
		}
	}
	if(real_item==null) { continue; }
	
	def ans_item_1 = ans_item[1]?.trim();
	println ans_item_1;
	println ans_item_1.indexOf("|");
	def choise_id = ans_item_1.substring(0, ans_item_1.indexOf("|"));
	def choise_value = ans_item_1.substring(ans_item_1.indexOf("|") + 1);
	
	QuestionResult ques_answer = new QuestionResult();
	ques_answer.longitude = longitude;
	ques_answer.latitude = latitude;
	
	println ans_item_1 + '=====' + ans_item_1.indexOf("|") + '-----' + choise_id + '------------' + choise_value;
	if(real_item.qt=='10') {
		//文本
		ques_answer.choise_value = choise_value;
	} else if(real_item.qt=='21') {
		//单选
		QuestionChoise choise = null;
		for(qc in item_choises) {
			if(qc.id.toString()==choise_id) {
				choise = qc; break;
			}
		}
		if(choise==null) continue;
		
		ques_answer.choise_id = choise.id;
		ques_answer.choise_value = choise.awname;
	} else if(real_item.qt=='22') {
		//多选
		//TODO 目前没有多选情况
	}
	
	ques_answer.item_id = real_item.id;
	ques_answer.item_name = real_item.question;
	ques_answer.pack_id = pack.id;
	ques_answer.user_id = session.ap_id;
	
	if(!(ques_answer.choise_id==null&&ques_answer.choise_value==null)) {
		println JackSonBeanMapper.toJson(ques_answer);
		ques_service.save_ques_result(ques_answer);
	}
}
return SimpleAjax.available();



