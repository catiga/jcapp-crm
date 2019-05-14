package com.jeancoder.crm.entry.api.member
//
//import com.jeancoder.app.sdk.JC
//import com.jeancoder.app.sdk.source.LoggerSource
//import com.jeancoder.app.sdk.source.RequestSource
//import com.jeancoder.core.http.JCRequest
//import com.jeancoder.core.log.JCLogger
//import com.jeancoder.core.power.CommunicationParam
//import com.jeancoder.core.result.Result
//import com.jeancoder.crm.ready.common.AccountProjectMCStatus
//import com.jeancoder.crm.ready.common.AvailabilityStatus
//import com.jeancoder.crm.ready.constant.JsConstants
//import com.jeancoder.crm.ready.constant.McConstants
//import com.jeancoder.crm.ready.constant.SimpleAjax
//import com.jeancoder.crm.ready.dto.order.McRechargeOrderDto
//import com.jeancoder.crm.ready.dto.order.OrderMcDto
//import com.jeancoder.crm.ready.entity.AccountProjectMC
//import com.jeancoder.crm.ready.entity.MemberCardHierarchy
//import com.jeancoder.crm.ready.entity.MemberCardRule
//import com.jeancoder.crm.ready.entity.OrderMc
//import com.jeancoder.crm.ready.entity.OrderRechargeMc
//import com.jeancoder.crm.ready.service.AccountProjectMcService
//import com.jeancoder.crm.ready.service.MemberCardHierarchyService
//import com.jeancoder.crm.ready.service.MemberCardRuleService
//import com.jeancoder.crm.ready.service.OrderMcService
//import com.jeancoder.crm.ready.service.OrderRechargeService
//import com.jeancoder.crm.ready.util.JackSonBeanMapper
//import com.jeancoder.crm.ready.util.MoneyUtil
//import com.jeancoder.crm.ready.util.RemoteUtil
//import com.jeancoder.crm.ready.util.StringUtil
//import com.jeancoder.crm.ready.order.OrderConstants;
///**
// * 创建会员卡购买订单
// */
//JCRequest request = RequestSource.getRequest();
//JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
//Result result = new Result();
//try {
//	SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':request.getAttribute("token_str"), domain:request.getServerName()]);
//	if(ret_result==null) {
//		return AvailabilityStatus.notAvailable(['trade_server_error','请检查交易服务']);
//	}
//	if(!ret_result.available) {
//		return ret_result;
//	}
//	if(ret_result.data['sid']==null) {
//		return AvailabilityStatus.notAvailable(['counter_set_error','请绑定收银台的门店信息']);
//	}
//	def sid = ret_result.data[0]['sid'];
//	def sname = ret_result.data[0]['sname'];
//	
//	String h_id = request.getParameter("h_id");
//	//String acm_id = request.getParameter("acm_id");
//	、、String recharge_amount = request.getParameter("recharge_amount"); // 元
//	def pid = RemoteUtil.getProj().id;
//	if ( StringUtil.isEmpty(recharge_amount) || StringUtil.isEmpty(acm_id) || StringUtil.isEmpty(h_id) ) {
//		return result.setData(AvailabilityStatus.notAvailable([ JsConstants.input_param_empty, "参数不能为空"] as String[] ));
//	}
//	recharge_amount = MoneyUtil.get_cent_from_yuan(recharge_amount);
//	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(h_id));
//	// 只有升级可以
//	if (mch == null ||  !McConstants._mch_get_upgrade_.equals(mch.getmode)) {
//		return result.setData(AvailabilityStatus.notAvailable([
//			JsConstants.mc_not_hierarchy,
//			"会员卡等级未找到"] as String[] ));
//	}
//	MemberCardRule mcr = MemberCardRuleService.INSTANSE.getItem(mch.mc_id);
//	if (mcr == null ||  (!pid.equals(mcr.pid) && !"1".equals(mcr.pid.toString()))) {
//		return result.setData(AvailabilityStatus.notAvailable([
//			JsConstants.mc_rule_not_found,
//			"会员卡规则未找到"] as String[]));
//	}
//	AccountProjectMC mc = AccountProjectMcService.INSTANSE.getItem(new BigInteger(acm_id));
//	// 创建会员卡订单时会把会员卡状态初始化成锁定状态
//	if (!AccountProjectMCStatus.LOCKING.equals(mc.status)) {
//		return result.setData(AvailabilityStatus.notAvailable([
//			JsConstants.mc_status_found,
//			"会员卡状态错误"] as String[]));
//	}
//	String fen_recharge_amount = MoneyUtil.get_cent_from_yuan(recharge_amount);
//	if (mch.getmode.equals(1)) {
//		//如果是充值升级 需要判断单次充值金额是否和等级金额相等
//		try {
//			def getpay =   Long.parseLong(mch.getpay);
//			def recharge = Long.parseLong(recharge_amount);
//			if (recharge  - getpay < 0) {
//				return result.setData(AvailabilityStatus.notAvailable([
//					JsConstants.recharge_amount_not_meet,
//					"最少充值" + getpay/100] as String[]));
//			}
//		} catch(Exception e) {
//			return result.setData(AvailabilityStatus.notAvailable([
//				JsConstants.limit_number,
//				"配置信息错误"] as String[]));
//		}
//	}
//	OrderMc orderMc = OrderMcService.INSTANSE.admin_create_order(pid,sid,sname, mch, mc.mc_pwd, 1, mc, RemoteUtil.getAuthToken());
//	OrderMcDto orderDto = new OrderMcDto();
//	orderDto.order_no = orderMc.order_no;
//	orderDto.total_amount = orderMc.total_amount;
//	orderDto.pay_amount = orderMc.pay_amount;
//	orderDto.o_c = orderMc.o_c;
//	def order_data = JackSonBeanMapper.toJson(orderDto);
//	order_data = URLEncoder.encode(order_data);
//	order_data = URLEncoder.encode(order_data);
//	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//	params.add(new CommunicationParam("oc", "8000"));
//	params.add(new CommunicationParam("od",order_data));
//	//开始去交易中心注册订单
//	def trade = RemoteUtil.connect(SimpleAjax.class, "trade", "/incall/create_trade", params);
//	println " trade rules : " + JackSonBeanMapper.toJson(trade);
//	return result.setData(AvailabilityStatus.available(trade));
//} catch (Exception e) {
//	Logger.error("创建充值订单失败", e);
//	return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown, "创建失败"] as String[] ));
//}