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

/**
 * 修改线上充值状态
 */
Result result=new Result();
JCRequest request =RequestSource.getRequest();
JCLogger logger=LoggerSource.getLogger(this.getClass().getName())
try {
	String rid=request.getParameter("id");
	if (StringUtil.isEmpty(rid)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	MemberCardHierarchy mch=new MemberCardHierarchy();
	mch.id=new BigInteger(rid.toString())
	String resultStr = MemberCardHierarchyService.INSTANSE.update_status_recharge(mch.id);
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();
} catch (Exception e) {
	logger.error("更新会员卡规则失败", e);
	return AvailabilityStatus.notAvailable("更新失败");
}
