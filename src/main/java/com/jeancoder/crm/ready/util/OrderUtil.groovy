package com.jeancoder.crm.ready.util

import com.jeancoder.crm.ready.order.OrderConstants

class OrderUtil {
	
	public static boolean is_order_payed(String o_s) {
		List<String> oss = OrderConstants.payed_orders();
		for(String s : oss) {
			if(s.equals(o_s)) {
				return true;
			}
		}
		return false;
	}
}
