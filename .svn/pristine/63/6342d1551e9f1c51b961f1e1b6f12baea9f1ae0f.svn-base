package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 保存积分规则
 */
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String mc_id = request.getParameter("mc_id");
	String mc_jf_supp = request.getParameter("mc_jf_supp");
	String mc_jf_a_r = request.getParameter("mc_jf_a_r");
	String mc_jf_c_r = request.getParameter("mc_jf_c_r");
	Long project_id = RemoteUtil.getProj().id;
	
	if(mc_id.equals(null)||mc_id.equals("")) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	if(String.valueOf(project_id).equals(null)||String.valueOf(project_id).equals("")){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	boolean supp_jf = true;
	if(StringUtil.isEmpty(mc_jf_supp)) {
		mc_jf_supp = 1;
	} else {
		if(!(mc_jf_supp.equals("0")||mc_jf_supp.equals("1"))) {
			mc_jf_supp = 1;
		}
	}
	if(mc_jf_supp.equals("0")) {
		supp_jf = false;
	}
	if(StringUtil.isEmpty(mc_jf_a_r)) {
		mc_jf_a_r = "1/1";
	}
	if(StringUtil.isEmpty(mc_jf_c_r)) {
		mc_jf_c_r = "100/1";
	}
	
	if(Quantity(mc_jf_a_r)>1||Quantity(mc_jf_c_r)>1){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	String[] arr_mc_jf_a_r = mc_jf_a_r.split("/");
	String[] arr_mc_jf_c_r = mc_jf_c_r.split("/");
	if(arr_mc_jf_a_r==null||arr_mc_jf_a_r.length!=2||arr_mc_jf_c_r==null||arr_mc_jf_c_r.length!=2) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	} else {
		try {
			Integer a_r_1 = Integer.valueOf(arr_mc_jf_a_r[0]);
			Integer a_r_2 = Integer.valueOf(arr_mc_jf_a_r[1]);
			Integer a_c_1 = Integer.valueOf(arr_mc_jf_c_r[0]);
			Integer a_c_2 = Integer.valueOf(arr_mc_jf_c_r[1]);
			mc_jf_a_r = Math.abs(a_r_1) + "/" + Math.abs(a_r_2);
			mc_jf_c_r = Math.abs(a_c_1) + "/" + Math.abs(a_c_2);
		} catch(NumberFormatException e) {
			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
		}
	}
	MemberCardRule mc_rule = MemberCardRuleService.INSTANSE.getItem(new BigInteger(mc_id.toString()),new BigInteger(project_id.toString()));
	if(mc_rule.equals(null)) {
		return AvailabilityStatus.notAvailable(JsConstants.mc_not_found);
	}
	SysProjectInfo project = RemoteUtil.getProj();
	mc_rule.id = new BigInteger(mc_id.toString());
	mc_rule.supp_jf = Integer.parseInt(mc_jf_supp);
	mc_rule.def_jf_add_ratio = mc_jf_a_r;
	mc_rule.def_jf_con_ratio = mc_jf_c_r;
	def mc_r = MemberCardRuleService.INSTANSE.doupdate_project_mcr(mc_rule);
	return result.setData(AvailabilityStatus.available(mc_r));
	
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}

public int Quantity(String quantity){
	char[] quantityc = quantity.toCharArray();
	int num = 0;
	for (quantitycj in quantityc) {
		if(quantitycj=='/'){
			num++;
		}
		if(num>2){
			break;
		}
	}
	return num;
}