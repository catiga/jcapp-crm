package com.jeancoder.crm.entry.predo.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.McPreOrderInfo
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

def content = JC.request.param('content');
order.content = content;

mo_service.update(order);

return SimpleAjax.available();



