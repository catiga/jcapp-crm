package com.jeancoder.crm.entry

import java.util.regex.Pattern

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
 * 增加一条规则前缀
 */

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MemberCardRule memberCardRule;
try {
	String mc_id = request.getParameter("mc_id");
	String prefix = request.getParameter("prefix");
	Long project_id = RemoteUtil.getProj().id;

	if(mc_id.equals(null)||mc_id.equals("")) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}

	if(String.valueOf(project_id).equals(null)||String.valueOf(project_id).equals("")){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	if(prefix.equals(null)||!Pattern.compile("[0-9a-zA-Z]+").matcher(prefix).matches()) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}

	MemberCardRule mc_rule = MemberCardRuleService.INSTANSE.getItem(new BigInteger(mc_id.toString()),new BigInteger(project_id.toString()));
	if(mc_rule==null) {
		return AvailabilityStatus.notAvailable("未找到规则");
	}
	SysProjectInfo project = RemoteUtil.getProj();

	if(!project.equals(null)||!project.equals("")) {
		//共享会员卡信息
		if(!(project.id).equals(mc_rule.pid)) {
			return AvailabilityStatus.notAvailable("未找到规则id");
		}
	} else {
		if(!(project.id).equals(mc_rule.pid)) {
			return AvailabilityStatus.notAvailable("未找到规则id");
		}
	}

	if(mc_rule.prefix!=null) {
		return AvailabilityStatus.notAvailable(JsConstants.unsupport_operatioin);
	}
	
	mc_rule.id = new BigInteger(mc_id.toString());
	mc_rule.prefix = prefix;
	def mc_r = MemberCardRuleService.INSTANSE.do_saveorupdate_prefix(mc_rule);

	return result.setData(AvailabilityStatus.available(mc_r));
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}