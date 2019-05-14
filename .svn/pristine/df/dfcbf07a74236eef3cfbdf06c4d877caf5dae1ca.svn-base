package com.jeancoder.crm.entry.h5.ques

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.crm.ready.service.QuesService
import com.jeancoder.crm.ready.service.SessionService


def app_id = 'wxd0569da27f196643';
def back_url = 'http://m.piaodaren.com/crm/h5/sys/check_code';

//首先判断用户的登录状态
JCRequest request = JC.request.get();
def token = null;
request.getCookies()?.each({
	if(it.name=='_lac_k_') {
		token = it.value;
		return;
	}
});

AccountSession session = token==null?null:SessionService.INSTANCE().flush_session(token, 0);
if(session==null) {
	//需要重定向
	back_url = URLEncoder.encode(back_url, "UTF-8");
	def redirect_url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + app_id + '&redirect_uri=' + back_url + '&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
	ResponseSource.getResponse().sendRedirect(redirect_url);
	return;
}


QuesService ques_service = QuesService.INSTANCE();

Questionnaire pack = ques_service.get_questionnaire(1);

Result view = new Result();
view.setView('h5/ques/index');
if(pack!=null) {
	//return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
	
	def ques_items = ques_service.get_ques_items(pack);
	view.addObject('pack', pack);
	view.addObject('items', ques_items);
}
return view;

//return SimpleAjax.available('', ques_items);



