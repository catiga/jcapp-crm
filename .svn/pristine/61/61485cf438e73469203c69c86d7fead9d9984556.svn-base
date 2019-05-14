package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.mc.AccountMcDto
import com.jeancoder.crm.ready.dto.mc.McCardAvailableDto
import com.jeancoder.crm.ready.dto.mc.McHierchHiDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService

/**
 * 查询会员卡列表
 */

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try { 
	def pid = request.getParameter("pid");
	if(pid == null ||pid.toString().equals("")) {
		return AvailabilityStatus.notAvailable("pid不能为空");
	}
	List<MemberCardRule> mrcl = MemberCardRuleService.INSTANSE.getItem_mch(new BigInteger(pid.toString()));
	if(mrcl==null||mrcl.isEmpty()){
		return AvailabilityStatus.notAvailable("没有对应的规则");
	}
	
	List<McCardAvailableDto> dtoList = new ArrayList<McCardAvailableDto>();
	for (MemberCardRule mcr : mrcl) {
		McCardAvailableDto mcrDto =  new McCardAvailableDto(mcr);
		List<MemberCardHierarchy> mchl =  MemberCardHierarchyService.INSTANSE.getAllByMcId(mcr.id);
		List<McHierchHiDto>  mchDtoList =new ArrayList<McHierchHiDto>();
		for (MemberCardHierarchy mch : mchl) {
			mchDtoList.add(new  McHierchHiDto(mch));
		}
		mcrDto.hierarchyList = mchDtoList;
		dtoList.add(mcrDto);
	}
	return AvailabilityStatus.available(dtoList);
} catch (Exception e) {
	Logger.error("查询等级列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}