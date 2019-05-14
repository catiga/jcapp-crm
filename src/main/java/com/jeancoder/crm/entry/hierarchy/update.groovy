package com.jeancoder.crm.entry.hierarchy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil
/**
 * 会员等级更新
 * 
 */
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MemberCardHierarchyService mchs=new MemberCardHierarchyService();
try {
	String id=request.getParameter("rid")
	String mcr_desc = request.getParameter("mcr_desc");
	String mcr_period = request.getParameter("mcr_period");
	String mcr_hindex = request.getParameter("mcr_hindex");
	String mcr_hname  =request.getParameter("mcr_hname");
	String mcr_goods = request.getParameter("mcr_goods");
	String mcr_movie  =request.getParameter("mcr_movie");
	BigInteger project_id =RemoteUtil.getProj().id;
	if (StringUtil.isEmpty(mcr_desc)||StringUtil.isEmpty(mcr_period)||StringUtil.isEmpty(mcr_hindex)||StringUtil.isEmpty(mcr_hname)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	MemberCardHierarchy mch=new MemberCardHierarchy();
	mch.id=new BigInteger(id.toString());
	mch.cr_type_desc=mcr_desc;
	mch.mcr_period=mcr_period;
	mch.hindex=Integer.parseInt(mcr_hindex);
	mch.h_name=mcr_hname;
	mch.goods_htimes = mcr_goods;
	mch.movie_hitmes = mcr_movie;
	if ((mchs.getItem(mch.id).mcRule.pid).equals(project_id)&&DataUtils.isNumber(mcr_hindex)) {
		String resultStr = MemberCardHierarchyService.INSTANSE.updateItem(mch);
		if (!StringUtil.isEmpty(resultStr)) {
			AvailabilityStatus.notAvailable(resultStr)
		}
		return AvailabilityStatus.available();
	}else{
		return result.setData(AvailabilityStatus.notAvailable("更新操作失败"));
	}

	
} catch (Exception e) {
	Logger.error("保存会员卡规则失败", e);
	return result.setData(AvailabilityStatus.notAvailable("更新失败"));
}