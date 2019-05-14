package com.jeancoder.crm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.AccountMcDto
import com.jeancoder.crm.ready.dto.order.AccountpMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.service.OrderMcService

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
List<AccountpMcDto> list = new ArrayList<AccountpMcDto>();
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));
	
	List<AccountProjectMC> order_items = new ArrayList<AccountProjectMC>();
	
	order_items = OrderMcService.INSTANSE.get_crm_order_item(order_no);
 
	for (AccountProjectMC order: order_items) {
		AccountMcDto   accountmcdto = new AccountMcDto();
		
		accountmcdto.cn = order.mc_num;
		accountmcdto.levelname = order.mc_level;
		accountmcdto.balance = order.balance;

		list.add(accountmcdto);
	}
	return SimpleAjax.available("",list);
} catch (e) {
	Logger.error("会员开卡列表查询失败",e);
	return SimpleAjax.notAvailable("会员开卡查询失败")
}