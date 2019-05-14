package com.jeancoder.crm.entry.hierarchy

import java.util.regex.Pattern

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 设置规则前缀
 */


//if(mc_id==null||mc_id<=0l) {
//	return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//}
//if(prefix==null||!Pattern.compile("[0-9a-zA-Z]+").matcher(prefix).matches()) {
//	return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//}
//
//MemberCardRule mc_rule = mcService.get_mcr_by_id(mc_id);
//if(mc_rule==null) {
//	return AvailabilityStatus.notAvailable(JsConstants.common_object_not_found);
//}
//SysProjectInfo project = this.getSysProj();
//SysMerchantInfo merchant = project.getMerchant();
//
//if(merchant.isAsMc()) {
//	//共享会员卡信息
//	if(!merchant.getId().equals(mc_rule.getProject().getMerchant().getId())) {
//		return AvailabilityStatus.notAvailable(JsConstants.object_belong_error);
//	}
//} else {
//	if(!project.getId().equals(mc_rule.getProject().getId())) {
//		return AvailabilityStatus.notAvailable(JsConstants.object_belong_error);
//	}
//}
//if(mc_rule.getPrefix()!=null) {
//	return AvailabilityStatus.notAvailable(JsConstants.unsupport_operatioin);
//}
////		mc_rule.setPrefix(prefix);
////		mcService.do_save_project_mcr(mc_rule, project);
//try {
//	mcService.modify_mc_prefix(project, mc_rule, prefix);
//	return AvailabilityStatus.available();
//} catch(ObjectExistException oex) {
//	return AvailabilityStatus.notAvailable(JsConstants.object_repeat);
//}
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	//获取参数
	String mch_id = request.getParameter("mch_id");
	String prefix = request.getParameter("prefix");
	def pid = RemoteUtil.getProj().id;
	//参数处理
	
	if(StringUtil.isEmpty(mch_id)) {
		return result.setData(AvailabilityStatus.notAvailable(JsConstants.input_param_error));
	}
	if(prefix==null||!Pattern.compile("[0-9a-zA-Z]+").matcher(prefix).matches()) {
		return result.setData(AvailabilityStatus.notAvailable(JsConstants.input_param_error));
	}
	MemberCardHierarchy mch = new MemberCardHierarchy();
	mch.id = new BigInteger(mch_id.toString());
	mch.prefix = prefix;
	String resultStr = MemberCardHierarchyService.INSTANSE.setPrefix(mch,pid);
	if (!StringUtil.isEmpty(resultStr)) {
		return result.setData(AvailabilityStatus.notAvailable(resultStr));
	}
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	Logger.error("设置规则前缀失败");
	return result.setData(AvailabilityStatus.notAvailable("设置规则前缀失败"));
}