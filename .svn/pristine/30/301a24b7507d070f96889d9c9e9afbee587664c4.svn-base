package com.jeancoder.crm.entry.api.hy
//
//import com.jeancoder.app.sdk.source.LoggerSource
//import com.jeancoder.app.sdk.source.RequestSource
//import com.jeancoder.core.http.JCRequest
//import com.jeancoder.core.log.JCLogger
//import com.jeancoder.core.result.Result
//import com.jeancoder.crm.ready.common.AccountProjectMCStatus
//import com.jeancoder.crm.ready.common.AvailabilityStatus
//import com.jeancoder.crm.ready.constant.JsConstants
//import com.jeancoder.crm.ready.dto.mc.McCardAvailableDto
//import com.jeancoder.crm.ready.dto.mc.McHierchHiDto
//import com.jeancoder.crm.ready.entity.AccountProjectMC
//import com.jeancoder.crm.ready.entity.MemberCardHierarchy
//import com.jeancoder.crm.ready.entity.MemberCardRule
//import com.jeancoder.crm.ready.service.AccountProjectMcService
//import com.jeancoder.crm.ready.service.MemberCardHierarchyService
//import com.jeancoder.crm.ready.service.MemberCardRuleService
//import com.jeancoder.crm.ready.util.AccountProjectMCUtil
//import com.jeancoder.crm.ready.util.RemoteUtil
//import com.jeancoder.crm.ready.util.StringUtil
///**
// * 返回第一条会员规则对应的会员等级
// */
//Result result = new Result();
//JCRequest request = RequestSource.getRequest();
//JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
//try {
//	String test_num = "redj00391";
//	def pid = RemoteUtil.getProj().id;
//	String card_code = request.getParameter("card_code");
//	card_code = StringUtil.trim(card_code);
//	if (StringUtil.isEmpty(card_code)) {
//		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_null,"参数不能为空"] as String[]));
//	}
//	card_code = AccountProjectMCUtil.getMcNum(card_code);
//	AccountProjectMC accoun = AccountProjectMcService.INSTANSE.get_mcs_by_mc_num(pid, card_code);
//	if (accoun == null ) {
//		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_found, "会员卡不存在"] as String[]));
//	}
//	if (!test_num.equals(card_code)) {
//		if(!AccountProjectMCStatus.INIT.equals(accoun.status)) {
//			return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_found, "当前会员卡已经被占用"] as String[]));
//		}
//	}
//	if (accoun.mch_id == null) {
//		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_hierarchy, "会员卡等级不存在"] as String[]));
//	}
//	
//	
//	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(accoun.mch_id);
//	
//	/**
//	 * 只有集团卡或者本店卡才显示
//	 */
//	if (mch == null || (!pid.equals(mch.mcRule.pid) && !"1".equals(mch.mcRule.pid.toString()))) {
//		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_hierarchy, "会员卡等级找不到"] as String[]));
//	}
//	List<McHierchHiDto> mchDtoList = new ArrayList<>();
//	McHierchHiDto dto = new McHierchHiDto(mch);
//	dto.mc_rule = new McCardAvailableDto(mch.mcRule);
//	mchDtoList.add(dto);
//	final List<MemberCardHierarchy> resulist = mchDtoList;
//	return result.setData(AvailabilityStatus.available(resulist));
//} catch (Exception e) {
//	Logger.error("查询会员卡等级列表失败", e);
//	return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown,"查询失败"] as String[]));
//}
//
// 