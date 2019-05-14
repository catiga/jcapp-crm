package com.jeancoder.crm.entry.hierarchy.equity

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.service.MemberCardHierarchySubjoinService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result=new Result();
MemberCardHierarchySubjoinService mchss=new MemberCardHierarchySubjoinService();
try {

	String mc_id=request.getParameter("mch_id");
	BigInteger id=new BigInteger(mc_id.toString());
	MemberCardHierarchySubjoin mchs=mchss.getBySet_type(id);//type 0000
	List<MemberCardHierarchySubjoin> mchsa=mchss.getByMch_id(id);//type !=0000
	String jsonMch=JackSonBeanMapper.listToJson(mchsa);
	if (mchsa!=null) {
		result.addObject("mch_id", mc_id)
		result.addObject("jsonMch", jsonMch);
	};
	if (mchs==null&&mchsa==null) {
		result.addObject("mch_id", mc_id);
	}
	if(mchs!=null){
		result.addObject("mch_id", mc_id);
		result.addObject("jsonMch", jsonMch);
		result.addObject("mchs", mchs.set_discount);
	}
	return result.setView("hierarchy/equity");
} catch (Exception e) {
	Logger.error("跳转失败", e);
	return result.setData(AvailabilityStatus.notAvailable("跳转失败"));
}
