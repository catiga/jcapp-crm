package com.jeancoder.crm.internal.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.mc.MembersBuyDto
import com.jeancoder.crm.ready.dto.mc.MembersBuyPage
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage


MembersBuyPage page = new MembersBuyPage();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String pn = JC.internal.param("pn");
	String card_no = JC.internal.param("card_no");
	String date_time = JC.internal.param("date_time");
	String trade_type = JC.internal.param("o_c"); // 订单类型 8001 8000 1000  2000 20001 5000
	if (StringUtil.isEmpty(pn)) {
		pn = "1";
	}
	JcPage<AccountProjectMcDetail> orderPage = new JcPage<AccountProjectMcDetail>();
	orderPage.pn = Integer.parseInt(pn);
	orderPage.ps = Integer.parseInt("10");

	//card_no查到acm_id在mm_account_project_mc表
	AccountProjectMC  acm = OrderMcService.INSTANSE.get_trade_acmid_list(card_no);
	//再用 acm_id查到账户明细在 mm_account_project_mc_detail表
	if(acm.equals(null)||acm.equals("")){
		return SimpleAjax.available();
	}
	orderPage = OrderMcService.INSTANSE.get_trade_order_list(orderPage,acm.id,date_time,trade_type);
	
	page.pn = orderPage.pn;
	page.ps = orderPage.ps;
	page.totalCount = orderPage.totalCount;
	List<AccountProjectMcDetail> list = new ArrayList<AccountProjectMcDetail>();
	for (AccountProjectMcDetail acm_d: orderPage.result) {
		MembersBuyDto  orderMcDto = new MembersBuyDto();
		orderMcDto.order_no = acm_d.order_no;
		orderMcDto.a_time = acm_d.a_time.getTime();
		orderMcDto.amount = acm_d.amount;
		orderMcDto.o_c = acm_d.o_c;
		orderMcDto.code = acm_d.code;
		orderMcDto.pay_type = "";
		//orderMcDto.total_amount = acm_d.total_amount.toString();
		list.add(orderMcDto);
	}
	page.available = true;
	page.result = list;
	return SimpleAjax.available("",page);
} catch (e) {
	Logger.error("账户明细查询失败",e);
	page.available = false;
	page.msg = "查询失败";
	return SimpleAjax.notAvailable("账户明细查询失败");
}