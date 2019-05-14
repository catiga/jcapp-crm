package com.jeancoder.crm.entry.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.NativeUtil

Result view = new Result();

def order_no = JC.request.param('order_no');
def o_c = JC.request.param('o_c');
def pid  = GlobalHolder.getProj().getId();
if(o_c.equals("8000")){
	SimpleAjax order_item = NativeUtil.connect(SimpleAjax.class, "crm", "order/order_mc_card_list", ["order_no":order_no,"pid":pid])
	return order_item;
}else if(o_c.equals("8001")){
	SimpleAjax order_item = NativeUtil.connect(SimpleAjax.class, "crm", "order/order_mc_recharge_list", ["order_no":order_no,"pid":pid])
	return order_item;
}else if(o_c.equals("1000")){
	//用d_num判断是否是退款如果不是空就是退款
	SimpleAjax order_item = NativeUtil.connect(SimpleAjax.class, "scm", "order/order_item_scm_crm_list", ["order_no":order_no])
	return order_item;
}else if(o_c.equals("2000")){
	SimpleAjax order_item = NativeUtil.connect(SimpleAjax.class, "ticketingsys", "ticketing/ticketing_crm_movies", ["order_no":order_no])
	return order_item;
}else if(o_c.equals("2010")){
	SimpleAjax order_item = NativeUtil.connect(SimpleAjax.class, "ticketingsys", "ticketing/ticketing_crm_reserve_movies", ["order_no":order_no])
	return order_item;
}else if(o_c.equals("5000")){
	SimpleAjax order_item = NativeUtil.connect(SimpleAjax.class, "reserve", "order/order_item_crm_list", ["order_no":order_no])
	return order_item;
}