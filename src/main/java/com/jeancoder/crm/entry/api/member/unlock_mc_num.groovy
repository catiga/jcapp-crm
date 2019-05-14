package com.jeancoder.crm.entry.api.member


import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.McPreOrderItem
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	def pid = RemoteUtil.getProj().id; 
	String mc_num = request.getParameter("mc_num");//等级id
	if (StringUtil.isEmpty(mc_num)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_empty, "参数不能为空"] as String[]))
	}
	
	// 通过卡号找到 对应的会员规则绑定的项目的id,与当前pid 要一致。
	List<McPreOrderItem> orderItem= PreMoService._instance_.get_item_by_mc_num(pid, mc_num);
	McPreOrderItem item = null;
	if (orderItem==null) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_found, "会员卡未找到"] as String[]));
	} 
	if (orderItem.size() != 1) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "会员卡号重复"] as String[]));
	}
	if (McConstants.mc_info_item_pub_.equals(orderItem.get(0).pub_ss)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "已经发卡成功"] as String[]));
	}
	item = orderItem.get(0);
	PreMoService._instance_.unlock_item(item);
//	AccountProjectMC apmc = AccountProjectMcService.INSTANSE.get_mcs_by_mc_num(pid, mc_num);
	return result.setData(AvailabilityStatus.available());
} catch (Exception e) {
	Logger.error("解锁会员卡失败", e);
	return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown, "解锁会员卡失败"] as String[]));
}
