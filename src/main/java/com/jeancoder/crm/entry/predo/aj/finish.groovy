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
McPreOrderInfo order = mo_service.get(id);
if(order==null) {
	return SimpleAjax.notAvailable('order_not_found');
}
if(order.ss!='10') {
	return SimpleAjax.notAvailable('status_invalid');
}

order.ss = '50';
mo_service.update(order);

return SimpleAjax.available('', order.id);



