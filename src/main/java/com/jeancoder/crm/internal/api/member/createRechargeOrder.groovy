package com.jeancoder.crm.internal.api.member

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.McRechargeOrderDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.OrderRechargeService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 创建充值订单
 */

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

String card_code = JC.internal.param("card_code");// 二维码字符串
String h_id = JC.internal.param("h_id"); // id

def sid = JC.internal.param('sid');
def sname = JC.internal.param('sname');
def log_id = JC.internal.param('log_id');

def pid = JC.internal.param('pid');
try {
	pid = new BigInteger(pid);
} catch(any) {
	return SimpleAjax.notAvailable('pid_error,pid参数错误');
}

Result result = new Result();
try {
	if (StringUtil.isEmpty(h_id) || StringUtil.isEmpty(card_code) ) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_null,"参数为空"] as String[]));
	}
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(h_id));
	if (mch == null) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "等级未找到"] as String[]));
	}
	card_code = StringUtil.trim(card_code);
	if ( StringUtil.isEmpty(card_code)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.input_param_empty, "参数不能为空"] as String[] ));
	}
	card_code = AccountProjectMCUtil.getMcNum(card_code);
	
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.get_normal_mc_by_num(pid, card_code);
	if (mc == null ) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "会员卡未找到"] as String[]));
	}
	
    // 1 判断是否支持充值
	if(!mch.supp_recharge.equals(1)){
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.not_supported_replenishment , "当前会员卡不支持充值"] as String[] ));
	}
	if (!DataUtils.isNumber(mch.getpay)) {
		return result.setData(AvailabilityStatus.notAvailable([JsConstants.sys_config_error,"会员卡配置错误"] as String[]));
	}
	OrderRechargeMc order = OrderRechargeService.INSTANSE.create_mc_recharge_order(pid,sid,sname, mc,mch.id,mch.getpay, "",1,GlobalHolder.getToken());
	McRechargeOrderDto dto = new McRechargeOrderDto(order);
	def order_data = JackSonBeanMapper.toJson(order);
	order_data = URLEncoder.encode(order_data);
	order_data = URLEncoder.encode(order_data);
	
//	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//	params.add(new CommunicationParam("oc","8001"));
//	params.add(new CommunicationParam("od",order_data));
//	params.add(new CommunicationParam("log_id",log_id));
//	//开始去交易中心注册订单
//	SimpleAjax trade = RemoteUtil.connect(SimpleAjax.class, "trade", "/incall/create_trade", params);
	
	SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'8001','od':order_data, log_id:log_id, pid:pid]);
	
	return result.setData(AvailabilityStatus.available(trade));
} catch (Exception e) {
	Logger.error("创建充值订单失败", e);
	return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown, "创建失败"] as String[]));
}