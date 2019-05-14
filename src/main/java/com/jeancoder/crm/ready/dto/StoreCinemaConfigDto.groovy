package com.jeancoder.crm.ready.dto

class StoreCinemaConfigDto {
	
	BigInteger sid;
	
	String storeCinemaNum;
	
	String tcCode;
	
	String tcName;
	
	String ssCode;
	
	String ssName;
	
	
	
	String authChanNum;
	
	String authChanCode;
	
	String authChanUrl;
	
	String ppRuleType;
	
	String ppRuleValue;
	
	String ppRuleCht = "10";
	
	//门店销售影票手续费，默认手续费为0
	String handleFee = "0";
	
	//门店售票出票规则，默认为1，实际售价出票
	//2 最低票价出票
	//3 NOPUB
	Integer ptRule = 1;
	
	//20170103停售时间设置，单位分钟
	Integer httime = 30;
	
	//自动同步处理字段
	Long utimekey;
}
