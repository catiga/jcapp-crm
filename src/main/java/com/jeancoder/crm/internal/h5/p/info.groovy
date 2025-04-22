package com.jeancoder.crm.internal.h5.p

import com.alibaba.druid.stat.JdbcStatManager
import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.h5.AccountInfoDto
import com.jeancoder.crm.ready.entity.AccountBasic
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.service.AccountBasicService
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate

def token = JC.internal.param('token')?.toString()?.trim();
def pid = JC.internal.param('pid');

if(token==null) {
	return SimpleAjax.notAvailable('token_invalid');
}
SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.flush_session(token, 0, pid);
if(session==null) {
	return SimpleAjax.notAvailable('token_invalid');
}

BigInteger ap_id = session.ap_id;
AccountInfo info = JcTemplate.INSTANCE().get(AccountInfo, 'select * from AccountInfo where ap_id=?', ap_id);
AccountInfoDto dto = new AccountInfoDto(info)

//if(info==null) {
//	return SimpleAjax.available();
//}

AccountThirdBind bind = JcTemplate.INSTANCE().get(AccountThirdBind, 'select * from AccountThirdBind where id=?', ap_id);
if(bind!=null) {
	//info.part_id = bind.part_id;
	dto.part_id = bind.part_id;
}
dto.ap_id = ap_id;

AccountBasic basic = JcTemplate.INSTANCE().get(AccountBasic.class, 'select * from AccountBasic where id=?', session.basic_id);
if (basic != null) {
	dto.mobile = basic.mobile;
}
dto.pid = session.proj_id;

try {
	// 绑定会员卡
	if (dto.mobile != null) {
		AccountProjectMcService.INSTANSE.updateByMobile(dto.mobile,session.ap_id,new BigInteger(pid.toString()));
	}
}catch(Exception e) {
	e.printStackTrace()
}
return SimpleAjax.available('', dto);


