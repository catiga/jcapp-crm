package com.jeancoder.crm.entry.hierarchy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/**
 *  设置优惠信息
 * */
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String mc_id = request.getParameter("mc_id");
	String mcr_discount = request.getParameter("mcr_discount");
	String mcr_balance_pay = request.getParameter("mcr_balance_pay");
	String mcr_day_limit =request.getParameter("mcr_day_limit");
	String show_buy_limit =request.getParameter("show_buy_limit");
	def pid = RemoteUtil.getProj().id;
	if(mc_id.equals("")){
		return null;
	}
	//折扣处理
	String set_discount=mcr_discount.trim();
	if(StringUtil.isEmpty(set_discount)){
		set_discount="0";
	}else{
		set_discount=mcr_discount;
	}
	Float disc = Float.valueOf(set_discount);
	if(disc<0.0f||disc>=100.0f) {
		return AvailabilityStatus.notAvailable("折扣不符合规则");
	}
	String cr_discount = MoneyUtil.get_cent_from_yuan(set_discount);//数字转化
	//是否余额支付处理
	if(mcr_balance_pay.equals("")||!(mcr_balance_pay.equals(1)||!mcr_balance_pay.equals(0))) {
		return result.setData(AvailabilityStatus.notAvailable(JsConstants.input_param_error));
	}
	//每日限制购买处理
	if(mcr_day_limit.equals("")||(StringUtil.trim(mcr_day_limit)).equals("")){
		mcr_day_limit = "0";
	}
	//每次限购数量处理
	if(show_buy_limit.equals("")||(StringUtil.trim(show_buy_limit)).equals("")){
		show_buy_limit = "0";
	}
	//判断限购数量是否为整数
	if (Float.parseFloat(mcr_day_limit)!=Integer.parseInt(mcr_day_limit)) {
		return result.setData(AvailabilityStatus.notAvailable("每天限购数量必须为整数"));
	}
	if (Float.parseFloat(show_buy_limit)!=Integer.parseInt(show_buy_limit)) {
		return result.setData(AvailabilityStatus.notAvailable("每单限购数量必须为整数"));
	}
	MemberCardHierarchy mch = new MemberCardHierarchy();
	mch.id = new BigInteger(mc_id);
	mch.cr_discount = cr_discount;
	mch.bapa = Integer.parseInt(mcr_balance_pay);
	mch.day_buy_limit = Integer.parseInt(mcr_day_limit);
	mch.show_buy_limit = Integer.parseInt(show_buy_limit);
	String resultStr = MemberCardHierarchyService.INSTANSE.setBenefit(mch, pid);
	if(!StringUtil.isEmpty(resultStr)){
		return result.setData(AvailabilityStatus.notAvailable(resultStr));
	}
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	logger.error("设置优惠信息失败");
	return result.setData(AvailabilityStatus.notAvailable("设置优惠信息失败"));
}