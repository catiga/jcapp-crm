package com.jeancoder.crm.entry.predo.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.McPreOrderInfo
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.GlobalHolder

PreMoService mo_service = PreMoService.INSTANCE();

def id = JC.request.param('id');

def name = JC.request.param('name');
def info = JC.request.param('info');
def sn = JC.request.param('sn');
def mch_id = JC.request.param('mch_id');
def num = JC.request.param('o_num');

MemberCardHierarchyService mch_service = MemberCardHierarchyService.INSTANSE;
MemberCardHierarchy hierarchy = mch_service.getItem(BigInteger.valueOf(Long.valueOf(mch_id)));
if(hierarchy==null) {
	return SimpleAjax.notAvailable('card_not_found');
}

McPreOrderInfo order = null;

def update = false;
if(id) {
	order = mo_service.get(id);
	if(order==null) {
		return SimpleAjax.notAvailable('order_not_found');
	}
	update = true;
	if(order.ss!='00') {
		return SimpleAjax.notAvailable('status_invalid');
	}
} else {
	order = new McPreOrderInfo();
	order.ss = '00';
	order.pid = GlobalHolder.proj.id;
	order.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	order.c_time = order.a_time;
	order.flag = 0;
	order.order_num = System.currentTimeMillis() + '';
}

order.mcr_id = hierarchy.mcRule.id;
order.mcr_name = hierarchy.mcRule.title;
order.mch_id = hierarchy.id;
order.mch_name = hierarchy.h_name;

order.name = name;
order.info = info;
try {
	order.total_num = Integer.valueOf(num);
	if(order.total_num<1) {
		return SimpleAjax.notAvailable('num_error');
	}
}catch(any) {
	return SimpleAjax.notAvailable('num_error');
}

if(update) {
	mo_service.update(order);
} else{
	order.id = mo_service.save(order);
}

return SimpleAjax.available('', order.id);



