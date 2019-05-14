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
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate

def apid = JC.internal.param('apid')?.toString()?.trim();

BigInteger ap_id = new BigInteger(apid);
AccountInfo info = JcTemplate.INSTANCE().get(AccountInfo, 'select * from AccountInfo where ap_id=?', ap_id);

if(info==null) {
	return SimpleAjax.available();
}
AccountThirdBind bind = JcTemplate.INSTANCE().get(AccountThirdBind, 'select * from AccountThirdBind where id=?', ap_id);
if(bind!=null) {
	info.part_id = bind.part_id;
}

AccountInfoDto dto = new AccountInfoDto(info)

if(bind.account_id) {
	AccountBasic basic = JcTemplate.INSTANCE().get(AccountBasic.class, 'select * from AccountBasic where id=?', bind.account_id);
	if (basic != null) {
		dto.mobile = basic.mobile;
	}
}

dto.pid = bind.pid;
return SimpleAjax.available('', dto);


