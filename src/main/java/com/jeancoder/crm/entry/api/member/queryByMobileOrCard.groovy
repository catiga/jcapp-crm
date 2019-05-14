package com.jeancoder.crm.entry.api.member

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.util.RemoteUtil

/**
 * 根据手机号或者会员号好查询会员列表
 */
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	def query_key = request.getParameter("query_key");
	
	def pid = RemoteUtil.getProj().id;
	def list = AccountProjectMcService.INSTANSE.queryByMobileOrCard(pid,query_key);
	return result.setData(AvailabilityStatus.available(list));
} catch (Exception e) {
	Logger.error("查询失败", e);
	return result.setData(AvailabilityStatus.notAvailable("查询失败"));
}
