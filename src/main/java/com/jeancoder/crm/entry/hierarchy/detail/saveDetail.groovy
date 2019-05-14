package com.jeancoder.crm.entry.hierarchy.detail

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.MemberCardHierarchyDetailService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService

/*
 * 保存会员权益
 */
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	//mch_pid mm_member_card_hierarchy表的主键
	String mch_id = request.getParameter("mch_id");
	String mch_detaill = request.getParameter("mch_detail");
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(mch_id));
	if("".equals(mch)){
		return result.setData(AvailabilityStatus.notAvailable("查询不到会员等级"));
	}
	
	Integer num = MemberCardHierarchyDetailService.INSTANSE.updateMemberCardHierarchyDetail(mch.id, mch_detaill);
	if(num<=0){
		return result.setData(AvailabilityStatus.notAvailable("保存会员权益失败"));
	}
	result.addObject("mch", mch);
	return result.setData(AvailabilityStatus.available());
}catch (Exception e){
	Logger.error("保存会员权益失败"+e);
	return result.setData(AvailabilityStatus.notAvailable("保存会员权益失败"));
}
