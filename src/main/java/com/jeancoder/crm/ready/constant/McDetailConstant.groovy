package com.jeancoder.crm.ready.constant

class McDetailConstant {
	
	static final String create_order =  "1001"; // 购卡
	static final String recharge_order =  "1020";// 充值
	static  final String gift_order =  "1021";// 赠送
	
	static final String deduction=  "2000";//扣款
	static final String deduction_goods =  "2001";//商品扣款
	static final String deduction_movie =  "2002";//电影扣款
	
	static final String refund =  "3000";//退款
	static final String refund_order_good =  "3001"; // 退款
	static final String refund_order_movie  =  "3002"; // 退款
	
	static final String refund_recharge_order = "3003";// 充值金额回退
	
	public static String getRefundCodes () {
		return   refund + "," + refund_order_good + "," + refund_order_movie ;
	}
	
	public static String getDeductionCodes () {
		return  deduction + "," + deduction_goods + "," + deduction_movie;
	}
	
}
