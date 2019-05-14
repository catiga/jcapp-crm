package com.jeancoder.crm.entry.common

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.CatalogDto
import com.jeancoder.crm.ready.dto.CatalogViewDto
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
JCRequest request = RequestSource.getRequest();
try {
	List<CatalogDto> list = null;
	String p_id_s = request.getParameter("id");
	if (StringUtil.isEmpty(p_id_s)) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator('scm');
		def ret = systemCaller.doworkAsString('/incall/cashier/catalog');
		if(ret==""){
			return "";
		}
		list = JackSonBeanMapper.jsonToList(ret, CatalogDto.class);
		if(list.size()==0){
			return "";
		}
	} else {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		CommunicationParam id = new CommunicationParam("pid",p_id_s);;
		params.add(id);
		
		CommunicationPower systemCaller = CommunicationSource.getCommunicator('scm');
		def ret = systemCaller.doworkAsString('/incall/cashier/catalog', params);
		if(ret==""){
			return "";
		}
		list = JackSonBeanMapper.jsonToList(ret, CatalogDto.class);
		if(list.size()==0){
			return "";
		}
	}
	def viewlist = new ArrayList<CatalogViewDto>();
	for (def dto : list) {
		def viewDto = new CatalogViewDto(dto);
		viewlist.add(viewDto);
	}
	return viewlist;
} catch (Exception e) {
	Logger.error("查询门店列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询门店列表失败"));
}
return result;
