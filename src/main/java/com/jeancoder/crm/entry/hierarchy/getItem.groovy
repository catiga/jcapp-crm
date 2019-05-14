package com.jeancoder.crm.entry.hierarchy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.common.RetObj
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String hid =request.getParameter("h_id")
	BigInteger id =new BigInteger(hid);
	MemberCardHierarchy mch= MemberCardHierarchyService.INSTANSE.getItem(id);
	return result.setData(AvailabilityStatus.available(mch));
} catch (Exception e) {
	Logger.error("查询列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}

