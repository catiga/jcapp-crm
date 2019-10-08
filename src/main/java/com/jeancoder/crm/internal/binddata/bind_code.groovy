package com.jeancoder.crm.internal.binddata

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.BDPet
import com.jeancoder.jdbc.JcTemplate

def ap_id = JC.internal.param('ap_id');
def pid = JC.internal.param('pid');
def code = JC.internal.param('code')

//首先检查当前用户是否已经绑定过编码
BDPet binded = JcTemplate.INSTANCE().get(BDPet, 'select * from BDPet where flag!=? and ap_id=?', -1, ap_id);
if(binded) {
	return SimpleAjax.notAvailable('bind_repeat, 已经绑定过编码');
}

BDPet bdp = new BDPet();
bdp.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
bdp.ap_id = ap_id;
bdp.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
bdp.flag = 0;
bdp.pid = new BigInteger(pid);
bdp.catcode = code;

JcTemplate.INSTANCE().save(bdp);

return SimpleAjax.available();
