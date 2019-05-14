package com.jeancoder.crm.entry.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.common.RetObj;
import com.jeancoder.crm.ready.dto.GoodsInfoDto
import com.jeancoder.crm.ready.util.RemoteUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
JCRequest request = RequestSource.getRequest();
String cat_id = request.getParameter("cat_id");
String tyc = request.getParameter("type_code");

try {
	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
	CommunicationParam id = new CommunicationParam("cat_id",cat_id);
	CommunicationParam tyc_temp= new CommunicationParam("tyc",tyc);
	params.add(id);
	params.add(tyc_temp);
	def page = RemoteUtil.connectPage(GoodsInfoDto.class, 'scm', '/incall/catalog/goods', params);
	if(null != page){
		final def dtoList = page.result;
		result.setData(AvailabilityStatus.available(dtoList));
		return result;
	}
	return result.setData("");
} catch (Exception e) {
	Logger.error("查询商品列表失败", e);
	return result.setData(AvailabilityStatus.notAvailable("查询商品列表失败"));
}
