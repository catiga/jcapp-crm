package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil

/**
 * 查询一条会员规则
 */

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MemberCardRule memberCardRule;
try { 
	def bid = request.getParameter("bid");
	Long project_id = RemoteUtil.getProj().id;
	if(bid.toString().equals(null)||bid.toString().equals("")) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	if(String.valueOf(project_id).equals(null)||String.valueOf(project_id).equals("")){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	memberCardRule = MemberCardRuleService.INSTANSE.getItem(new BigInteger(bid.toString()),new BigInteger(project_id.toString()));
	final def item = memberCardRule;
	return result.setData(AvailabilityStatus.available(item));
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}