package com.jeancoder.crm.internal.h5.p

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.h5.AccountInfoDto
import com.jeancoder.crm.ready.entity.AccountBasic
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.jdbc.JcTemplate

def mobile = JC.internal.param('mobile')?.toString()?.trim();
def pid = JC.internal.param('pid');

AccountBasic basic = JcTemplate.INSTANCE().get(AccountBasic, 'select * from AccountBasic where mobile=?', mobile);

if(basic==null) {
	return SimpleAjax.notAvailable('obj_not_found,手机号码未找到')
}
AccountThirdBind bind = JcTemplate.INSTANCE().get(AccountThirdBind, 'select * from AccountThirdBind where account_id=? and pid=?', basic.id, pid);
if(bind==null) {
	return SimpleAjax.notAvailable('obj_not_found,账户信息未找到')
}

AccountInfo info = JcTemplate.INSTANCE().get(AccountInfo, 'select * from AccountInfo where ap_id=?', bind.id);

AccountInfoDto dto = new AccountInfoDto(info);
dto.ap_id = bind.id;
dto.mobile = mobile;
dto.part_id = bind.part_id;
dto.pid = bind.pid;

return SimpleAjax.available('', dto);


