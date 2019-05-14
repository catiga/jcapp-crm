package com.jeancoder.crm.internal.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.AccountpMcDto
import com.jeancoder.crm.ready.dto.order.OrderMcDto
import com.jeancoder.crm.ready.dto.order.OrderRechargeMcDto
import com.jeancoder.crm.ready.entity.OrderMc
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcTemplate

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
def  list = [];
try {
	String os = JC.internal.param("os");
	def pid = JC.internal.param("pid");
	if (StringUtil.isEmpty(os)) {
		return SimpleAjax.available("",list);
	}
	String[] oss = os.split(";")
	String mc_no = ""
	String rec_no =""
	for (def str : oss) {
		String[] order = str.split(",");
		if ("8000".equals(order[1])) {
			mc_no +=  "'" + order[0] + "',"
		}
		if ("8001".equals(order[1])) {
			rec_no +=  "'" +  order[0] + "',"
		}
	}
	JcTemplate jcTemplate = JcTemplate.INSTANCE()
	if (!StringUtil.isEmpty(mc_no)) {
		mc_no = mc_no.substring(0,mc_no.length()-1);
		String sql = "select  * from  mm_order_mc WHERE  order_no in ("+mc_no +");"
		List<OrderMc> mcs = jcTemplate.find(OrderMc, sql, null);
		for (def mc : mcs) {
			list.add(new OrderRechargeMcDto(mc));
		}
	}
	if (!StringUtil.isEmpty(rec_no)) {
		rec_no = rec_no.substring(0,rec_no.length()-1);
		String sql = "select  * from  mm_order_recharge_mc WHERE  order_no in ("+rec_no +");"
		println sql;
		List<OrderRechargeMc> mcs = jcTemplate.find(OrderRechargeMc, sql,null);
		for (def mc : mcs) {
			list.add(new OrderRechargeMcDto(mc));
		}
	}
	return SimpleAjax.available("",list)
} catch (e) {
	Logger.error("会员开卡列表查询失败",e);
	return SimpleAjax.notAvailable("会员开卡查询失败");
}
