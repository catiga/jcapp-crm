package com.jeancoder.crm.internal.h5.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.StoreInfoDto
import com.jeancoder.crm.ready.dto.order.McRechargeOrderDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.OrderRechargeService
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.StringUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	// 所有参数类型为String
	def pid = JC.internal.param('pid');
	def acmid = JC.internal.param("acmid");//卡号
	def sid = JC.internal.param("sid")//等级id
	def apid = JC.internal.param("apid")//等级id
	def r_amount = JC.internal.param("r_amount")//充值金额,单位元
	
	StoreInfoDto store = null;
	if(sid != null) {
		store = JC.internal.call(StoreInfoDto, 'project', '/incall/store_by_id', ['id':sid]);	//h5获取门店不限制pid，所以不传pid
	}
	def sname = null;
	if (store != null) {
		sid = store.id;
		sname = store.store_name;
	}
	if (StringUtil.isEmpty(acmid) || StringUtil.isEmpty(r_amount) || !DataUtils.isNumber(r_amount)) {
		return AvailabilityStatus.notAvailable([JsConstants.input_param_null,"参数为空"] as String[]);
	}
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.get_normal_mc_by_id(pid, acmid);
	if (mc == null ) {
		return AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "等级未找到"] as String[]);
	}

	OrderRechargeMc order = OrderRechargeService.INSTANSE.create_mc_recharge_order(new BigInteger(pid.toString()),sid,sname, mc,null,MoneyUtil.get_cent_from_yuan(r_amount), "",0,null);
	McRechargeOrderDto dto = new McRechargeOrderDto(order);
	def order_data = JackSonBeanMapper.toJson(order);
	order_data = URLEncoder.encode(order_data);
	order_data = URLEncoder.encode(order_data);
	//开始去交易中心注册订单
	SimpleAjax trade = JC.internal.call(SimpleAjax,"trade", "/incall/create_trade", [pid:pid,od:order_data,oc:"8001"])
	return AvailabilityStatus.available(trade);
} catch (Exception e) {
	Logger.error("创建充值订单失败", e);
	return AvailabilityStatus.notAvailable([JsConstants.unknown,"创建充值订单失败"] as String[]);
}