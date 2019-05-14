package com.jeancoder.crm.entry.api.member

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.entity.McPreOrderItem
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 根据等级id 取一个卡编号并锁卡操作
 * 
 **/
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	def pid = RemoteUtil.getProj().id; 
	String h_id = request.getParameter("h_id");//等级id
	if (StringUtil.isEmpty(h_id)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_empty, "参数不能为空"] as String[]))
	}
	
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(h_id));
	if(mch==null){
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_hierarchy, "会员等级不存在"] as String[]))
	}
	MemberCardRule mcr = MemberCardRuleService.INSTANSE.getItem(mch.mc_id);
	// 判断会员规则绑定的项目id 和当前pid是否一致，
	if (mcr == null ||  !pid.equals(mcr.pid)) {
		return result.setData(AvailabilityStatus.notAvailable("会员卡批次不存在或不能使用"));
	}
	if(!McConstants.mc_rule_start_.equals(mcr.mcr_status)){
		return result.setData(AvailabilityStatus.notAvailable("当前会员卡批次被停用"));
	}
	
	List<McPreOrderItem> orderItem= PreMoService._instance_.get_item_by_mch_id(mch.id);
	McPreOrderItem item = null;
	if (orderItem==null || orderItem.size() == 0) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_hierarchy, "会员卡已经发完"] as String[]));
	} 
	item = orderItem.get(0);
	PreMoService._instance_.lock_mc_num(item);
	return result.setData(AvailabilityStatus.available(item));
} catch (Exception e) {
	Logger.error("查询会员卡号失败", e);
	return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_hierarchy, "查询会员卡号失败"] as String[]));

}
