package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil
import java.sql.Timestamp

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try {
	String mc_id = request.getParameter("mc_id");
	String s_id = request.getParameter("s_id");
	Long project_id = RemoteUtil.getProj().id;
	//	参数判空
	if(StringUtil.isEmpty(s_id)) {
		result.setData(AvailabilityStatus.notAvailable("参数不能为空"))
	}
	String resultStr = MemberCardRuleService.INSTANSE.bindStore(new BigInteger(mc_id), new BigInteger(s_id));
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();
}catch(Exception e) {
	Logger.error("绑定发卡店失败");
	return AvailabilityStatus.notAvailable("绑定发卡店失败");
}