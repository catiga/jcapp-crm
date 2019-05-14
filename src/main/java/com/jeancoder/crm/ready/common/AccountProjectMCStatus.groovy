package com.jeancoder.crm.ready.common

class AccountProjectMCStatus {
	
	static final String INIT = "000"; // 初始化
	static final String LOCKING = "001";// 支付订单未完成
	static final String STOP = "010";// 停止使用
	static final String  UNLOCK = "011";// 会员卡注销
	static final String NORMAL = "100";// 正常使用
}
