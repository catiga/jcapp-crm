package com.jeancoder.crm.internal.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.dto.order.MembersBuyDto
import com.jeancoder.crm.ready.dto.order.MembersBuyPage
import com.jeancoder.crm.ready.dto.order.OrderRechargePage
import com.jeancoder.crm.ready.entity.OrderMc
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
MembersBuyPage page = new MembersBuyPage();
page.available = false;
def pn = StringUtil.trim((String)CommunicationSource.getParameter("pn"));
def ps = StringUtil.trim((String)CommunicationSource.getParameter("ps"));
def pid = StringUtil.trim((String)CommunicationSource.getParameter("pid"));
def start_data = StringUtil.trim((String)CommunicationSource.getParameter("start_data"));
def end_data = StringUtil.trim((String)CommunicationSource.getParameter("end_data"));
def order_status=StringUtil.trim((String)CommunicationSource.getParameter("order_status"));

try {
	if (StringUtil.isEmpty(pn)) {
		pn = "1";
	}
	pn = Integer.parseInt(pn);
	if (StringUtil.isEmpty(ps)) {
		ps = "10";
	}
	ps = Integer.parseInt(ps);
	if (pn < 1) {
		pn = 1;
	}
	if (ps < 1) {
		ps = 10;
	}
	if (StringUtil.isEmpty(pid)) {
		pid = null;
	}
	if (StringUtil.isEmpty(start_data)) {
		start_data = "";
	}
	if (StringUtil.isEmpty(end_data)) {
		end_data = "";
	}
	JcPage<OrderMc> orderPage  = new JcPage<OrderMc>();
	orderPage.pn = pn;
	orderPage.ps = ps;
	orderPage = OrderMcService.INSTANSE.get_trade_order(orderPage, pid, start_data, end_data,order_status);
	page.pn = orderPage.pn;
	page.ps = orderPage.ps;
	page.totalCount = orderPage.totalCount;
	List<OrderMc> list = new ArrayList<OrderMc>();
	for (OrderMc order: orderPage.result) {
		MembersBuyDto  orderMcDto = new MembersBuyDto();
		orderMcDto.total_amount = order.total_amount.toString();
		orderMcDto.pid = order.pid;
		orderMcDto.order_no = order.order_no;
		orderMcDto.a_time = order.a_time.getTime();
		orderMcDto.card_no = order.card_no;
		orderMcDto.pay_type = order.pay_type;
		orderMcDto.ops = order.ops;
		orderMcDto.puid = order.puid;
		orderMcDto.puname = order.puname;
		list.add(orderMcDto);
	}
	page.available = true;
	page.result = list;
} catch (e) {
	Logger.error("会员充值订单查询失败",e);
	page.available = false;
	page.msg = "查询失败";
}
return page;