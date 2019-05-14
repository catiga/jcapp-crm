package com.jeancoder.crm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.OrderMc
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.service.OrderRechargeService
import com.jeancoder.crm.ready.util.JackSonBeanMapper

Result result = new Result();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName())
def p = CommunicationSource.getParameter("orders");
def pid = CommunicationSource.getParameter("pid");
try {
	if (StringUtil.isEmpty(p)||StringUtil.isEmpty(pid)) {
		return SimpleAjax.notAvailable('参数不可为空');
	}
	def resultList = [];
	String [] list = p.split(";");//参数为："编号+oc"
	for (int i = 0 ; i<list.length;i++) {
		def item = list[i].split(",");
		if (item[1].equals('8000')) {//开卡订单
			OrderMc order = new OrderMc();
			order.order_no = item[0];
			order.o_c = item[1];
			order.pid = new BigInteger(pid.toString());
			def result_data  = OrderMcService.INSTANSE.update_order_status(order);
			resultList.add(result_data)
		}else if(item[1].equals('8001')){//充值订单
			OrderRechargeMc order = new OrderRechargeMc();
			order.order_no = item[0];
			order.o_c = item[1];
			order.pid = new BigInteger(pid.toString());
			def result_data  = OrderRechargeService.INSTANSE.update_order_status(order);
			resultList.add(result_data)
		}
	}
	return SimpleAjax.available("",resultList);
} catch (Exception e) {
	Logger.error("修改订单状态失败", e);
	return SimpleAjax.notAvailable("修改订单状态失败");
}