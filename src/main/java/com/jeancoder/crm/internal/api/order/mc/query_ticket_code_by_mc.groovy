package com.jeancoder.crm.internal.api.order.mc

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.ret.McPayMovieRet
import com.jeancoder.crm.ready.service.AccountProjectMcDetailService
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyDetailService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil

// 外部会员卡购票 查询取票码
AccountProjectMcService apms = new AccountProjectMcService()
MemberCardHierarchyService mchs= new MemberCardHierarchyService();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	def c_id = JC.internal.param("c_id");
	def order_no = JC.internal.param("order_no");
	def oc = JC.internal.param("oc");
	def lock_flag = JC.internal.param("lock_flag");
	def pid = JC.internal.param("pid");
	List<AccountProjectMcDetail> list = AccountProjectMcDetailService.INSTANSE.get_order_on_oc(new BigInteger(pid.toString()), oc, order_no);
	if (list == null || list.isEmpty()) {
		return SimpleAjax.notAvailable("not_outer_mc");
	}
	
	def acmid = list.get(0).acmid;
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.get_normal_mc_by_id(pid, acmid);
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(mc.mch_id);
	if ('0'.equals(mch.mcRule.outer_type)) {
		return SimpleAjax.notAvailable("not_outer_mc");
	}
	def bring = MCFactory.generate(mch.mcRule);
	McPayMovieRet ret = bring.query_ticket_code_by_mc(c_id, order_no, lock_flag);
	return SimpleAjax.available("",ret);
} catch (Exception e) {
	Logger.error("获取可用的会员规则失败", e);
	return SimpleAjax.notAvailable("查询会员卡规则失败")
}

