package com.jeancoder.crm.entry.hierarchy
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.MoneyUtil

/*
 * 更新领用方式
 */
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String mch_id = request.getParameter("mch_id");
	String mcr_getmode = request.getParameter("mcr_getmode");
	String mcr_getpay = request.getParameter("mcr_getpay");
//	String mcr_gfcharge = request.getParameter("mcr_gfcharge");
	String validate_type = request.getParameter("validate_type");
	String validate_period = request.getParameter("validate_period");
	def project_id = RemoteUtil.getProj().id;
	Integer getmode = Integer.parseInt(mcr_getmode);
	if(mcr_getmode==null||(!getmode.equals(McConstants._mch_get_mode_direct_)&&!getmode.equals(McConstants._mch_get_upgrade_))) {
		return AvailabilityStatus.notAvailable("参数错误");
	}
	MemberCardHierarchy hierarchy = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(mch_id));	
	if(hierarchy==null) {
		return result.setData(AvailabilityStatus.notAvailable("会员卡规则不存在"));
	}
	MemberCardRule mcr = MemberCardRuleService.INSTANSE.getItem(hierarchy.mc_id);
	if(!mcr.pid.equals(project_id)) {
		return result.setData(AvailabilityStatus.notAvailable("会员卡规则不存在"));
	}
	if(mcr.is_outer == 1) {
		return result.setData(AvailabilityStatus.notAvailable("不能修改规则"));
	}
	if(mcr_getpay==null||!DataUtils.isNumber(mcr_getpay)) {
		return AvailabilityStatus.notAvailable("参数错误");
	}
//	if(mcr_gfcharge==null||!DataUtils.isNumber(mcr_gfcharge)) {
//		return AvailabilityStatus.notAvailable("参数错误");
//	}

	hierarchy.getmode = getmode
	hierarchy.gift_recharge = "";
	hierarchy.getpay = MoneyUtil.get_cent_from_yuan(mcr_getpay);
	hierarchy.validate_type = validate_type;
	hierarchy.validate_period = validate_period;
	MemberCardHierarchyService.INSTANSE.update_mcr_hierarchy(hierarchy);
	return AvailabilityStatus.available();
} catch (Exception e) {
	Logger.error("保存会员卡规则失败", e);
	return result.setData(AvailabilityStatus.notAvailable("更新失败"));
}
