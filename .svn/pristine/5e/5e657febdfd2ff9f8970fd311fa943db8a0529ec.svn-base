package com.jeancoder.crm.internal.api.order

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.McDetailConstant
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.dto.ItemDto
import com.jeancoder.crm.ready.mcbridge.ret.McPayMovieRet
import com.jeancoder.crm.ready.mcbridge.ret.TicketRefundRet
import com.jeancoder.crm.ready.service.AccountProjectMcDetailService
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.CPISCoderTools
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.StringUtil

/**
 外部会员卡 退票接口
**/
JCLogger Logger = LoggerSource.getLogger("outer_refund:");
SimpleAjax ajax = null
def num =   new Date().getTime();
num = num.toString() + new Random().nextInt(1000).toString();
try {
	String pid  =  JC.internal.param("pid").toString();
	String order_no = JC.internal.param("order_no");//  
	String oc = JC.internal.param("oc");//  
	String c_id = JC.internal.param("c_id");//  
	String ticket_flag1 = JC.internal.param("ticket_flag1");//  
	String ticket_flag2 = JC.internal.param("ticket_flag2");//  
	String seats = JC.internal.param("seats");//  
	String lock_flag = JC.internal.param("lock_flag");// 
	
	Logger.info('outer_refund:{num= '+num+ ' , parameter pid:'+ pid + ' order_no:' + order_no + ' order_no:'+oc + ' c_id:'+c_id +'}');
	List<AccountProjectMcDetail> list = AccountProjectMcDetailService.INSTANSE.get_order_on_oc(new BigInteger(pid.toString()), oc, order_no);
	if (list == null || list.isEmpty()) {
		return SimpleAjax.notAvailable("会员卡支付记录未找到");
	}
	def acmid = list.get(0).acmid;
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.get_normal_mc_by_id(pid, acmid);
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(mc.mch_id);
	if ('0'.equals(mch.mcRule.outer_type)) {
		return SimpleAjax.notAvailable("not_outer_mc");
	}
	def bring = MCFactory.generate(mch.mcRule);
	TicketRefundRet ret = bring.tickes_refund_mc(c_id, order_no, ticket_flag1, ticket_flag2, seats, lock_flag);
	return SimpleAjax.available("",ret)
} catch (Exception e) {
	ajax = SimpleAjax.notAvailable("退票失败");
	Logger.error("", e);
	return  ajax;
} finally {
	Logger.info('outer_refund:{num= '+num+ ' , rules:'+ JackSonBeanMapper.toJson(ajax)+'}');
}