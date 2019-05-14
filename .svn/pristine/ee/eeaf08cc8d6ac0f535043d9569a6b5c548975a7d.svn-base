package com.jeancoder.crm.entry.hierarchy;

import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.DateUtil
import com.jeancoder.crm.ready.util.RemoteUtil

import javax.servlet.http.Cookie

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchy


/**
 * 添加一条 升级规则
 */
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

MemberCardHierarchyService mchs=new MemberCardHierarchyService();
try {

	String mcr_id = request.getParameter("mcr_id");
//	String mcr_hnum = request.getParameter("mcr_hnum");
	String mcr_desc = request.getParameter("mcr_desc");
	String mcr_period = request.getParameter("mcr_period");
	String mcr_hindex = request.getParameter("mcr_hindex");
	String mcr_hname  =request.getParameter("mcr_hname");
	String mcr_goods = request.getParameter("mcr_goods");
	String mcr_movie  =request.getParameter("mcr_movie");
	BigInteger project_id=RemoteUtil.getProj().id;
	if (StringUtil.isEmpty(mcr_id)||StringUtil.isEmpty(mcr_desc)||StringUtil.isEmpty(mcr_period)||StringUtil.isEmpty(mcr_hindex)||StringUtil.isEmpty(mcr_hname)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	
	
	
	MemberCardHierarchy mch=new MemberCardHierarchy();
	mch.mc_id=new BigInteger(mcr_id.toString());
	mch.h_num=generate_coupon_no(project_id);
	mch.cr_type_desc=mcr_desc;
	mch.mcr_period=mcr_period;
	mch.hindex=Integer.parseInt(mcr_hindex);
	mch.h_name=mcr_hname;
	mch.goods_htimes = mcr_goods;
	mch.movie_hitmes = mcr_movie;
	if (!(DataUtils.isNumber(mcr_hindex))) {
		return AvailabilityStatus.notAvailable("输入的参数类型不符合");	
	}
	String resultStr = MemberCardHierarchyService.INSTANSE.saveItem(mch);
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}

	return AvailabilityStatus.available();
	
} catch (Exception e) {
	Logger.error("保存会员卡规则失败", e);
	return result.setData(AvailabilityStatus.notAvailable("添加失败"));
}

String generate_coupon_no(def pid) {
	String buffer = "mc" + pid.toString() + DateUtil.format_yyyyMMdd(new Date()) + this.nextInt(100, 999);
	buffer = buffer.replaceAll("\\-", "");
	return buffer;
}

int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
