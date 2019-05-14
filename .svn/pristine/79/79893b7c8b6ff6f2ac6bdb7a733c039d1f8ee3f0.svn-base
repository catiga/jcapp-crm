package com.jeancoder.crm.entry.hierarchy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

//
//	@RequestParam(value = "mch_id") Long mch_id,
//			@RequestParam(value = "mcr_supp_recharge") Integer mcr_supp_recharge,
//			@RequestParam(value = "mcr_init_recharge") String mcr_init_recharge,
//			@RequestParam(value = "mcr_least_recharge") String mcr_least_recharge) {
//		SysProjectInfo project = this.getSysProj();
//		MemberCardHierarchy hierarchy = mcService.get_mch_by_id(mch_id);
//		if(hierarchy==null) {
//			return AvailabilityStatus.notAvailable(JsConstants.mc_not_found);
//		}
//		if(!hierarchy.getMcRule().getProject().getId().equals(project.getId())) {
//			return AvailabilityStatus.notAvailable(JsConstants.object_belong_error);
//		}
//		if(hierarchy.getMcRule().isOuter()) {
//			return AvailabilityStatus.notAvailable(JsConstants.unsupport_operatioin);
//		}
//		if(mcr_supp_recharge==null||(!mcr_supp_recharge.equals(0)&&!mcr_supp_recharge.equals(1))) {
//			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//		}
//		if(!DataUtils.isNumber(mcr_init_recharge)||!DataUtils.isNumber(mcr_least_recharge)) {
//			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//		}
//		boolean supp_re = mcr_supp_recharge.equals(0)?false:true;
//		hierarchy.setSuppRecharge(supp_re);
//		hierarchy.setInitRecharge(MoneyUtil.get_cent_from_yuan(mcr_init_recharge));
//		hierarchy.setLeastRecharge(MoneyUtil.get_cent_from_yuan(mcr_least_recharge));
//		mcService.do_save_mcr_hierarchy(hierarchy);
//		return AvailabilityStatus.available();
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MemberCardHierarchyService mchs=new MemberCardHierarchyService();
try {
	String mch_id =request.getParameter("mch_id");
	String mcr_supp_recharge=request.getParameter("mcr_supp_recharge");//是否支持充值
//	String mcr_init_recharge=request.getParameter("mcr_init_recharge");//首充金额
//	String mcr_least_recharge=request.getParameter("mcr_least_recharge");//最低充值金额
	BigInteger id =new BigInteger(mch_id.toString());
	MemberCardHierarchy mch= mchs.getItem(id);
	if(mch==null){
		return AvailabilityStatus.notAvailable("没有数据")
	}
	if(mch.getMcRule().is_outer!=0){
		return AvailabilityStatus.notAvailable("不是会员卡");
	}
//	String init_recharge= StringUtil.trim(mcr_init_recharge);
//	String least_recharge= StringUtil.trim(mcr_least_recharge);
//	if(StringUtil.isEmpty(least_recharge)){
//		mcr_least_recharge="0";
//	}
//	if(StringUtil.isEmpty(init_recharge)){
//		mcr_init_recharge="0";
//	}
//	if(!DataUtils.isNumber(mcr_init_recharge)||!DataUtils.isNumber(mcr_least_recharge)) {
//		return AvailabilityStatus.notAvailable("充值金额必须为数字");
//	}
//	BigInteger mch_init_recharge=new BigInteger(mcr_init_recharge);
//	BigInteger mch_least_recharge=new BigInteger(mcr_least_recharge);
//	String	m_least_recharge=MoneyUtil.get_cent_from_yuan(mcr_least_recharge);
//	String	m_init_recharge=MoneyUtil.get_cent_from_yuan(mcr_init_recharge);
//	if(mch_init_recharge<0||mch_least_recharge<0){
//		return AvailabilityStatus.notAvailable("充值金额必须为大于0");
//	}
	mch.id=id;
	mch.supp_recharge=new Integer(mcr_supp_recharge);
	mch.init_recharge="";
	mch.least_recharge="";
	String resultStr= mchs.updataByRecharge(mch);
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();

} catch (Exception e) {
	Logger.error("保存会员卡规则失败", e);
	return result.setData(AvailabilityStatus.notAvailable("更新失败"));}
