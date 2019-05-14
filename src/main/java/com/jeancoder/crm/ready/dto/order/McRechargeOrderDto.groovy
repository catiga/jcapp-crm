package com.jeancoder.crm.ready.dto.order

import com.jeancoder.crm.ready.entity.OrderRechargeMc

class McRechargeOrderDto {
	BigInteger id;
	BigInteger pid;
	
	String order_no;
	
	String total_amount;
	
	String pay_amount;
	
	String pay_type;
	
	String card_no;
	String o_c;// 8001
	String pay_time;
	
	
	
	public McRechargeOrderDto() {
		super();
	}
	
	public McRechargeOrderDto(OrderRechargeMc order) {
		this.id = order.id;
		this.pid = order.pid;
		this.order_no = order.order_no;
		this.total_amount = order.total_amount;
		this.pay_amount = order.pay_amount;
		this.card_no = order.card_no;
		this.o_c = order.o_c;
		this.pay_time = order.pay_time;
	}
}
