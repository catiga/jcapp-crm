package com.jeancoder.crm.ready.mcbridge

import java.sql.Timestamp

import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.CommonOrder
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.constants.MCBringConstants
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.service.AccountProjectMcService

//class MCBringRechargeInstance implements MCBring  {
////	
////	@Override
////	public MCPrice compute_mc_price(IOrder deduction, AccountProjectMC mct) {
////		//获取该会员卡的手续费信息
////		MemberCardHierarchy mch = mct.getMch();
////		String per_handle_fee = mch.handle_fee();
////		String handle_fee_by = mch.handle_fee_by();
////		
////		MCPrice ret_price = new MCPrice();
////		String real_price = deduction.getTotalAmount();
////		
////		//是否允许低于最低票价
////		Integer allow_bellow_low_price = mch.getBelop();
////		//是否允许周末使用
////		Integer allow_weekend_use = mch.getHodu();
////		
////		boolean could_use = true;
////		if(allow_weekend_use==null||allow_weekend_use.equals(0)) {
////			//说明不允许周末使用
////			//判断今天是否是周末
////			Calendar c = Calendar.getInstance();
////			Integer today = c.get(Calendar.DAY_OF_WEEK);
////			if(today.equals(1)||today.equals(7)) {
////				//说明今天是周末
////				could_use = false;
////			}
////		}
////		
////		if(could_use) {
////			String discount = mct.getDiscount();
////			if(discount==null) {
////				if(mct.getMch()!=null) {
////					discount = mct.getMch().cr_discount();
////				}
////			}
////			if(discount!=null) {
////				real_price = MoneyUtil.divide(MoneyUtil.multiple(real_price, discount), "100");
////			}
////		}
////		ret_price.setPrice(real_price);
////		
////		String total_handle_fee = null;
////		
////		if(deduction instanceof TcssOrder) {
////			//判断是否允许低于最低票价
////			if(allow_bellow_low_price!=null&&allow_bellow_low_price.equals(1)) {
////				//说明允许低于最低票价
////				//不做任何事情
////				
////			} else {
////				//说明不允许低于最低票价
////				SysProjCinemaService sysProjCinemaService = (SysProjCinemaService)wac.getBean("sysProjCinemaService");
////				if(sysProjCinemaService!=null) {
////					PlanInfo plan = sysProjCinemaService.get_plan_by_id(((TcssOrder)deduction).getPlanId());
////					String low_price = plan.getPlanBasic().getPriceFloor();
////					if(MoneyUtil.add(real_price, "-" + low_price).indexOf("-") > -1) {
////						//说明低于最低票价了，则重新对 real_price 赋值
////						real_price = low_price;
////						ret_price.setPrice(real_price);
////					}
////				}
////			}
////			
////			if(handle_fee_by==null) {
////				//默认为每张影票收取
////				handle_fee_by = McConstants._mch_handle_fee_by_per_goods_;
////			}
////			//影票类订单手续费
////			Integer ticket_num = ((TcssOrder)deduction).getTicketSum();
////			if(per_handle_fee==null) {
////				total_handle_fee = ((TcssOrder)deduction).getHandleFee();
////				per_handle_fee = MoneyUtil.divide(total_handle_fee, ticket_num.toString());
////			} else {
////				if(handle_fee_by.equals(McConstants._mch_handle_fee_by_per_goods_)) {
////					total_handle_fee = MoneyUtil.multiple(per_handle_fee, ticket_num.toString());
////				} else {
////					total_handle_fee = per_handle_fee;
////					//这种情况需要重新定义每个单品的手续费，以保证汇总
////					per_handle_fee = MoneyUtil.divide(per_handle_fee, ticket_num.toString());
////				}
////			}
////			
////			MarketService marketService = (MarketService)wac.getBean("marketService");
////			//寻找需要自动匹配的会员卡活动
////			List<MarketRule> mc_market_rules = marketService.get_available_markets(deduction.getProject().getId(), "2200", 0);
////			if(mc_market_rules!=null&&!mc_market_rules.isEmpty()) {
////				MarketJsonRuleWithMC mc_rule = null;
////				MarketRule m_r = null;
////				for(MarketRule rule : mc_market_rules) {
////					try {
////						List<KV> kv_mc_list = ((MarketJsonRuleWithMC)rule.getRule()).getMc_list();
////						if(kv_mc_list!=null&&!kv_mc_list.isEmpty()) {
////							for(KV kv : kv_mc_list) {
////								if(kv.getK().equals(mct.getMch().getId().toString())) {
////									mc_rule = (MarketJsonRuleWithMC)rule.getRule();
////									m_r = rule;
////									break;
////								}
////							}
////						}
////					}catch(Exception e) {}
////				}
////				if(mc_rule!=null) {
////					//TODO 执行计算逻辑
////					List<MarketJsonRuleItem> marketItems = marketService.getAllMarketItemByOrder(deduction, deduction.getOc(), deduction.getProject(), Double.valueOf(total_handle_fee).longValue(),MarketConstants._market_type_tc_ss_for_member_card_);
////					for(MarketJsonRuleItem marketItem : marketItems){
////						if(marketItem.getMarketId().equals(m_r.getMarket().getId())){
////							//匹配
////							MarketPriceDto marketPrice = marketService.orderAmountCalc(deduction, deduction.getOc(), marketItem.getId(), deduction.getProject(), mct.getBasic(), Double.valueOf(per_handle_fee).longValue(), PayConstants._order_pay_type_pay_mc_,MarketConstants._market_type_tc_ss_for_member_card_);
////							if(marketPrice.isSuccess()){
////								ret_price.setPrice(""+marketPrice.getPayAmount());
////								total_handle_fee = ""+marketPrice.getHandleFee();
////								ret_price.setMarketId(marketItem.getMarketId());
////								ret_price.setMarketItemId(marketItem.getId());
////							}
////						}
////					}
////				}
////			}
////		} else if(((IOrder)deduction) instanceof ServiceOrderDto) {
////			ServiceOrderDto service_order = (ServiceOrderDto)((IOrder)deduction);
////			Long sp_id = service_order.getPack().getId();
////			Long res_id = service_order.getResId();
////			
////			String abjs = service_order.getAbjs();
////			List<Long> a_bind_ids = JackSonBeanMapper.jsonToList(abjs, Long.class);
////			
////			AccountBindingService accountBindingService = (AccountBindingService)wac.getBean("accountBindingService");
////			McService mcService = (McService)wac.getBean("mcService");
////			List<AccountBinding> binds = accountBindingService.get_bind_by_ids(a_bind_ids);
////			String ret_a_bind_ids = "";
////			for(AccountBinding bind : binds){
////				if(!"".equals(a_bind_ids)){
////					ret_a_bind_ids += ",";
////				}
////				Map<String,Object> pet_info = JackSonBeanMapper.jsonToMap(bind.getBindcontext());
////				try {
////					ret_a_bind_ids += bind.getId() + ":" + URLEncoder.encode(URLEncoder.encode(pet_info.get("catcode").toString(), "utf-8"), "utf-8");
////				} catch (UnsupportedEncodingException e) {
////					log.error("", e);
////				}
////			}
////			log.info(ret_a_bind_ids);
////			
////			ExampleEurekaClient.PRD_PROTOCOL_SYSPROJ pro_sys_jjj = new ExampleEurekaClient.PRD_PROTOCOL_SYSPROJ(mct.getProject().getId(), mct.getProject().getProjName());
////			PetHisService ser = null;
////			try {
////				ser = ExampleEurekaClient.geneService(PetHisService.class, pro_sys_jjj);
////			} catch(Exception e) {}
////			List<ServicePackPetPriceDto> price_list = ser.pack_pet_res_price(sp_id, ret_a_bind_ids.substring(1), res_id);
////			
////			ServAppService serv_app_ser = null;
////			try {
////				serv_app_ser = ExampleEurekaClient.geneService(ServAppService.class, pro_sys_jjj);
////			} catch(Exception e) {}
////			
////			List<ServiceOrderItemDto> sp_order_items = serv_app_ser.get_items(service_order.getId());
////			String add_amount = "0";
////			if(sp_order_items!=null&&!sp_order_items.isEmpty()) {
////				for(ServiceOrderItemDto sid : sp_order_items) {
////					add_amount = MoneyUtil.add(sid.getPayAmount(), add_amount);
////				}
////			}
////			//计算会员卡价格
////			Map<String,String[]> mc_calc = new HashMap<String,String[]>();
////			for(ServicePackPetPriceDto pri : price_list){
////				String pri_low_price = MoneyUtil.add(add_amount, pri.getLowprice());
////				//mc_calc.put(String.valueOf(sp_id),new String[]{MCHSConstants.SERVICE,pri.getLowprice(),"10"});
////				mc_calc.put(String.valueOf(service_order.getBookids().split(",")[0].replaceAll("\\[|\\]","")),new String[]{MCHSConstants.SERVICE,pri_low_price,"10"});
////			}
////			mcService.mchs_calc(mc_calc, mct.getId(), deduction.getProject());
////			
////			String sp_real_price = "0";
////			if(price_list!=null&&!price_list.isEmpty()) {
////				for(String key : mc_calc.keySet()) {
////					String[] calc_res = mc_calc.get(key);
////					sp_real_price = MoneyUtil.add(sp_real_price, calc_res[1]);
////				}
////			}
////			if(!sp_real_price.equals("0")) {
////				ret_price.setPrice(sp_real_price);
////			}
////		} else if(((IOrder)deduction) instanceof OrderInfo) {
////			McService mcService = (McService)wac.getBean("mcService");
////			//普通商品订单
////			OrderInfo goods_order = (OrderInfo)deduction;
////			Set<OrderInfoGoods> goods_order_items = goods_order.getGoods();
////			//计算会员卡价格
////			Map<String,String[]> mc_calc = new HashMap<String,String[]>();
////			for(OrderInfoGoods pri : goods_order_items){
////				Long g_id = pri.getGoodsId();
////				String g_amount = pri.getSaleAmount();
////				mc_calc.put(g_id.toString() ,new String[]{MCHSConstants.GOODS, g_amount, "10"});
////			}
////			mcService.mchs_calc(mc_calc, mct.getId(), deduction.getProject());
////			
////			String sp_real_price = "0";
////			if(goods_order_items!=null&&!goods_order_items.isEmpty()) {
////				for(String key : mc_calc.keySet()) {
////					String[] calc_res = mc_calc.get(key);
////					sp_real_price = MoneyUtil.add(sp_real_price, calc_res[1]);
////				}
////			}
////			if(!sp_real_price.equals("0")) {
////				ret_price.setPrice(sp_real_price);
////			}
////		}
////		
////		ret_price.setHandle_fee(total_handle_fee);
////		ret_price.setPer_handle_fee(per_handle_fee);
////		return ret_price;
////	}
////
//
//	@Override
//	public MCRetDetail get_detail(def mc_rule, String c_id, String mc_num, def fix) {
//		MCRetDetail ret = new MCRetDetail();
////		if(!mc_rule.mc_level().equals(1)&&c_id==null) {
////			ret.setCode(MCBringConstants._mcb_failed_need_store_);
////			ret.setMsg("need bind store");
////			return ret;
////		}
////		AccountMCService accountMCService = (AccountMCService)wac.getBean("accountMCService");
////		List<AccountProjectMC> account_mc_info_result = accountMCService.get_mc_by_rule_and_num(mc_rule, mc_num);
////		if(account_mc_info_result==null) {
////			ret.setCode(MCBringConstants._mcb_not_found_);
////			ret.setMsg("mc not found");
////			return ret;
////		}
////		if(account_mc_info_result.size()>1) {
////			ret.setCode(MCBringConstants._mcb_result_repeat_);
////			ret.setMsg("mc repeat");
////			return ret;
////		}
////		
////		AccountProjectMC account_mc_info = account_mc_info_result.get(0);
////		
////		if(account_mc_info!=null) {
////			MCLocalDetail detail = new MCLocalDetail();
////			detail.setAvailableJifen(account_mc_info.getPoint());
////			detail.setBalance(account_mc_info.getBalance());
////			//detail.setBasePrice(basePrice);
////			detail.setCanBuyNum(account_mc_info.getLimitDiscountBuyNum().toString());
////			detail.setCanBuyWithOnlinePay(account_mc_info.getCanBuyWithOnlinePay());
////			detail.setCanRecharge(account_mc_info.getCanRecharge());
////			//detail.setCanUse(account_mc_info.getcan);
////			detail.setCardLevel(account_mc_info.getMcLevel());
////			detail.setCardLevelId(account_mc_info.getMch().gethNum());
////			detail.setCardNumber(account_mc_info.getMcNum());
////			//detail.setCardStatus(account_mc_info.get);
////			detail.setCardType(account_mc_info.getMch().getMcRule().getTitle());
////			detail.setCardTypeId(account_mc_info.getMch().getMcRule().getId().toString());
////			//detail.setCinemaNum(cinemaNum);
////			detail.setDiscount(account_mc_info.getDiscount());
////			detail.setGlobalCanBuyNum(account_mc_info.getLimitDiscountBuyNum().toString());
////			detail.setGoodsDiscount(account_mc_info.getGoodsDiscount());
////			//detail.setLowestPrice(account_mc_info.get);
////			detail.setMaxBuyNum(account_mc_info.getLimitDiscountBuyNum().toString());
////			//detail.setMinAddMoney(minAddMoney);
////			detail.setMobile(account_mc_info.getBasic().getMobile());
////			//detail.setOverLimitForbidBuy(overLimitForbidBuy);
////			detail.setPeriod(account_mc_info.getPeriod());
////			//detail.setPurchaseDiscountNum(account_mc_info.getMch().get);
////			detail.setUsername(account_mc_info.getMcName());
////			ret.setDetail(detail);
////		}
//		return ret;
//	}
//
//	@Override
//	public MCPrice compute_mc_price(def order, def mct) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public MCRet pay_with_mc(def order, def mct) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public MCRet mc_recharge(def mct, String recharge_amount, String pay_amount, String serial_num) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void drawback(String amount, Long apmcid) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean deductions(String amount, Long apmcid) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
////	@Override
////	public MCRet pay_with_mc(IOrder deduction, AccountProjectMC mct) {
////		//权益卡不支持付款操作
////		MCRet ret = new MCRet();
////		
////		if(mct.getCanBuyWithOnlinePay().equals("1")) {
////			ret.setCode(MCBringConstants._mcb_unsupport_pay_);
////			ret.setMsg("unsupport pay");
////			return ret;
////		}
////		//获取实际会员价格
////		MCPrice mc_price_RET = this.compute_mc_price(deduction, mct);
////		String total_mc_price = MoneyUtil.add(mc_price_RET.getHandle_fee(), mc_price_RET.getPrice());
////		String card_balance = mct.getBalance();
////		
////		String rest_balance = MoneyUtil.add(card_balance, "-" + total_mc_price);
////		if(rest_balance.indexOf("-") > -1) {
////			//余额不足
////			ret.setCode(MCBringConstants._mcb_balance_not_enough_);
////			ret.setMsg("balance not enough");
////			return ret;
////		}
////		//重置余额
////		mct.setBalance(rest_balance);
////		AccountMCService accountMCService = (AccountMCService)wac.getBean("accountMCService");
////		accountMCService.update_local_mc(mct);
////		
////		//开始更新订单状态
////		String oc = OrderUtil.get_oc_from_order_no(deduction.getOrderNo());
////		OrderPayInfo order_pay = new OrderPayInfo();
////		order_pay.setBankType(""+mct.getId());
////		order_pay.setPayAmount(total_mc_price);
////		order_pay.setPayType(PayConstants._order_pay_type_pay_mc_);
////		order_pay.setOrderId(deduction.getId());
////		order_pay.setOc(oc);
////		
////		//保存支付信息
////		try {
////			OrderPayService orderPayService = (OrderPayService)wac.getBean("orderPayService");
////			order_pay = orderPayService.save_order_pay(order_pay);
////		}catch(Exception e){
////			log.error("save mc deduction pay failed:", e);
////		}
////		
////		IOrder append_order = null;
////		if(oc.equals(OrderConstants.OrderType._general_)) {
////			//商品订单
////			OrderService orderService = (OrderService)wac.getBean("orderService");
////			deduction.setPayAmount(total_mc_price);
////			orderService.do_success_pay((OrderInfo)deduction, order_pay);
////			
////			append_order = deduction;
////		} else if(oc.equals(OrderConstants.OrderType._ticket_cinema_)) {
////			//影票订单
////			TcssOrderService tcssOrderService = (TcssOrderService)wac.getBean("tcssOrderService");
////			tcssOrderService.do_success_pay((TcssOrder)deduction, order_pay);
////			append_order = deduction;
////		} else if(oc.equals(OrderConstants.OrderType._onsite_)) {
////			//onsite 类订单
////			OnsiteOrderService onsiteOrderService = (OnsiteOrderService)wac.getBean("onsiteOrderService");
////			onsiteOrderService.do_success_pay(deduction, order_pay);
////		} else if(oc.equals(OrderConstants.OrderType._appoint_)) {
////			//服务预约订单
////			ExampleEurekaClient.PRD_PROTOCOL_SYSPROJ pro_sys_jjj = new ExampleEurekaClient.PRD_PROTOCOL_SYSPROJ(mct.getProject().getId(), mct.getProject().getProjName());
////			ServAppService serv_app_ser = null;
////			try {
////				serv_app_ser = ExampleEurekaClient.geneService(ServAppService.class, pro_sys_jjj);
////			} catch(Exception e) {}
////			serv_app_ser.do_success_pay(deduction.getId(), total_mc_price, "20050", mct.getId());
////		}
////		if(append_order!=null&&append_order instanceof TcssOrder) {
////			this.process_pub_ticket(append_order);
////			if(append_order.getOrderStatus().equals(OrderConstants._order_status_delivering_)) {
////				//出票成功
////				ret.setMsg("ticket ok");
////				return ret;
////			} else {
////				//出票失败 这里是线上会员卡 直接把还原之前的余额算了
////				mct.setBalance(card_balance);
////				accountMCService.update_local_mc(mct);
////				append_order.setOrderStatus(OrderConstants._order_status_refund_ok_ticket_failed_);
////				TcssOrderService tcssOrderService = (TcssOrderService)wac.getBean("tcssOrderService");
////				tcssOrderService.do_update_order((TcssOrder)deduction);
////			}
////		} else if(append_order!=null&&(append_order instanceof OrderInfo)) {
////			this.process_goods_pub(append_order);
////		}
////		return ret;
////	}
////
////	@Override
////	public MCRet mc_recharge(AccountProjectMC mct, String recharge_amount, String pay_amount, String serial_num) {
////		MCRet ret = new MCRet();
////
////		String exist_balance = mct.getBalance();
////		String new_balance = MoneyUtil.add(exist_balance, recharge_amount);
////		mct.setBalance(new_balance);
////		AccountMCService accountMCService = (AccountMCService)wac.getBean("accountMCService");
////		accountMCService.update_local_mc(mct);
////		return ret;
////	}
////
////	/** local method to process ticket put **/
////	private void process_pub_ticket(IOrder append_order) {
////		TcssOrder tc_order = (TcssOrder)append_order;
////		Long cinema_id = tc_order.getStoreId();
////		
////		StoreService storeService = (StoreService)wac.getBean("storeService");
////		SysProjCinemaService sysProjCinemaService = (SysProjCinemaService)wac.getBean("sysProjCinemaService");
////		MarketService marketService = (MarketService)wac.getBean("marketService");
////		TcssOrderService tcssOrderService = (TcssOrderService)wac.getBean("tcssOrderService");
////		
////		StoreInfo store = storeService.get_store_by_id(cinema_id);
////		SysProjectInfo project = store.getProject();
////		
////		SysProjectCinemaConfig cinema_config = sysProjCinemaService.get_project_default_cinema_config_by_proj(project);
////		StoreCinemaConfig store_cinema_config = store.getCinemaConfig();
////		
////		String tc_ss_type = store_cinema_config.getSsCode()==null?cinema_config.getSsCode():store_cinema_config.getSsCode();
////		String channel_url = store_cinema_config.getAuthChanUrl()==null?cinema_config.getAuthChanUrl():store_cinema_config.getAuthChanUrl();
////		String channel_num = store_cinema_config.getAuthChanNum()==null?cinema_config.getAuthChanNum():store_cinema_config.getAuthChanNum();
////		String channel_code = store_cinema_config.getAuthChanCode()==null?cinema_config.getAuthChanCode():store_cinema_config.getAuthChanCode();
////		SscAuthModel auth_model = SscFactory.direct_generateAuthModel(tc_ss_type, channel_url, channel_num, channel_code);
////		SscOp ssc_op = SscFactory.generateSscOp(auth_model);
////		
////		String cinema_no = store_cinema_config.getStoreCinemaNum();
////		PlanInfo plan = sysProjCinemaService.get_plan_by_id(tc_order.getPlanId());
////		String plan_no = plan.getPlanBasic().getPlanNo();
////		String last_update_time = plan.getPlanBasic().getrUpdateTime();
////		String inner_order_no = tc_order.getOrderNo();
////		
////		Set<TcssOrderSeat> orderSeats = tc_order.getoSeats();
////		List<SeatPub> seat_nos = new ArrayList<SeatPub>();
////		for(TcssOrderSeat hs : orderSeats) {
////			//判断是否最低票价出票
////			Integer pt_rule = store_cinema_config.getPtRule();
////			if(pt_rule==null) {
////				//默认销售价格出票
////				pt_rule = TcSsConstants._tc_pt_rule_sale_;
////			}
////			if(!pt_rule.equals(TcSsConstants._tc_pt_rule_floor_)&&!pt_rule.equals(TcSsConstants._tc_pt_rule_sale_)&&!pt_rule.equals(TcSsConstants._tc_pt_rule_nopub_)) {
////				pt_rule = TcSsConstants._tc_pt_rule_sale_;
////			}
////			String sp_price = null;
////			
////			if(pt_rule.equals(TcSsConstants._tc_pt_rule_floor_)) {
////				//如果设置了按照最低票价出票
////				sp_price = plan.getPlanBasic().getPriceFloor();
////			} else if(pt_rule.equals(TcSsConstants._tc_pt_rule_sale_)) {
////				//按照实际销售出票
////				/*
////				1.如果实际销售小于最低票价
////				2.到对应的订单里面找是否有营销活动
////				2.1没有，则直接按照最低票价出
////				2.2有，则找到营销活动里面对应的结算金额
////				*/
////				
////				//按照销售价格出票
////				String order_real_pay_amount = tc_order.getPayAmount();
////				Integer ticket_num = tc_order.getTicketSum();
////				
////				//设置出票为原价
////				sp_price = hs.getSaleFee();
////				//重新计算单张票价，先简单按照除法平均来计算 TODO
////				sp_price = MoneyUtil.divide(order_real_pay_amount, ticket_num.toString());
////				try {
////					String market_sp_price = marketService.get_market_sp_price_by_order(tc_order);
////					if(market_sp_price==null) {
////						//订单没有对应市场活动，或者市场活动未设置结算金额
////						//不用做任何处理
////						
////					} else {
////						//订单设置了市场活动，并且市场活动设置了结算金额
////						//结算金额代表影城的实际收入
////						try {
////							Float f_v1 = Float.valueOf(sp_price);
////							Float f_v2 = Float.valueOf(market_sp_price.trim());
////							if(f_v1<f_v2) {
////								//如果结算金额高于计算的出票金额
////								//设置出票金额为结算金额
////								sp_price = market_sp_price;
////							}
////						} catch(Exception e) {
////							log.error("dispose market price error :", e);
////						}
////					}
////				} catch(Exception e) {
////					log.error("dispose market price error 2 :", e);
////				}
////			} else {
////				sp_price = "-1";
////			}
////			
////			if(sp_price==null) {
////				//如果未设置成功，按照最低票价出票
////				sp_price = plan.getPlanBasic().getPriceFloor();
////			}
////			
////			if(!sp_price.equals("-1")) {
////				//重新判断是否设置的出票价低于最低票价
////				try {
////					Float f_v1 = Float.valueOf(sp_price);
////					Float f_v2 = Float.valueOf(plan.getPlanBasic().getPriceFloor());
////					if(f_v1<=f_v2) {
////						sp_price = plan.getPlanBasic().getPriceFloor();
////					}
////				} catch(Exception e) {
////					//出异常的情况，也按照最低票价来出票
////					sp_price = plan.getPlanBasic().getPriceFloor();
////				}
////			}
////			
////			SeatPub sp = new SeatPub();
////			sp.setSeat_id(hs.getSeatNo());
////			sp.setHandle_fee(hs.getHandleFee());
////			sp.setPay_amount(sp_price);
////			seat_nos.add(sp);
////			
////			try {
////				hs.setPubFee(sp_price);
////				tcssOrderService.do_update_order_seat(hs);
////			} catch(Exception e) {
////				log.error("store ticket pub fee error: ", e);
////			}
////		}
////		
////		String dx_lock_flag = tc_order.getRemote().getLockFlag();
////		RemoteTicketResult dx_remote_ticket = ssc_op.process_ticket(cinema_no, plan_no, inner_order_no,tc_order.getOriginalNo(), dx_lock_flag, seat_nos, last_update_time,null,null);
////		if(dx_remote_ticket.getCode().equals("0")) {
////			//表示出票成功，更新订单信息
////			tc_order.setDeliverTime(new Date());
////			tc_order.setOrderStatus(OrderConstants._order_status_delivering_);
////			if(!dx_remote_ticket.isPub()) {
////				tc_order.setOrderStatus(OrderConstants._order_status_delivering_nopub_);
////			}
////			tcssOrderService.update_dx_order_info(tc_order, dx_remote_ticket.getValue1(), dx_remote_ticket.getValue2());
////		} else {
////			//出票失败，做记录
////			//tc_order.setOrderStatus(OrderConstants._order_status_remote_ticket_failed_);
////			//改为记录支付成功，出票失败状态
////			tc_order.setOrderStatus(OrderConstants._order_status_payed_pub_failed_);
////			tcssOrderService.do_update_order(tc_order);
////		}
////	}
////	
////	/**
////	 * 处理卖品订单
////	 * @param append_order
////	 */
////	protected void process_goods_pub(IOrder append_order) {
////		OrderInfo deduction = (OrderInfo)append_order;
////		Set<OrderInfoGoods> order_goods = deduction.getGoods();
////		List<Long> goods_ids = new ArrayList<Long>();
////		if(order_goods!=null&&!order_goods.isEmpty()) {
////			for(OrderInfoGoods og : order_goods) {
////				goods_ids.add(og.getGoodsId());
////			}
////		} else {
////			return;
////		}
////		GoodsService goodsService = (GoodsService)wac.getBean("goodsService");
////		List<Goods> all_order_goods = goodsService.get_goods_by_ids(goods_ids);
////		if(all_order_goods==null||all_order_goods.isEmpty()) {
////			return;
////		}
////		
////		StoreService storeService = (StoreService)wac.getBean("storeService");
////		StoreInfo store = null;
////		if(deduction.getStoreId()!=null) {
////			store = storeService.get_store_by_id(deduction.getStoreId());
////		}
////		
////		Map<String, List<GOP>> req_map = new HashMap<String, List<GOP>>();
////		for(Goods g : all_order_goods) {
////			String goods_code = g.getLocal();
////			List<GOP> gop = req_map.get(goods_code);
////			if(gop==null) {
////				gop = new ArrayList<GOP>();
////			}
////			GOP new_gop = new GOP();
////			new_gop.setGoods(g);
////			for(OrderInfoGoods og : order_goods) {
////				if(og.getGoodsId().equals(g.getId())) {
////					new_gop.setNum(og.getBuyNum());
////					new_gop.setPrice(og.getPayAmount());
////					break;
////				}
////			}
////			gop.add(new_gop);
////			req_map.put(goods_code, gop);
////		}
////		log.info("need to pub goods num ====== " + req_map.size());
////		if(!req_map.isEmpty()) {
////			OrderService orderService = (OrderService)wac.getBean("orderService");
////			for(String s : req_map.keySet()) {
////				GoodsOp goods_op = GoodsOpFactory.generate(s);
////				if(goods_op!=null) {
////					GpPubRet ret = goods_op.notify_pub(deduction.getOrderNo(), store, req_map.get(s));
////					if(ret.isSuccess()) {
////						//处理卖品成功，将远程信息本地同步
////						String flag1 = ret.getG_flag1();
////						String flag2 = ret.getG_flag2();
////						OrderRemote order_remote = new OrderRemote();
////						order_remote.setaTime(new Date());
////						order_remote.setcTime(DataUtils.getCurrentTimestamp());
////						order_remote.setFlag(0);
////						order_remote.setOrder(deduction);
////						order_remote.setValidFlag1(flag1);
////						order_remote.setValidFlag2(flag2);
////						orderService.do_save_order_remote(order_remote);
////					} else {
////						log.error("GpPubRet code=" + ret.getCode() + ", msg=" + ret.getMsg() + "---------rm_code=" + ret.getRmCode() + ", rm_msg=" + ret.getRmMsg());
////					}
////				}
////			}
////		}
////	}
////
////	@Override
////	public boolean deductions(String amount, Long apmcid) {
////		AccountMCService accountMCService = (AccountMCService)wac.getBean("accountMCService");
////		AccountProjectMC apmc = accountMCService.get_mc_by_id(apmcid);
////		String new_balance = MoneyUtil.add(apmc.getBalance(), "-"+amount);
////		if(new_balance != null && !new_balance.startsWith("-")){
////			apmc.setBalance(new_balance);
////			accountMCService.save_local_mc(apmc);
////			return true;
////		}else{
////			//扣款失败
////			return false;
////		}
////	}
////	
////	@Override
////	public void drawback(String amount, Long apmcid) {
////		AccountMCService accountMCService = (AccountMCService)wac.getBean("accountMCService");
////		AccountProjectMC apmc = accountMCService.get_mc_by_id(apmcid);
////		String new_balance = MoneyUtil.add(apmc.getBalance(), amount);
////		apmc.setBalance(new_balance);
////		accountMCService.save_local_mc(apmc);
////	}
//}
