package com.jeancoder.crm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.OrderMcDto
import com.jeancoder.crm.ready.entity.OrderMc
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.util.DateUtil
import com.jeancoder.crm.ready.util.NativeUtil

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
OrderMcDto omd = new OrderMcDto();
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));
	def pid = StringUtil.trim((String)CommunicationSource.getParameter("pid"));
	OrderMc order_item = new OrderMc();
	SimpleAjax pay_type_list = NativeUtil.connect(SimpleAjax, "project", "incall/get_pay_type", ["pid":pid])
	order_item = OrderMcService.INSTANSE.get_crm_card_item(order_no);
	if (!StringUtil.isEmpty(order_item.pay_type)) {
		for(def x:pay_type_list.data){
			if (order_item.pay_type.equals(x.sc_code)) {
				omd.pay_type = x.sc_name;
			}
		}
	}
	omd.order_no = order_item.order_no;
	omd.card_no = order_item.card_no;
	omd.total_amount = order_item.total_amount;
	omd.pay_amount = order_item.pay_amount;
	omd.pay_time = DateUtil.getDate(order_item.pay_time.getTime());
	omd.puid = order_item.puid;
	omd.puname = order_item.puname;
	return SimpleAjax.available("",omd);
} catch (e) {
	Logger.error("会员开卡列表查询失败",e);
	return  SimpleAjax.available.notAvailable("会员开卡查询失败");
}