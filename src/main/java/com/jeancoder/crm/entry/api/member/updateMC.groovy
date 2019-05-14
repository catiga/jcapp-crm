package com.jeancoder.crm.entry.api.member

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.dto.mc.McHierchHiDto
import com.jeancoder.crm.ready.dto.mc.AccountMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.OrderRechargeService
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil


/**
 * 更新会员卡
 */
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	//BigInteger pid = GlobalHolder.proj.id;
	String acm_id = request.getParameter("acm_id");// id
	String mc_name = request.getParameter("mc_name");//会员姓名
	String mc_mobile = request.getParameter("mc_mobile");// 手机号
	String id_card = request.getParameter("id_card");// 身份证号码
	if (StringUtil.isEmpty(mc_name) || StringUtil.isEmpty(mc_mobile) || StringUtil.isEmpty(id_card)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_empty, "参数不能为空"] as String[]));
	}
	//println  getEncoding(mc_name);
	mc_name = URLDecoder.decode(mc_name);
	mc_name = URLDecoder.decode(mc_name);
	AccountProjectMC item = new AccountProjectMC();
	item.id = new BigInteger(acm_id);
	item.mc_name = mc_name;
	item.mc_mobile = mc_mobile;
	item.id_card = id_card;
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.update_account_project(item);
	if (mc == null) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.mc_not_found, "会员卡未找到"] as String[]));
	}
	AccountMcDto dto = new AccountMcDto(mc);
	return result.setData(AvailabilityStatus.available(dto));
} catch (Exception e) {
	Logger.error("更新会员资料失败", e);
	return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown, "更新失败"] as String[]));
}


public static String getEncoding(String str) {
	String encode = "GB2312";
   try {
	   if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
			String s = encode;
		   return s;      //是的话，返回“GB2312“，以下代码同理
		}
	} catch (Exception exception) {
	}
	encode = "ISO-8859-1";
   try {
	   if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
			String s1 = encode;
		   return s1;
		}
	} catch (Exception exception1) {
	}
	encode = "UTF-8";
   try {
	   if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
			String s2 = encode;
		   return s2;
		}
	} catch (Exception exception2) {
	}
	encode = "GBK";
   try {
	   if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
			String s3 = encode;
		   return s3;
		}
	} catch (Exception exception3) {
	}
   return "";
}
