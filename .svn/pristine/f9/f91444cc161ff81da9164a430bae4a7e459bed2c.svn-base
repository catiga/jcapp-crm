package com.jeancoder.crm.entry.api.order

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.TriggerToKen
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.RemoteUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
params.add(new CommunicationParam("app_code","membercard"));
params.add(new CommunicationParam("callback","/api/order/recharge"));
params.add(new CommunicationParam("order_type","8000,8001"));
//开始去交易中心注册订单
TriggerToKen trade = RemoteUtil.connect(TriggerToKen.class, "trade", "/incall/reg/trigger", params);
println JackSonBeanMapper.toJson(trade);
return new Result().setData(AvailabilityStatus.notAvailable(trade));
} catch (Exception e) {
Logger.error("创建充值订单失败", e);
	return new Result().setData(AvailabilityStatus.notAvailable("创建失败"));
}