package com.jeancoder.crm.entry.h5.sys

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.idream.DPCODE
import com.jeancoder.crm.ready.idream.DreamProtocol
import com.jeancoder.crm.ready.idream.SecuritySandbox
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.jdbc.JcTemplate



def pid = RemoteUtil.getProj().id;

def p = JC.request.param('p')?.trim();
def code = JC.request.param('code')?.trim();

def apid = JC.request.param('apid')?.trim();

if(p.length()!=11) {
	return SimpleAjax.notAvailable('手机格式错误');
}

//MemPower mem_power = MemSource.getMemPower();
//
//def phone_code = mem_power.get(p);
//if(!phone_code||code!=phone_code) {
//	return SimpleAjax.notAvailable('验证码错误');
//}
//mem_power.delete(p);
//调用创梦登录接口
DreamProtocol dpinst = new SecuritySandbox();
DPCODE ret_code = dpinst.login(p, code);
if(ret_code.code!=0) {
	println 'fail' + ret_code.code + ',' + ret_code.desc;
	return SimpleAjax.notAvailable('验证码错误');
}


SessionService session_service = SessionService.INSTANCE();
GeneralUserService user_service = GeneralUserService.INSTANCE();

GeneralUser user = user_service.init_account(p, '99881122678');
if(user==null) {
	return SimpleAjax.notAvailable('账户创建失败，请重试');
}

AccountThirdBind third = user_service.get_third_account_by(GlobalHolder.proj.id, user.id);
if(third==null) {
	//只有这种情况执行重新绑定操作
	third = user_service.get_third_account_by_id(GlobalHolder.proj.id, apid);
	if(third==null) {
		return SimpleAjax.notAvailable('账户初始化失败，请重试');
	}
	if(third.account_id!=null) {
		return SimpleAjax.notAvailable('账户安全检查失败，请重试');
	}
	third.account_id = user.id;
	JcTemplate.INSTANCE().update(third);
	AccountInfo account = new AccountInfo(); 
//	account.info = ret_code.data['message'];
//	account.head = ret_code.data['avatar_url'];
//	account.nickname = ret_code.data['nickname'];
//	account.realname = ret_code.data['real_name'];
	account.sex = 0;
	account.user_no = ret_code.data['uid'] + '';
	user_service.update_account_info(third.id, account);
}
def validate_period = 15*24*60*60*1000l; //默认有效期 15天

AccountSession session = session_service.login_session(user.mobile, user.password, third, validate_period, "0");

try {
	AccountProjectMcService.INSTANSE.updateByMobile(user.mobile,session.ap_id,pid);
}catch(Exception e) {
	e.printStackTrace()
}

return SimpleAjax.available('', session.token);
