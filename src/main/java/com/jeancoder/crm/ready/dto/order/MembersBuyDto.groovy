package com.jeancoder.crm.ready.dto.order


class MembersBuyDto {
	String card_no;
	Long a_time;
	String total_amount;
	String order_no;
	String pay_type;
	String pay_type_name;
	Integer ops = 0;//0:用户充值 1:后台充值
	BigInteger puid;
	String puname;
	BigInteger pid;
}
