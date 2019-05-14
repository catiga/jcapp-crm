package com.jeancoder.crm.internal.token

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService

def token = JC.internal.param('token');
def pid = JC.internal.param('pid');

AccountSession session = SessionService.INSTANCE().flush_session(token, 0, pid);
if(!session) {
	return SimpleAjax.notAvailable('user_not_login,请重新登录');
}

GeneralUserService user_service = GeneralUserService.INSTANCE();

AccountThirdBind third = user_service.get_third_account_by_id(pid, session.ap_id);
if(third) {
	session.part_id = third.part_id;
}
if(session.basic_id) {
	GeneralUser basic_user = user_service.get(session.basic_id);
	session.mobile = basic_user.mobile;		//增加返回手机号码
}
return SimpleAjax.available('', session);