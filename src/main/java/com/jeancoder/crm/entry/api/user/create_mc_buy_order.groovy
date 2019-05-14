package com.jeancoder.crm.entry.api.user
//
//import com.jeancoder.app.sdk.source.LoggerSource
//import com.jeancoder.app.sdk.source.RequestSource
//import com.jeancoder.core.http.JCRequest
//import com.jeancoder.core.log.JCLogger
//import com.jeancoder.core.result.Result
//import com.jeancoder.crm.ready.common.AvailabilityStatus
//import com.jeancoder.crm.ready.constant.McConstants
//import com.jeancoder.crm.ready.dto.AccountBasicDto
//import com.jeancoder.crm.ready.dto.OrderPayObj
//import com.jeancoder.crm.ready.entity.AccountProjectMC
//import com.jeancoder.crm.ready.entity.MemberCardHierarchy
//import com.jeancoder.crm.ready.entity.MemberCardRule
//import com.jeancoder.crm.ready.entity.OrderMc
//import com.jeancoder.crm.ready.service.AccountProjectMcService
//import com.jeancoder.crm.ready.service.MemberCardHierarchyService
//import com.jeancoder.crm.ready.service.OrderMcService
//import com.jeancoder.crm.ready.util.DataUtils
//import com.jeancoder.crm.ready.util.OrderUtil
//import com.jeancoder.crm.ready.util.RemoteUtil
//
///**
// * 创建购买会员卡订单
// * H5
// */
//JCRequest request = RequestSource.getRequest();
//JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
//Result result = new Result();
//try {
//	def mch_id = request.getParameter("mch_id"); // 会员卡等级id
//	def opd = request.getParameter("opd"); 
//	
//	def pid = RemoteUtil.getProj().id;
//	AccountBasicDto account = RemoteUtil.getAccountBasic();
//	MemberCardHierarchy mch =null;
//	if(mch_id!=null) {
//		mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(mch_id));
//	}
//	
//	if(mch==null|| !pid.equals(mch.mcRule.pid)) {
//		return result.setData(AvailabilityStatus.notAvailable("mch_id=" + mch_id + " not found"));
//	}
//		
//	def mc_rule = mch.mcRule;
////	SysMerchantInfo merchant = project.getMerchant();
//		
////	AccountMCService accountMCService = (AccountMCService)wac.getBean("accountMCService");
//	//这里校验／计算卡规则
//	//首先判断该用户是否已经有了该会员卡
//	//List<AccountProjectMC> all_mchs = accountMCService.get_all_account_mc(project, this.current_account);
//	List<AccountProjectMC> all_mchs = AccountProjectMcService.INSTANSE.get_all_account_mc(pid, RemoteUtil.getAccountBasic().id);
//	if(all_mchs!=null&&!all_mchs.isEmpty()) {
//		for(AccountProjectMC apmc : all_mchs) {
//			MemberCardHierarchy r_mch = apmc.getMch();
//			if(r_mch!=null) {
//				if(r_mch.getId().equals(mch_id)) {
//					//代表用户已经有了这张卡，直接返回重复
//					return result.setData(AvailabilityStatus.notAvailable("已经拥有会员卡"));
//				}
//			}
//		}
//	}
//	if(!mch.getGetmode().equals(McConstants._mch_get_mode_direct_)) {
//		//不属于可直接获取状态，返回状态非法
//		return result.setData(AvailabilityStatus.notAvailable("会员卡状态非法"));
//	}
//	
//	if(opd==null||opd.toString().trim().equals("")) {
//		//设置默认密码
//		opd = "987123";
//	}
//	
//	OrderMc order_mc = OrderMcService.INSTANSE.create_order(pid, account, mch, opd, 0, null);
//	OrderPayObj opo = new OrderPayObj(order_mc.id, order_mc.pay_amount);
//	
//	if("0".equals(order_mc.pay_amount())){
//		//需要支付金额为0元，则直接领取储值卡
//		String check_mc_pwd = order_mc.init_pwd();
//		if(check_mc_pwd==null||check_mc_pwd.trim().equals("")) {
//			check_mc_pwd = this.nextInt(100000, 999999) + "";
//		}
//		if(OrderUtil.is_order_payed(order_mc.order_status())&&order_mc.getAcmid()==null) {
////			//支付成功并且未发卡的订单，执行发卡操作
////			//可以发卡
//			AccountProjectMC apmc = null;
//			try {
//				String gift_balance = order_mc.gift_amount();
//				if(!DataUtils.isNumber(gift_balance)) {
//					gift_balance = "0";
//				}
//				apmc = AccountProjectMcService.INSTANSE.pub_card_by_rule(pid, mch, account, check_mc_pwd, gift_balance);
//			} catch(Exception e) {
//				Logger.error("", e);
//				return result.setData(AvailabilityStatus.notAvailable(e.getMessage()));
//			}
//			if(apmc!=null) {
//				AccountProjectMcService.INSTANSE.de
//				//更新会员卡订单信息
//				orderMcService.delivery(order_mc, apmc);
//			}
////			return new McBuyOrderDto(order_mc);
//		}
//	}  else {
////		//领取充值卡
////		return new McBuyOrderDto(order_mc);
//	}
//} catch (Exception e) {
//	Logger.error("创建会员卡订单失败", e);
//	return result.setData(AvailabilityStatus.notAvailable("创建购买会员卡订单失败"));
//}
//
//int nextInt(final int min, final int max){
//	Random rand= new Random();
//	int tmp = Math.abs(rand.nextInt());
//	return tmp % (max - min + 1) + min;
//}
//	