package com.jeancoder.crm.internal.token

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.h5.AccountInfoDto
import com.jeancoder.crm.ready.entity.AccountBasic
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.jdbc.JcTemplate

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

AccountInfo info = JcTemplate.INSTANCE().get(AccountInfo, 'select * from AccountInfo where ap_id=?', session.ap_id);

AccountInfoDto dto = new AccountInfoDto(info)
AccountBasic basic = JcTemplate.INSTANCE().get(AccountBasic.class, 'select * from AccountBasic where id=?', session.basic_id);
if (basic != null) {
	dto.mobile = basic.mobile;
	if(basic.mobile) {
		//寻找是否为当前系统的管理用户手机号
		SimpleAjax admin_user_result = JC.internal.call(SimpleAjax, 'project', '/auth/user_by_phone', [phone:dto.mobile,pid:third.pid]);
		if(admin_user_result.available) {
			dto.mt = admin_user_result.data;
		}
	}
}
dto.pid = session.proj_id;

return SimpleAjax.available('', [session, dto]);