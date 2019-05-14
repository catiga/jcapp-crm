package com.jeancoder.crm.entry.mc

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.dto.mc.MembersBuyDto
import com.jeancoder.crm.ready.dto.mc.MembersBuyPage
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.util.DateUtil
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MembersBuyPage page = new MembersBuyPage();
page.available = false;
try {
	String pn = request.getParameter("pn");
	String card_no = request.getParameter("card_no");
	String date_time = request.getParameter("date_time");
	String trade_type = request.getParameter("trade_type");
	if (StringUtil.isEmpty(pn)) {
		pn = "1";
	}

	JcPage<AccountProjectMcDetail> orderPage = new JcPage<AccountProjectMcDetail>();
	orderPage.pn = Integer.parseInt(pn);
	orderPage.ps = Integer.parseInt("10");
	
	//card_no查到acm_id在mm_account_project_mc表
	AccountProjectMC  acm = AccountProjectMcService.INSTANSE.getItem(new BigInteger(card_no));
	//再用 acm_id查到账户明细在 mm_account_project_mc_detail表
	if(acm.equals(null)||acm.equals("")){
		return result.setData(AvailabilityStatus.notAvailable("查询会员信息失败"));
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
		orderMcDto.d_num = acm_d.d_num;
		orderMcDto.pay_type = "";
		//orderMcDto.total_amount = acm_d.total_amount.toString();
		list.add(orderMcDto);
	}
	page.available = true;
	page.result = list;
	result.addObject("card_no", card_no);
} catch (e) {
	Logger.error("账户明细查询失败",e);
	page.available = false;
	page.msg = "查询失败";
}
result.addObject("page", page);
result.addObject("data", new DateUtil());
result.setView("mc/index");
return result;