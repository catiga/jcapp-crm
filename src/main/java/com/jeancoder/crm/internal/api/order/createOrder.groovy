package com.jeancoder.crm.internal.api.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.common.TradeCommon
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.order.NotifyObj
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.entity.McPreOrderItem
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.OrderMc
import com.jeancoder.crm.ready.exception.McrException
import com.jeancoder.crm.ready.order.OrderConstants
import com.jeancoder.crm.ready.service.AccountBasicService
import com.jeancoder.crm.ready.service.AccountProjectMcDetailService
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MD5Util
import com.jeancoder.crm.ready.util.NativeUtil

/*
 * 创建会员卡购买订单成功通知
 */
JCLogger Logger = LoggerSource.getLogger("mc_createOrder");
AccountBasicService abcs=new AccountBasicService();
AccountProjectMcService apmcs=new AccountProjectMcService();
def t_num = new Date().getTime().toString() + new Random().nextInt(1000).toString();
Result rules = new Result();
String on="";
try {
	def pid   = JC.internal.param("pid");
	String token = TradeCommon.INSTANSE.getCreateToken();
	String oc = JC.internal.param("oc");
	on = JC.internal.param("on");//订单编号
	String sign = JC.internal.param("sign");
	String op = JC.internal.param("op");
	String pt = JC.internal.param("pt");
	Logger.info('{t_num=' + t_num + ',on='+on +  ',oc=' + oc+ ',op=' + op + ',pt='+pt+',pid='+pid+'}');
	pid = new BigInteger(pid.toString());
	String singKey = "oc="+oc+"&on=" + on+token;
	singKey = MD5Util.getMD5(singKey);
	if (!singKey.equals(sign)) {
		Logger.info('{t_num=' + t_num + ',sign=' + sign+ ',singKey='+ singKey +'}');
		return rules.setData(NotifyObj.build("-1","sign 检验失败", "", "",null ));
	}
	//	String on="gmc1808141623481900010";

	if ("refund".equals(op)) {
		println "不支持退单";
		return new Result().setData(AvailabilityStatus.notAvailable("不支持退单"));
	}
	
	/******************************先去明细中查询当前交易是否已经记录************************************************/
	List<AccountProjectMcDetail>  detail = AccountProjectMcDetailService.INSTANSE.get_order_on_oc(pid, oc, on);
	if (detail != null && !detail.isEmpty()) {
		return rules.setData(NotifyObj.build("-1","订单编号重复", "", "",null ));
	}

	/***************************充值成功了*******************************************/
	OrderMc order = OrderMcService.INSTANSE.getByNum(on, pid);//通过订单编号找到对应的订单
	if (order == null) {
		return rules.setData(NotifyObj.build("-1","订单不存在", "", "",null ));
	}
	order.order_status =  OrderConstants._order_status_payed_;
	order.pay_type = pt;
	order.pay_time = new Date();
	OrderMcService.INSTANSE.update(order);//修改状态
	
	if(order.mch_id == null||order.init_pwd==null){
		return rules.setData(NotifyObj.build("-1","订单信息错误", "", "",null ));
	}
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(order.mch_id);
	if (order.ops.equals(0)) {
		// H5订单中没有会员信息 需要取会员卡号
		try {
			List<McPreOrderItem> orderItem= PreMoService._instance_.get_item_by_mch_id(order.mch_id);
			McPreOrderItem item = null;
			if (orderItem==null || orderItem.size() == 0) {
				return rules.setData(NotifyObj.build("-1","会员卡已经发完", "", "",null ));
			}else{
				item = orderItem.get(0);
				item.pub_ss=McConstants.mc_info_item_pub_;//修改会员卡状态为已被使用
				item.pub_order_mc = order.id;
				PreMoService._instance_.updae(item);
			}
			order.pre_item_id = item.id;
			order.card_no = item.cards_identifier;
		} catch (McrException e) {
			OrderMcService.INSTANSE.recharge(order);
			return rules.setData(NotifyObj.build("-1",e.getMessage(), "", "",null ));
		}
	} else {
		McPreOrderItem order_item = PreMoService._instance_.getItem(order.pre_item_id);
		order_item.pub_ss = McConstants.mc_info_item_pub_;//修改会员卡状态为已被使用
		order_item.pub_order_mc = order.id;
		PreMoService._instance_.updae(order_item);
		
	}
	// 会员卡的pid 应该规则对应的pid
	AccountProjectMC account = AccountProjectMcService.INSTANSE.pub_card_by_rule(mch.mcRule.pid, mch, order.basic_id, order.mobile, order, "0");
	order.acmid = account.id;
	// 充值操作
	try {
		OrderMcService.INSTANSE.recharge(order,account,order.basic_id);
		// 修改订单状态为发卡成功
		order.order_status = OrderConstants._order_status_taked_;
		OrderMcService.INSTANSE.update(order);//修改状态
	} catch (Exception ee) {
		Logger.error("充值失败", ee);
		order = OrderConstants._order_status_payed_pub_failed_
		OrderMcService.INSTANSE.update(order);//修改状态为充值成功
		return rules.setData(NotifyObj.build("-1","发卡异常", "", "",null ));
	}
	
	try {
		AccountProjectMC new_account = AccountProjectMcService.INSTANSE.getItem(order.acmid);//通过acmid找到对应的会员卡
		NativeUtil.connect(SysProjectInfo.class, 'market', '/market/mc_recharge_market', ["mobile":new_account.mc_mobile,"pid":pid,"h_id":new_account.mch_id,"recharge_amount":order.pay_amount,"s_id":order.store_id]);
	} catch (Exception ee) {
		Logger.error("参加营销活动失败", ee);
	}
	return rules.setData(NotifyObj.succ("发卡成功"));
} catch (Exception e) {
	Logger.error("发卡异常", e);
	return rules.setData(NotifyObj.build("-1","发卡异常", "", "",null ));
} finally {
	Logger.info('{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(rules)+'}');
}