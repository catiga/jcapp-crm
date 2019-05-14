package com.jeancoder.crm.entry.h5.mc

import java.util.List

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil

/**查询当前pid对应的第一条可以用的会员规则对应的等级列表**/
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try{
	Long pid = RemoteUtil.getProj().id;
	//由当前pid查询启用会员卡规则列表
	List<MemberCardRule> mcrList= MemberCardRuleService.INSTANSE.getListAvailable(pid);
	if(null==mcrList){
		return result.setData(AvailabilityStatus.notAvailable("查询不到会员卡规则"));
	}
	MemberCardRule mcr = mcrList.get(0);
	BigInteger mcr_id = mcr.id;
	//由mc_id查询网售开启会员卡等级列表
	List<MemberCardHierarchy> mchList = MemberCardHierarchyService.INSTANSE.getListAvailable(mcr_id, new BigInteger(0));
	if("".equals(mchList)){
		return result.setData(AvailabilityStatus.notAvailable("查询不到会员卡等级列表"));
	}
	result.setData(AvailabilityStatus.available(mchList));
	return result;
}catch(Exception e){
	Logger.error("查询等级列表失败", e);
	return result.setData(AvailabilityStatus.notAvailable("查询失败"));
}