package com.jeancoder.crm.internal.h5.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.McRechargeOrderDto
import com.jeancoder.crm.ready.dto.sys.AuthToken
import com.jeancoder.crm.ready.dto.sys.AuthUser
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
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 创建充值订单
 */
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
 
	def pid = JC.internal.param('pid').toString();
	String mc_num =  JC.internal.param('mc_num');// 会员卡号
	String mch_id = JC.internal.param('h_id').toString();// 等级id
	String op_user_id = JC.internal.param('op_user_id').toString();// 等级id
	String op_user_name = JC.internal.param('op_user_name').toString();// 等级id
	def r_amount = JC.internal.param('r_amount');//充值金额 不能低于h_id 对应的充值金额， r_amount为空时，充值金额为h_id 对应的充值金额
	
	if (StringUtil.isEmpty(mc_num) || StringUtil.isEmpty(mch_id) || StringUtil.isEmpty(pid)) {
		return SimpleAjax.notAvailable("input_param_null,参数为空");
	}
	pid = new BigInteger(pid);
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(mch_id));
	if (mch == null) {
		//return result.setData(AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "等级未找到"] as String[]));
		return SimpleAjax.notAvailable("obj_not_found,会员卡等级不存在或被停用");
	}
	AccountProjectMC mc = AccountProjectMcService.INSTANSE.get_normal_mc_by_num(pid, mc_num);
	if (mc == null ) {
		//return result.setData(AvailabilityStatus.notAvailable([JsConstants.object_not_exist, "会员卡未找到"] as String[]));
		return SimpleAjax.notAvailable("obj_not_found,会员卡信息不存在");
	}

	// 1 判断是否支持充值
	if(!mch.supp_recharge.equals(1)){
		//return result.setData(AvailabilityStatus.notAvailable([JsConstants.not_supported_replenishment , "当前会员卡不支持充值"] as String[]));
		return SimpleAjax.notAvailable("unsupport_op,当前会员卡不支持充值");
	}
	// 2判断网售状态
	if (!mch.flag.equals(0)) {
		//return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown,"该等级不支持网售"] as String[]));
		return SimpleAjax.notAvailable("unsupport_op,该等级不支持网售");
	}
	
	// 自定义充值金额为空时，充值金额取等级对应的充值金额。
	if (!DataUtils.isNumber(mch.getpay)) {
		//return result.setData(AvailabilityStatus.notAvailable([JsConstants.sys_config_error,"配置错误"] as String[]));
		return SimpleAjax.notAvailable("config_error,会员等级配置错误");
	}
	
	String rechargeAmount = null;
	// 充值金额判断
	if (!StringUtil.isEmpty(r_amount)) {
		if (!DataUtils.isNumber(r_amount)) {
			//return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown,"充值金额错误"] as String[]));
			return SimpleAjax.notAvailable("param_error,充值金额参数错误");
		}
		rechargeAmount = r_amount;
	} else {
		rechargeAmount = mch.getpay;
	}
 
	AuthToken token = new AuthToken();
	AuthUser user = new AuthUser();
	user.id = new BigInteger(op_user_id);
	user.name = op_user_name;
	token.user = user;
	
	OrderRechargeMc order = OrderRechargeService.INSTANSE.create_mc_recharge_order(pid,null,null, mc,mch.id,rechargeAmount, "",1,token);

	McRechargeOrderDto dto = new McRechargeOrderDto(order);
	def order_data = JackSonBeanMapper.toJson(order);
	order_data = URLEncoder.encode(order_data, "UTF-8");
	order_data = URLEncoder.encode(order_data, "UTF-8");
//	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//	params.add(new CommunicationParam("oc","8001"));
//	params.add(new CommunicationParam("od",order_data));
	//开始去交易中心注册订单
	//SimpleAjax trade = RemoteUtil.connect(SimpleAjax.class, "trade", "/incall/create_trade", params);
	
	SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'8001','od':order_data, tnum:'', pid:pid]);
	
	if(!trade.available) {
		return SimpleAjax.notAvailable("trade_create_error,交易创建失败，请重试");
	}
	//return result.setData(AvailabilityStatus.available(trade));
	return SimpleAjax.available('', [trade.data, dto]);
} catch (Exception e) {
	Logger.error("创建充值订单失败", e);
	//return result.setData(AvailabilityStatus.notAvailable([JsConstants.unknown,"创建充值订单失败"] as String[]));
	return SimpleAjax.notAvailable("sys_error,创建充值订单失败");
}


