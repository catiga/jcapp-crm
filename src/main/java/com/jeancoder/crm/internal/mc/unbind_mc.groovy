package com.jeancoder.crm.internal.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.jdbc.JcTemplate

def apmc_id = JC.internal.param('apmc_id');
def pid = JC.internal.param('pid');

def sql = 'select * from AccountProjectMC where id=? and flag!=? and pid=?';

AccountProjectMC e = JcTemplate.INSTANCE().get(AccountProjectMC, sql, apmc_id, -1, pid);
if(e==null) {
	return SimpleAjax.notAvailable('obj_not_found,会员卡未找到');
}

e.flag = -1;
JcTemplate.INSTANCE().update(e);

return SimpleAjax.available();
