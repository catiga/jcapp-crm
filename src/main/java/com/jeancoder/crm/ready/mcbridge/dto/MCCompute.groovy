package com.jeancoder.crm.ready.mcbridge.dto

import com.jeancoder.crm.ready.dto.order.AccountMcDto

// 会员计算统一返回结构
class MCCompute {
	String code;
	String msg;
	List<ItemDto> items;
	AccountMcDto accountMc;
	String offerAmount;
	String totalAmount;
}
