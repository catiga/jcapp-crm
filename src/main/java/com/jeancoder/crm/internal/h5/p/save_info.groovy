package com.jeancoder.crm.internal.h5.p

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.jdbc.JcTemplate

def name = JC.internal.param('name');
def mobile = JC.internal.param('mobile');
def email = JC.internal.param('email');
def postcode = JC.internal.param('postcode');
def weight = JC.internal.param('weight');
def height = JC.internal.param('height');
def countryname = JC.internal.param('countryname');
def ethnicitycode = JC.internal.param('ethnicitycode');
def ethnicityname = JC.internal.param('ethnicityname');
def birthday = JC.internal.param('birthday');

def ap_id = JC.internal.param('ap_id');
def pid = JC.internal.param('pid');

AccountInfo info = JcTemplate.INSTANCE().get(AccountInfo, 'select * from AccountInfo where ap_id=?', ap_id);
def update = true;
if(info==null) {
	info = new AccountInfo();
	info.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	info.ap_id = new BigInteger(ap_id);
	info.pid = new BigInteger(pid);
	info.flag = 0;
	update = false;
}

info.realname = name;
info.mobile = mobile;
info.email = email;
info.postcode = postcode;
info.birthday = birthday;
try {
	info.weight = new BigDecimal(weight);
} catch(any) {}

try {
	info.height = new BigDecimal(height);
} catch(any) {}

info.countryname = countryname;
info.ethnicitycode = ethnicitycode;
info.ethnicityname = ethnicityname;

if(update) {
	JcTemplate.INSTANCE().update(info);
} else {
	JcTemplate.INSTANCE().save(info);
}

return SimpleAjax.available();


