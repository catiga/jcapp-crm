package com.jeancoder.crm.entry.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.util.RemoteUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
JCRequest request = RequestSource.getRequest();
Result result = new Result();
try {
	def list = RemoteUtil.connectList(SysProjectInfo.class, 'project', '/incall/project_list');
//	def list = new ArrayList<GoodsInfoDto>();
//	def dto = new GoodsInfoDto();
//	dto.id = 2L;
//	dto.goods_id="2";
//	dto.goods_name = "商品1";
//	list.add(dto);
//	
//	def dto1 = new GoodsInfoDto();
//	dto1.id = 21L;
//	dto1.goods_id="11";
//	dto1.goods_name = "商品11";
//	list.add(dto1);
	result.setData(AvailabilityStatus.available(list));
} catch (Exception e) {
	Logger.error("查询项目失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询项目列表失败"));
}
return result;