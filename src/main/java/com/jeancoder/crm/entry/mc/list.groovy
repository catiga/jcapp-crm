package com.jeancoder.crm.entry.mc

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	BigInteger project_id = RemoteUtil.getProj().getId();
	Long storeInfoId = null;
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	String like = StringUtil.trim(request.getParameter("like"));
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
	JcPage<AccountProjectMC> page = new JcPage<AccountProjectMC>();
	page.setPn(pn);
	page.setPs(ps);
	// 返回结果
	page = AccountProjectMcService.INSTANSE.getPageByLike(page,project_id, like)
	result.addObject("page", page);
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	result.addObject("error_msg", "查询列表失败")
}
return result.setView("mc/list");