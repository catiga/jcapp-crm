package com.jeancoder.crm.entry.hierarchy.detail

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchyDetail
import com.jeancoder.crm.ready.service.MemberCardHierarchyDetailService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	//mc_pid  MemberCardHierarchy的主键
	String mc_pid = request.getParameter("mc_pid");
	BigInteger mch_id = new BigInteger(mc_pid);
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(mch_id);
	if(mch==null){
		return result.setData(AvailabilityStatus.notAvailable("查询不到会员等级"));
	}
	BigInteger mchd_id = mch.id;
	MemberCardHierarchyDetail mch_detail = MemberCardHierarchyDetailService.INSTANSE.getMemberCardHierarchyDetail(mchd_id);
	if(mch_detail==null){
		mch_detail = new MemberCardHierarchyDetail();
		result.addObject("mch", mch);
		result.addObject("mch_detail", mch_detail);
		result.setView("hierarchy/detail");
		return result;
	}
	result.addObject("mch", mch);
	result.addObject("mch_detail", mch_detail);
	result.setView("hierarchy/detail");
	return result;
}catch(Exception e){
	Logger.error("查询会员等级失败");
	return result.setData(AvailabilityStatus.notAvailable("查询会员等级失败"));
}

