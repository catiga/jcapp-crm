package com.jeancoder.crm.entry.api.member

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.dto.mc.AccountMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/*
 * 根据加密字符串解析会员信息
 */

JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	String card_code = request.getParameter("card_code");//二维码字符串
//	String card_code ="0004__eree";测试参数
	def pid = RemoteUtil.getProj().id;
	if (StringUtil.isEmpty(card_code)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_error,"参数不能为空"] as String[]));
	}
	card_code = AccountProjectMCUtil.getMcNum(card_code);
	//TODO 手机号 卡号 读卡器 二维码
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.get_normal_mc_by_num(pid, card_code);
	AccountMcDto dto = new AccountMcDto();
	if (mc == null ) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_found, "会员卡找不到"] as String[] ));
	}
	dto.acmid=mc.id;
	dto.balance=mc.balance;
	dto.cn=mc.mc_name;
	dto.levelname=mc.mc_level;
	dto.mcmobile=mc.mc_mobile;
	dto.mc_num=mc.mc_num;
	dto.id_card=mc.id_card;
	dto.point=mc.point;
	dto.mcname = mc.mc_name;
	
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(mc.mch_id);
	if(mch!=null){
		dto.levelname=mch.h_name;
	} 
//	if (mch == null) {
//		dto = new AccountMcDto(mc);
//	} else {
//		dto = new AccountMcDto(mc,mch,mch.mcRule);
//	}
	return result.setData(AvailabilityStatus.available(dto));
} catch (Exception e) {
	Logger.error("查询不到会员信息失败", e);
	return result.setData(AvailabilityStatus.notAvailable(JsConstants.unknown));
}