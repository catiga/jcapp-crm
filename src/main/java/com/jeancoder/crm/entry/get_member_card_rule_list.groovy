package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardRuleService
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	List<MemberCardRule> mc_all = MemberCardRuleService.INSTANSE.findAll();
	return AvailabilityStatus.available(mc_all);
} catch (Exception e) {
	Logger.error("查询等级列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}
