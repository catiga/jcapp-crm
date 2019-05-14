package com.jeancoder.crm.entry.api.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.mc.McCardAvailableDto
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil

JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	SysProjectInfo project = RemoteUtil.getProj();
	List<MemberCardRule> mc_rules = MemberCardRuleService.INSTANSE.getListAvailable(project.id);
	if(mc_rules==null||mc_rules.isEmpty()) {
		return null;
	}
	
	List<McCardAvailableDto> ret_list = new ArrayList<McCardAvailableDto>();
	for(MemberCardRule mc : mc_rules) {
		ret_list.add(new McCardAvailableDto(mc));
	}
	return result.setData(AvailabilityStatus.available(ret_list));
} catch (Exception e) {
	Logger.error("获取可用的会员规则失败", e);
	return result.setData(AvailabilityStatus.notAvailable("获取失败"));
}
