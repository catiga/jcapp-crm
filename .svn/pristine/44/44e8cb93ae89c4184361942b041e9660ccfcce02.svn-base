package com.jeancoder.crm.internal.h5.user

import java.sql.Timestamp
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.bring.DXMCBringInstance
import com.jeancoder.crm.ready.mcbridge.constants.MCBringConstants
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.ret.MCRet
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcTemplate

/**
 * 解绑会员卡
 */
JCLogger  Logger  = LoggerSource.getLogger();
try {
	def   pid = JC.internal.param("pid");
	def   acmid = JC.internal.param("acmid");
	def   mc_pwd = JC.internal.param("mc_pwd");
	def   s_id = JC.internal.param("s_id");
	if (StringUtil.isEmpty(pid)  || StringUtil.isEmpty(acmid)  || StringUtil.isEmpty(mc_pwd)  || StringUtil.isEmpty(s_id)) {
		return  SimpleAjax.notAvailable("参数不能为空");
	}
	AccountProjectMC account = AccountProjectMcService.INSTANSE.getItem(new BigInteger(acmid.toString()))
	if (account == null) {
		return  SimpleAjax.notAvailable("未找到会员卡");
	}
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(account.mch_id);
	if (mch == null) {
		return  SimpleAjax.notAvailable("未找到等级");
	}
	MemberCardRule rule = mch.mcRule;
	SimpleAjax ajax = JC.internal.call(SimpleAjax,"ticketingsys", "/store/cinema_by_id", [store_id:s_id])
	if (!ajax.available) {
		return ajax;
	}
	if (ajax.data == null || ajax.data.size()  == 0) {
		return SimpleAjax.notAvailable("请添加门店")
	}
	
	DXMCBringInstance bring = new DXMCBringInstance(rule);
	MCAuthFix fix = new MCAuthFix();
	fix.pwd = mc_pwd;
	fix.idnum = account.mc_num;
	fix.mobile = account.mc_mobile;
	//校验会员卡信息
	MCRet ret = bring.auth_mc(rule, ajax.data.get(0).store_no, account.mc_num, mc_pwd, fix, [:])
	if(!ret.isSuccess()) {
		if (MCBringConstants._mcb_failed_need_store_.equals(ret.code)) {
			return  SimpleAjax.notAvailable("请设置门店");
		}
		return SimpleAjax.notAvailable(ret.code+":"+ret.msg)
	}
	account.flag =-1;
	account.c_time = new Timestamp(new Date().getTime());
	JcTemplate.INSTANCE().update(account);
	return  SimpleAjax.available();
} catch (Exception e) {
	Logger.error("解绑会员卡失败", e);
	return  SimpleAjax.notAvailable("解绑会员卡失败");
}
