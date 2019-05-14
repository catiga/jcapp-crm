package com.jeancoder.crm.entry

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.dto.SysDicConfig
import com.jeancoder.crm.ready.entity.DicCommonConfig
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.DicCommonConfigService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage


Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCResponse response = ResponseSource.getResponse();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try { 
	Long project_id = RemoteUtil.getProj().getId();
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	if (StringUtil.isEmpty(pn_s)) {
		pn_s = "1";
	}
	if (StringUtil.isEmpty(ps_s)) {
		ps_s = "10";
	}
	
	Integer pn = Integer.parseInt(pn_s);
	if (pn < 1) {
		pn = 1;
	}
	Integer ps = Integer.parseInt(ps_s);
	JcPage<MemberCardHierarchy> page = new JcPage<MemberCardHierarchy>();
	page.setPn(pn);
	page.setPs(ps);
	// 返回结果
	page = MemberCardRuleService.INSTANSE.getList(project_id,page);
	List<DicCommonConfig> dcc = DicCommonConfigService.INSTANSE.find_support_configs(SysDicConfig.MEMBERCARD.key());
	result.addObject("page", page);
	result.addObject("all_supp_mcs", dcc);
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	result.addObject("error_msg", "查询列表失败")
}
return result.setView("test");