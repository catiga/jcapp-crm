package com.jeancoder.crm.internal.order

import java.util.List

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.dto.order.OrderRechargeMcDto
import com.jeancoder.crm.ready.dto.order.OrderRechargePage
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.OrderRechargeService
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage
 
JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
OrderRechargePage page = page = new OrderRechargePage();
page.available = false;
def pn = StringUtil.trim((String)CommunicationSource.getParameter("pn"));
def ps = StringUtil.trim((String)CommunicationSource.getParameter("ps"));
def pid = StringUtil.trim((String)CommunicationSource.getParameter("pid"));
def mobile_num = StringUtil.trim((String)CommunicationSource.getParameter("mobile_num"));
def start_data = StringUtil.trim((String)CommunicationSource.getParameter("start_data"));
def end_data = StringUtil.trim((String)CommunicationSource.getParameter("end_data"));
def order_status= StringUtil.trim((String)CommunicationSource.getParameter("order_status"));
def mch_id = "";

try {
	if (StringUtil.isEmpty(pn)) {
		pn = "1";
	}
	if (StringUtil.isEmpty(ps)) {
		ps = "10";
	}
	pn = Integer.parseInt(pn);
	ps = Integer.parseInt(ps);
	if (StringUtil.isEmpty(pid)) {
		pid = null;
	}
	if (StringUtil.isEmpty(start_data)) {
		start_data = "";
	}
	if (StringUtil.isEmpty(end_data)) {
		end_data = "";
	}
	JcPage<OrderRechargeMc> orderPage  = new JcPage<OrderRechargeMc>();
	orderPage.pn = pn;
	orderPage.ps = ps;
	if(!StringUtil.isEmpty(mobile_num)){
		List<AccountProjectMC> mcList = AccountProjectMcService.INSTANSE.queryByMobile(new BigInteger(pid), mobile_num);
		AccountProjectMC mc;
		if(null == mcList){
			mch_id = "";
		}else{
			mc = mcList.get(0);
		}
		List<MemberCardHierarchy> mchList;
		if(null != mc){
			 mchList = MemberCardHierarchyService.INSTANSE.getAllByMcId(mc.id);
		}
		if(null == mchList){
			mch_id = "";
		}
		if(null != mchList){
			mch_id = mchList.get(0).id;
		}
	}
	orderPage = OrderRechargeService.INSTANSE.get_trade_order(orderPage, pid, mch_id,start_data, end_data,order_status);
	page.pn = orderPage.pn;
	page.ps = orderPage.ps;
	page.totalCount = orderPage.totalCount;
	List<OrderRechargeMc> list = new ArrayList<OrderRechargeMc>();
	for (OrderRechargeMc order: orderPage.result) {
		OrderRechargeMcDto  orderRechargeMcDto = new OrderRechargeMcDto();
		orderRechargeMcDto.total_amount = order.total_amount.toString();
		orderRechargeMcDto.pid = order.pid;
		orderRechargeMcDto.order_no = order.order_no;
		orderRechargeMcDto.a_time = order.a_time.getTime();
		orderRechargeMcDto.card_no = order.card_no;
		orderRechargeMcDto.pay_type = order.pay_type;
		orderRechargeMcDto.ops = order.ops;
		orderRechargeMcDto.puid = order.puid;
		orderRechargeMcDto.puname = order.puname;
		list.add(orderRechargeMcDto);
	}
	page.available = true;
	page.result = list;
} catch (e) {
	Logger.error("会员充值订单查询失败",e);
	page.available = false;
	page.msg = "查询失败";
}
return page;