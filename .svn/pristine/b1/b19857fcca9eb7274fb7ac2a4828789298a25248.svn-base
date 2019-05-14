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
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 添加一条发卡规则
 */

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String mc_id = request.getParameter("mc_id");
	String a_mc_prefix = request.getParameter("a_mc_prefix");
	String a_mch_prefix = request.getParameter("a_mch_prefix");
	String s_cn = request.getParameter("s_cn");
	String e_cn = request.getParameter("e_cn");
	Long project_id = RemoteUtil.getProj().id;
	
	if(StringUtil.isEmpty(mc_id)||mc_id.equals("")) {
		return result.setData(AvailabilityStatus.notAvailable("未找到规则id"));
	}
	
	if(StringUtil.isEmpty(String.valueOf(project_id))||String.valueOf(project_id).equals("")){
		return result.setData(AvailabilityStatus.notAvailable("未找到项目id"));
	}
	
	if (StringUtil.isEmpty(s_cn) || s_cn.equals("") || StringUtil.isEmpty(e_cn) || s_cn.equals("")) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	
	if(!s_cn.matches("[0-9]+")||!e_cn.matches("[0-9]+")){
		return result.setData(AvailabilityStatus.notAvailable("参数格式不正确"));
	}
	
	MemberCardRule mc_rule = MemberCardRuleService.INSTANSE.getItem(new BigInteger(mc_id.toString()),new BigInteger(project_id.toString()));
	if(mc_rule==null) {
		return AvailabilityStatus.notAvailable("未找到会员规则");
	}else{
		if(!(mc_rule.start_cn.equals(null)||mc_rule.start_cn.equals(""))&&!(mc_rule.end_cn.equals(null)||mc_rule.end_cn.equals(""))){
			return AvailabilityStatus.notAvailable("已经存在发卡规则");
		}
	}
	SysProjectInfo project = RemoteUtil.getProj();
	
	if(!project.equals(null)||!project.equals("")) {
		//共享会员卡信息
		if(!(project.id).equals(mc_rule.pid)) {
			return result.setData(AvailabilityStatus.notAvailable("未找到项目id"));
		}
	} else {
		if(!(project.id).equals(mc_rule.pid)) {
			return result.setData(AvailabilityStatus.notAvailable("未找到项目id"));
		}
	}
	
	mc_rule.id = new BigInteger(mc_id.toString());
	mc_rule.a_mc_prefix = Integer.parseInt(a_mc_prefix);
	mc_rule.a_mch_prefix = Integer.parseInt(a_mch_prefix);
	mc_rule.start_cn = Integer.parseInt(s_cn);
	mc_rule.end_cn = Integer.parseInt(e_cn);
	def mc_r = MemberCardRuleService.INSTANSE.doupdate_rule(mc_rule);
	
	return AvailabilityStatus.available();
} catch (Exception e) {
	Logger.error("保存会员卡规则失败", e);
	return AvailabilityStatus.notAvailable("保存失败");
}