package com.jeancoder.crm.entry.hierarchy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String rid =request.getParameter("rid")
	if (StringUtil.isEmpty(rid)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	Long id =Long.parseLong(rid)
	MemberCardHierarchy mch= MemberCardHierarchyService.INSTANSE.getItem(id);
	if (!StringUtil.isEmpty(mch)) {
		AvailabilityStatus.notAvailable(mch)
	}
	println mch;
	return AvailabilityStatus.available();
} catch (Exception e) {
	Logger.error("更改会员等级失败", e);
	return AvailabilityStatus.notAvailable("更新失败");
}

