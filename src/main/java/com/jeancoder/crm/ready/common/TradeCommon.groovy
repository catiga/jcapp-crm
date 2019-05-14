package com.jeancoder.crm.ready.common

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.crm.ready.dto.TriggerToKen;
import com.jeancoder.crm.ready.dto.order.DataTrans
import com.jeancoder.crm.ready.order.OrderConstants
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

class TradeCommon {
	
	public static final TradeCommon INSTANSE =  new TradeCommon();
	private static Map<String, String> map = new HashMap<String, String>();
	
	private TradeCommon() {}
	
	public String getRechargeToken() {
		String token = map.get(OrderConstants.OrderType._recharge_mc_);
		if (!StringUtil.isEmpty(token)) {
			return token;
		}
//		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//		params.add(new CommunicationParam("app_code","crm"));
//		params.add(new CommunicationParam("callback","/api/order/recharge"));
//		params.add(new CommunicationParam("order_type",OrderConstants.OrderType._recharge_mc_));
		//开始去交易中心注册订单
		DataTrans trade = JC.internal.call(DataTrans, 'trade', '/incall/reg/trigger', [app_code:"crm",order_type:OrderConstants.OrderType._recharge_mc_,callback:"/api/order/recharge"])
		//DataTrans trade = RemoteUtil.connect(DataTrans.c
		//DataTrans trade = RemoteUtil.connect(DataTrans.class, "trade", "/incall/reg/trigger", params);
		map.put(OrderConstants.OrderType._recharge_mc_, trade.data[0].token);
		return trade.data[0].token;
	}
	
	public String getCreateToken() {
		String token = map.get(OrderConstants.OrderType._get_mc_);
		if (!StringUtil.isEmpty(token)) {
			return token;
		}
//		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//		params.add(new CommunicationParam("app_code","crm"));
//		params.add(new CommunicationParam("callback","/api/order/createOrder"));
//		params.add(new CommunicationParam("order_type",OrderConstants.OrderType._get_mc_));
		//开始去交易中心注册订单
		DataTrans trade = JC.internal.call(DataTrans, 'trade', '/incall/reg/trigger', [app_code:"crm",order_type:OrderConstants.OrderType._get_mc_,callback:"/api/order/createOrder"])
		//DataTrans trade = RemoteUtil.connect(DataTrans.class, "trade", "/incall/reg/trigger", params);
		map.put(OrderConstants.OrderType._get_mc_, trade.data[0].token);
		return trade.data[0].token;
	}
}
