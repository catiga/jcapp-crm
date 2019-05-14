package com.jeancoder.crm.entry.hierarchy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.StoreInfoDto
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
String view = "hierarchy/list";
try {
	String mc_id_s = request.getParameter("mc_id");
	def pid = RemoteUtil.getProj().id;
	boolean is_root = RemoteUtil.getProj().root;
	result.addObject("is_root", true);
	
	if (StringUtil.isEmpty(mc_id_s)) {
		return AvailabilityStatus.notAvailable("参数不能为空");
	}
	Long mc_id = Long.parseLong(mc_id_s);
	
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
	page = MemberCardHierarchyService.INSTANSE.getPage(mc_id, page);
	
	// 返回结果
	MemberCardRule  mcr_item = MemberCardRuleService.INSTANSE.getItem(mc_id);
	result.addObject("page", page);
	result.addObject("mcRule", mcr_item);
} catch (Exception e) {
	Logger.error("查询规则列表失败", e);
	result.addObject("error_msg", "查询列表失败")
}

return result.setView(view);