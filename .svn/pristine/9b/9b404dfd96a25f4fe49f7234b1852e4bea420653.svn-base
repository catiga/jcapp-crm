package com.jeancoder.crm.internal.h5.user

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.service.GeneralUserService

JCLogger  Logger = JCLoggerFactory.getLogger(this.getClass())
try {
	def name = CommunicationSource.getParameter("name");
	def ap_id = CommunicationSource.getParameter("ap_id");
	def head = CommunicationSource.getParameter("head");
	println name + "___" + ap_id + 'head'
	AccountInfo accountInfo = new AccountInfo();
	accountInfo.nickname = name;
	accountInfo.head = head;
	GeneralUserService._instance_.update_account_info(ap_id, accountInfo);
	println "修改成功"
	return AvailabilityStatus.available();
} catch (Exception e) {
	Logger.error("修改资料失败", e);
	return AvailabilityStatus.notAvailable("修改资料失败");
}
