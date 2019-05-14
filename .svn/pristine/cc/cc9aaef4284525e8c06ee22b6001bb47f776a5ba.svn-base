package com.jeancoder.crm.entry.common

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.common.RetObj;
import com.jeancoder.crm.ready.dto.StoreInfoDto
import com.jeancoder.crm.ready.util.RemoteUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	List<StoreInfoDto> list = RemoteUtil.connectList(StoreInfoDto.class, 'project', '/incall/mystore');
	final def dtoList = list;
	result.setData(AvailabilityStatus.available(dtoList));
} catch (Exception e) {
	Logger.error("查询门店列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询门店列表失败"));
}
return result;