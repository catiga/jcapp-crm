package com.jeancoder.crm.internal.wechat.app

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.MemPower
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.jdbc.JcTemplate

def p = JC.internal.param('p')?.toString()?.trim();
def ap_id = JC.internal.param('ap_id');
def pid = JC.internal.param('pid');
def part_id = JC.internal.param('part_id');

if(p==null||p.length()!=11) {
	return SimpleAjax.notAvailable('param_error,手机格式错误');
}

GeneralUserService user_service = GeneralUserService.INSTANCE();

AccountThirdBind third = user_service.get_third_account_by_single_id(ap_id);
if(third==null||third.part_id!=part_id) {
	return SimpleAjax.notAvailable('secruity_error,数据安全匹配失败');
}


GeneralUser user = user_service.init_account(p, '99881122678');
if(user==null) {
	return SimpleAjax.notAvailable('account_init_error,账户创建失败，请重试');
}

third.account_id = user.id;
JcTemplate.INSTANCE().update(third);

AccountInfo account = new AccountInfo(); 
account.sex = 0;
user_service.update_account_info(third.id, account);

def validate_period = 15*24*60*60*1000l; //默认有效期 15天

SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.login_session(user.mobile, user.password, third, validate_period, "0", pid);

try {
	// 所有会员卡的pid 均为1
	AccountProjectMcService.INSTANSE.updateByMobile(user.mobile,session.basic_id,1);
}catch(any) {
}
if(session==null) {
	return SimpleAjax.notAvailable('secruity_error,会话创建失败，请重试');
}

return SimpleAjax.available('', session.token);

