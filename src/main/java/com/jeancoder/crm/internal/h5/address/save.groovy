package com.jeancoder.crm.internal.h5.address

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountAddress
import com.jeancoder.jdbc.JcTemplate

JCLogger logger = JCLoggerFactory.getLogger('');

def pid = JC.internal.param('pid');

def id = JC.internal.param('id');
def is_def = JC.internal.param('is_def');

def name = JC.internal.param('name');
def mobile = JC.internal.param('mobile');

def province_code = JC.internal.param('province_code');
def province_name = JC.internal.param('province_name');

def city_code = JC.internal.param('city_code');
def city_name = JC.internal.param('city_name');

def zone_code = JC.internal.param('zone_code');
def zone_name = JC.internal.param('zone_name');

def address = JC.internal.param('address');

def ap_id = JC.internal.param('ap_id');

def update = true;
AccountAddress address_obj = null;
if(id) {
	address_obj = JcTemplate.INSTANCE().get(AccountAddress, 'select * from AccountAddress where flag!=? and id=?', -1, id);
}

if(address_obj==null) {
	update = false;
	address_obj = new AccountAddress();
	address_obj.proj_id = new BigInteger(pid);
	address_obj.a_time = new Date();
	address_obj.flag = 0;
	address_obj.ap_id = ap_id;
}

logger.info('address=' + address);

if(address!=null) {
	address_obj.address = address;
}
if(city_name!=null) {
	address_obj.city = city_name;
}
if(city_code!=null) {
	address_obj.city_code = city_code;
}

if(province_name!=null)
	address_obj.province = province_name;
if(province_code!=null)
	address_obj.province_code = province_code;
if(zone_name!=null)
	address_obj.zone = zone_name;
if(zone_code!=null)
	address_obj.zone_code = zone_code;
if(mobile!=null)
	address_obj.mobile = mobile;
if(name!=null)
	address_obj.name = name;


address_obj.is_def = is_def;
address_obj.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());

if(update) {
	JcTemplate.INSTANCE().update(address_obj);
} else {
	address_obj.id = JcTemplate.INSTANCE().save(address_obj);
}

if(is_def>0) {
	def sql = 'update AccountAddress set is_def=0 where id!=? and ap_id=?';
	JcTemplate.INSTANCE().batchExecute(sql, address_obj.id, ap_id);
}

return SimpleAjax.available('', address_obj);


