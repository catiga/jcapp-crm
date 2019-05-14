package com.jeancoder.crm.internal.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate

def info = JC.internal.param('info');

info = JackSonBeanMapper.jsonToMap(info);

def id = info['id'];
def name = info['name'];
def phone = info['phone'];
def discount = info['discount'];
def goods_discount = info['goods_discount'];

try {
	id = new BigInteger(id + '');
} catch(any) {}
AccountProjectMC mc = null;
if(id) {
	mc = AccountProjectMcService.INSTANSE.getItem(id);
}
if(mc==null) {
	return SimpleAjax.notAvailable('mc_not_found,会员卡未找到');
}
mc.mc_name = name;
mc.mc_mobile = phone;
mc.discount = discount;
mc.goods_discount = goods_discount;

JcTemplate.INSTANCE().update(mc);

return SimpleAjax.available('', mc);


