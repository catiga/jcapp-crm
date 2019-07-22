package com.jeancoder.crm.internal.api.order

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.TradeCommon
import com.jeancoder.crm.ready.constant.McDetailConstant
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.market.RechargeMarketDto
import com.jeancoder.crm.ready.dto.order.NotifyObj
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.exception.McrException
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.bring.DXMCBringInstance
import com.jeancoder.crm.ready.mcbridge.ret.MCRet
import com.jeancoder.crm.ready.order.OrderConstants
import com.jeancoder.crm.ready.service.AccountProjectMcDetailService
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.OrderRechargeService
import com.jeancoder.crm.ready.util.CPISCoderTools
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MD5Util
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.NativeUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcTemplate

/*
 * 充值接口通知
 */
JCLogger Logger = LoggerSource.getLogger();
AccountProjectMcService apmc =new AccountProjectMcService();
def t_num = new Date().getTime().toString() + new Random().nextInt(1000).toString();
Result rules = new Result();
String on=""
try {
	def pid = null;
	try {
		pid = new BigInteger(JC.internal.param("pid").toString());
	}catch(Exception ee) {
		pid = RemoteUtil.getProj().id;
	}
	
	String token = TradeCommon.INSTANSE.getRechargeToken();
	String oc = JC.internal.param("oc");  //card_code类型  mm_order_recharge_mc
	on = JC.internal.param("on");  //编号
	def pt = JC.internal.param("pt");  //支付方式
	String sign = JC.internal.param("sign"); //
	String op = JC.internal.param("op"); //
	Logger.info('mc_recharge:{t_num=' + t_num + ',on='+on +  ',oc=' + oc+ ',op=' + op + ',pt='+pt+'}');
	String singKey = "oc="+oc+"&on=" + on+token;
	singKey = MD5Util.getMD5(singKey);
//	if (!singKey.equals(sign)) {
//		Logger.info('{t_num=' + t_num + ',sign=' + sign+ ',singKey='+ singKey +'}');
//		return rules.setData(NotifyObj.build("-1", "检验失败", "", "",null ));
//	}
	if (!StringUtil.isEmpty(op) && !"refund".equals(op)) {
		return rules.setData(NotifyObj.build("-1", "操作类型错误", "", "",null ));
	}
	if ("refund".equals(op)) {
		OrderRechargeMc order = OrderRechargeService.INSTANSE.getByNum(on, pid);
		if (order == null) {
			return rules.setData(NotifyObj.build("-1","订单不存在", "", "",null ));
		}
		if (!OrderConstants._order_status_payed_pub_failed_.equals(order.order_status)) {
			// 充值成功不支持退款
			return rules.setData(NotifyObj.build("-1", "不支持退款", "", "",null ));
		} 
		// 充值失败 分两种情况 一种是有充值记录
		order.order_status = OrderConstants._order_status_drawback_ok_;
		order.refund_time = new Date();
		OrderRechargeService.INSTANSE.updateOrder(order)//修改状态充值成功
		return rules.setData(NotifyObj.succ("退款成功"));
	}
	/******************************先去明细中查询当前交易是否已经记录************************************************/
	List<AccountProjectMcDetail>  detail = AccountProjectMcDetailService.INSTANSE.get_order_on_oc(pid, oc, on);
	if (detail != null && !detail.isEmpty()) {
		return rules.setData();
	}
	/***************************开始充值*******************************************/
	OrderRechargeMc order = OrderRechargeService.INSTANSE.getByNum(on, pid);
	if (order == null) {
		return rules.setData(NotifyObj.build("-1","订单不存在", "", "",null ));
	}
	//先修改订单状态为支付成功
	order.order_status=OrderConstants._order_status_payed_;
	order.c_time = new Timestamp(new Date().getTime());
	order.pay_type = pt;
	OrderRechargeService.INSTANSE.updateOrder(order)//修改状态充值成功
	
	// 充值操作
	try {
		AccountProjectMC account = AccountProjectMcService.INSTANSE.getItem(order.acmid);//通过acmid找到对应的会员卡
		MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(account.mch_id);
		if (mch != null && !"0".equals(mch.getMcRule().outer_type)) {
			def cinema_no = "";
			SimpleAjax ajax = JC.internal.call(SimpleAjax,"ticketingsys", "/store/cinema_by_id", [store_id:order.store_id.toString()])
			if (ajax.available && ajax.data != null && ajax.data.size()  != 0) {
				cinema_no = ajax.data.get(0).store_no;
			}
			
			// 外部会员卡处理
			def recharge_amount = order.total_amount;
			//DXMCBringInstance bring = new DXMCBringInstance(mch.getMcRule());
			def bring =   MCFactory.generate(mch.getMcRule());
			MCRet ret = bring.mc_recharge(account, recharge_amount, recharge_amount, order.order_no, [cinema_no:cinema_no])
			Logger.info("mc_recharge_: 充值结果:"+ JackSonBeanMapper.toJson(ret));
			if (!ret.isSuccess()) {
				// 远程接口充值失败
				Logger.info("mc_recharge_: 充值失败:" +JackSonBeanMapper.toJson(ret));
				order.order_status=OrderConstants._order_status_payed_pub_failed_;//1013
				order.c_time = new Timestamp(new Date().getTime());
				OrderRechargeService.INSTANSE.updateOrder(order);//修改状态
				return rules.setData(NotifyObj.build("-1","充值失败", "1020", "外部会员卡充值失败",null ));
			}
		}
		def ors = OrderRechargeService.INSTANSE.recharge(account, order, pid);
	}catch (Exception ee) {
		order.order_status=OrderConstants._order_status_payed_pub_failed_;//1013
		order.c_time = new Timestamp(new Date().getTime());
		OrderRechargeService.INSTANSE.updateOrder(order);//修改状态
		Logger.error("充值失败", ee);
		return rules.setData(NotifyObj.build("-1","充值失败", "1020", "支付成功充值失败",null ));
	}
	
	
	/***************************赠送卡劵或余额的营销活动*******************************************/
	try {
		AccountProjectMC new_account = AccountProjectMcService.INSTANSE.getItem(order.acmid);//通过acmid找到对应的会员卡
		RechargeMarketDto rechargeMarketDto = NativeUtil.connect(RechargeMarketDto.class, 'market', '/market/mc_recharge_market', ["mobile":new_account.mc_mobile,"pid":pid,"h_id":new_account.mch_id,"recharge_amount":order.pay_amount,"s_id":order.store_id]);
		if(rechargeMarketDto && rechargeMarketDto.obj!=null) {
			//需要进行充值赠送
			try {
				def gift_balance = rechargeMarketDto.obj;
				gift_balance = new BigDecimal(gift_balance).multiply(new BigDecimal('100'));	//转变为分
				
				if(gift_balance > 0) {
					// 构建充值记录
					AccountProjectMcDetail gift_detail = new AccountProjectMcDetail();
					gift_detail.pid =  pid;
					gift_detail.acmid = new_account.id;
					gift_detail.order_no = order.order_no;
					gift_detail.o_c =  OrderConstants.OrderType._recharge_mc_;
					gift_detail.a_time = new Date();
					gift_detail.c_time = new Timestamp(new Date().getTime());
					gift_detail.flag = 0;
					gift_detail.remarks = "";
					gift_detail.amount = gift_balance.toString();
					gift_detail.code = McDetailConstant.gift_order;
					gift_detail.num = CPISCoderTools.serialNum(gift_detail.code);
					JcTemplate.INSTANCE().save(gift_detail);
					//同时更新余额
					new_account.balance = new BigDecimal(new_account.balance).add(gift_balance).toString();
					JcTemplate.INSTANCE().update(new_account);
				}
			} catch(any) {
				Logger.error("参加营销活动赠送会员余额失败" + JackSonBeanMapper.toJson(rechargeMarketDto), any);
			}
		}
		Logger.info "参加充值营销活动结果" + JackSonBeanMapper.toJson(rechargeMarketDto);
	} catch (Exception ee) {
		Logger.error("参加营销活动失败", ee);
	}
	order.order_status=OrderConstants._order_status_taked_;
	order.c_time = new Timestamp(new Date().getTime());
	OrderRechargeService.INSTANSE.updateOrder(order)//修改状态充值成功
	return rules.setData(NotifyObj.succ("充值成功"));
} catch (McrException ee) {
	Logger.error("充值失败", ee);
	return rules.setData(NotifyObj.build("-1","充值失败", "", "",null ));
} catch (Exception e) {
	Logger.error("充值失败", e);
	return rules.setData(NotifyObj.build("-1","充值失败", "", "",null ));
} finally {
	Logger.info('mc_recharge:{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(rules)+'}');
}
