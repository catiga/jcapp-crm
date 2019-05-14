package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil

/**
 * 查询一条会员规则
 */

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MemberCardRule memberCardRule;
try { 
	String mc_id = request.getParameter("mc_id");
	Long project_id = RemoteUtil.getProj().id;
	
	if(mc_id.equals(null)||mc_id.equals("")) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	if(String.valueOf(project_id).equals(null)||String.valueOf(project_id).equals("")){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	MemberCardRule mc_rule = MemberCardRuleService.INSTANSE.getItem(new BigInteger(mc_id.toString()),new BigInteger(project_id.toString()));
	if(mc_rule==null) {
		return AvailabilityStatus.notAvailable(JsConstants.mc_not_found);
	}
	SysProjectInfo project = RemoteUtil.getProj();
	mc_rule.id = new BigInteger(mc_id.toString());
	def mc_r = MemberCardRuleService.INSTANSE.doupdate_ro(mc_rule);
	
	return result.setData(AvailabilityStatus.available(mc_r));
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}