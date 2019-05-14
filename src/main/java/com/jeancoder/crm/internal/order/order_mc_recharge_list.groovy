package com.jeancoder.crm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.McRechargeOrderDto
import com.jeancoder.crm.ready.dto.order.OrderRechargeMcDto
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.util.DateUtil

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
List<McRechargeOrderDto> list = new ArrayList<McRechargeOrderDto>();
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));
	
	List<OrderRechargeMc> order_items = new ArrayList<OrderRechargeMc>();
	
	order_items = OrderMcService.INSTANSE.get_crm_recharge_item(order_no);
 
	for (OrderRechargeMc order: order_items) {
		OrderRechargeMcDto  orderrechargemcdto = new OrderRechargeMcDto();
		
		orderrechargemcdto.order_no = order.order_no;
		orderrechargemcdto.card_no = order.card_no;
		orderrechargemcdto.total_amount = order.total_amount;
		orderrechargemcdto.pay_amount = order.pay_amount;
		orderrechargemcdto.pay_time = DateUtil.getDate(order.pay_time.getTime());
		orderrechargemcdto.pay_type = order.pay_type;
		orderrechargemcdto.puid = order.puid;
		orderrechargemcdto.puname = order.puname;
		
		list.add(orderrechargemcdto);
	}
	return SimpleAjax.available("",list);
} catch (e) {
	Logger.error("会员充值查询失败",e);
	return SimpleAjax.notAvailable("会员充值查询失败")
}