package com.jeancoder.crm.internal.api.hy

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.jdbc.JcTemplate

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try{
	def pid = JC.internal.param('pid');
	pid = new BigInteger(pid.toString());
	//由当前pid查询启用会员卡规则列表
	List<MemberCardRule> mcrList= MemberCardRuleService.INSTANSE.getListAvailable(pid);
	if(null==mcrList){
		return  AvailabilityStatus.notAvailable("查询不到会员卡规则");
	}
	MemberCardRule mcr = mcrList.get(0);
	BigInteger mcr_id = mcr.id;
	//由mc_id查询网售开启会员卡等级列表
	List<MemberCardHierarchy> mchList = MemberCardHierarchyService.INSTANSE.getListAvailable(mcr_id);
	if("".equals(mchList)){
		return  AvailabilityStatus.notAvailable("查询不到会员卡等级列表");
	}
	if(mchList && !mchList.empty) {
		for(x in mchList) {
			MemberCardRule card_rule = JcTemplate.INSTANCE().get(MemberCardRule, 'select * from MemberCardRule where id?', x.mc_id);
			x.mcRule = card_rule;
		}
	}
	return AvailabilityStatus.available(mchList);
}catch(Exception e){
	Logger.error("查询等级列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}