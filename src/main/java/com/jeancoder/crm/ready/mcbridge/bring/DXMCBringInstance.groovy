package com.jeancoder.crm.ready.mcbridge.bring
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.dto.mc.McHierchHiDto
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.MCEnum
import com.jeancoder.crm.ready.mcbridge.McBuilder
import com.jeancoder.crm.ready.mcbridge.constants.MCBringConstants
import com.jeancoder.crm.ready.mcbridge.dto.ItemDto
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.dto.MCCompute
import com.jeancoder.crm.ready.mcbridge.ret.MCLocalDetail
import com.jeancoder.crm.ready.mcbridge.ret.MCRet
import com.jeancoder.crm.ready.mcbridge.ret.MCRetDetail
import com.jeancoder.crm.ready.mcbridge.ret.MCardLevelRet
import com.jeancoder.crm.ready.mcbridge.ret.McPayMovieRet
import com.jeancoder.crm.ready.mcbridge.ret.TicketRefundRet
import com.jeancoder.crm.ready.util.DateUtil
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MoneyUtil
import com.milepai.core.mc.McProtocol
import com.piaodaren.ssc.constants.SscConstants
import com.piaodaren.ssc.factory.SscFactory
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.MCAuthResult
import com.piaodaren.ssc.model.MCDResult
import com.piaodaren.ssc.model.MCDetail
import com.piaodaren.ssc.model.MCRechargeResult
import com.piaodaren.ssc.model.MCTicketPriceResult
import com.piaodaren.ssc.model.MCTicketPub
import com.piaodaren.ssc.model.MCTicketPubResult
import com.piaodaren.ssc.model.MCardLevelResult
import com.piaodaren.ssc.model.SscAuthModel
import com.piaodaren.ssc.model.TicketFlagResult
import com.piaodaren.ssc.model.TicketRefundResult
import com.piaodaren.ssc.request.McCheck
import com.piaodaren.ssc.request.SeatBuy

//class DXMCBringInstance implements MCBring {
class DXMCBringInstance implements MCBring {
	
	private static  final JCLogger Logger = LoggerSource.getLogger("DXMCBringInstance");
	private SscOp ssc_op = null;;
	
	public static void  main(String[] agr) {
		MemberCardRule mcr = new MemberCardRule();
		mcr.outer_uri = ""
		mcr.outer_pc_num;
		mcr.outer_pc_key;
		//DXMCBringInstance
	}
	
	public DXMCBringInstance(MemberCardRule mcr) {
		String auth_url = mcr.outer_uri
		String auth_num = mcr.outer_pc_num;
		String auth_code = mcr.outer_pc_key;
		MCEnum mc_type = MCEnum.key_to_obj(mcr.outer_type);
		McProtocol protocol = McBuilder.build(mc_type, auth_url, auth_num, auth_code);
		SscAuthModel auth_model = SscFactory.generateAuthModel(protocol);
		this.ssc_op = SscFactory.generateSscOp(auth_model);
	}
	
	@Override
	public MCCompute compute_mc_goods_price(def mct, String c_id, List<ItemDto> items, Map<String, Object> param) {
		MCCompute mcc = new MCCompute();
		mcc.code = JsConstants.unknown;
		mcc.msg = "该会员卡不支持购买商品"
		return mcc;
	}
	
	
	/**
	 * 电影票计算价格
	 * @param account
	 * @param c_id
	 * @param items
	 * @param param
	 * @return
	 */
	@Override
	public MCCompute compute_mc_movie_price(def account, String c_id,List<ItemDto> items, Map<String, Object> param) {
		MemberCardHierarchy mch = param['mch'];
		String lock_flag = param['lock_flag'];
		MCCompute compute = new MCCompute();
		String totalPrices = "0";
		String totalAmount = "0";
		try {
			String card = account.mc_num;
			MCTicketPriceResult result = ssc_op.compute_mc_ticket_price(c_id, card, lock_flag, null);
			Logger.info("compute_mc_movie_price_" + JackSonBeanMapper.toJson(result))
			if(!result.isSuccess()) {
				//操作失败，获取业务提示
				compute.code = result.getCode();
				compute.msg = result.getMsg();
				return compute;
				
			} else {
				//操作成功
				totalPrices = result.getResult().getTotal_price();
			}
			List<ItemDto> movies = new ArrayList<ItemDto>()
			for (def dto : items) {
				def goods_id = dto.id;// 座位编号
				def price = dto.price;
				def level_price = dto.level_price;
				// 按原价返回
				ItemDto g1 = new ItemDto();
				g1.id = goods_id.toString();
				g1.price = price;
				g1.total_amount = price;
				g1.mc_price = price;
				g1.pay_amount = price;
				g1.discount = "0";
				//payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
				totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
				movies.add(g1);
			}
			compute.code = '0';
			compute.totalAmount = totalAmount;
			compute.offerAmount = MoneyUtil.add(compute.totalAmount, "-"+totalPrices);
			return compute;
		} catch (Exception e) {
			Logger.error("计算会员价失败", e);
			compute.code = JsConstants.unknown;
			compute.msg = "计算会员价失败"
			return compute;
		}
	}

	@Override
	public MCRet auth_mc(def mc_rule, String c_id, String mc_num, String mc_pwd, MCAuthFix auth_fix,Map<String, Object> param) {
		MCRet ret = new MCRet();
		try {
			if(c_id==null) {
				//鼎新的会员卡需要指定门店
				ret.code = MCBringConstants._mcb_failed_need_store_;
				ret.msg = "请绑定门店";
				return ret;
			}
			String auth_url = mc_rule.outer_uri;
			String auth_num = mc_rule.outer_pc_num;
			String auth_code = mc_rule.outer_pc_key;
			
			//SscAuthModel auth_model = SscFactory.generateAuthModel(TcSsConstants._tc_ss_dx_, auth_url, auth_num, auth_code);
			McCheck mc_check = new McCheck();
			mc_check.setC_id(c_id);
			mc_check.setMc_num(mc_num);
			mc_check.setMc_pwd(mc_pwd);
			MCAuthResult auth_result = ssc_op.doCardAuthRequest(mc_check);
			if(!auth_result.isSuccess()) {
				Logger.error("mc_num="+ mc_num + " auth_mc failure rule " + JackSonBeanMapper.toJson(auth_result));
				//操作失败，获取业务提示
				ret.code = auth_result.getCode();
				ret.msg = auth_result.getMsg();
				ret.rmCode = auth_result.getRmCode();
				ret.rmMsg = auth_result.getRmMsg();
			} else {
				if(!auth_result.getResult().getAuth().equals("1")) {
					ret.code = auth_result.getCode();
					ret.msg = auth_result.getMsg();
					ret.rmCode = auth_result.getResult().getAuth()
					ret.rmMsg = "密码校验失败";
				}
			}
			return ret;
		} catch (Exception e) {
			Logger.error("校验会员识别", e);
			ret.code = JsConstants.unknown;
			ret.msg = "校验会员识别"
			return ret;
		}
	}

	@Override
	public MCRetDetail get_detail(String c_id, String mc_num, MCAuthFix fix,Map<String, Object> param) {
		MCRetDetail detail = new MCRetDetail();
		try {
			if(c_id==null) {
				//鼎新的会员卡需要指定门店
				detail.code = MCBringConstants._mcb_failed_need_store_;
				detail.msg = "请绑定门店";
				return detail;
			}
			McCheck check_obj = new McCheck();
			check_obj.setC_id(c_id);
			check_obj.setMc_num(mc_num);
			check_obj.setMc_pwd(fix.pwd);
			check_obj.setMobile(fix.mobile);
			MCDResult card_detail = ssc_op.doCardDetailRequest(c_id, mc_num, null, null, check_obj);
			if(card_detail!=null) {
				if(!card_detail.isSuccess()) {
					Logger.error("mc_num="+ mc_num + " auth_mc failure rule " + JackSonBeanMapper.toJson(card_detail));
					String remote_msg = card_detail.getRmMsg();
					detail.code = SscConstants._ssc_model_code_net_ret_null_;
					detail.msg = remote_msg;
					return detail;
				}
				
				
				MCDetail card_detail_data = card_detail.getResult();
				if(card_detail_data!=null) {
					MCLocalDetail local_card = new MCLocalDetail();
					local_card.availableJifen = card_detail_data.getAvailableJifen();
					local_card.balance = MoneyUtil.get_cent_from_yuan(card_detail_data.getBalance());
					local_card.basePrice = card_detail_data.getBasePrice();
					local_card.callerId = card_detail_data.getCallerId();
					local_card.canBuyNum = card_detail_data.getCanBuyNum();
					local_card.canBuyWithOnlinePay = card_detail_data.getCanBuyWithOnlinePay();
					local_card.canRecharge = card_detail_data.getCanRecharge();
					local_card.canUse = card_detail_data.getCanUse();
					local_card.cardLevel = card_detail_data.getCardLevel();
					local_card.cardLevelId = card_detail_data.getCardLevelId();
					local_card.cardNumber = card_detail_data.getCardNumber();
					local_card.cardStatus = card_detail_data.getCardStatus();
					local_card.cardType = card_detail_data.getCardType();
					local_card.cardTypeId = card_detail_data.getCardTypeId();
					local_card.cinemaNum = card_detail_data.getCinemaNum();
					local_card.discount = card_detail_data.getDiscount();
					local_card.globalCanBuyNum = card_detail_data.getGlobalCanBuyNum();
					local_card.goodsDiscount = card_detail_data.getGoodsDiscount();
					local_card.lowestPrice = card_detail_data.getLowestPrice();
					local_card.maxBuyNum = card_detail_data.getMaxBuyNum();
					local_card.minAddMoney = MoneyUtil.get_cent_from_yuan(card_detail_data.getMinAddMoney());
					local_card.mobile = card_detail_data.getMobile();
					local_card.overLimitForbidBuy = card_detail_data.getOverLimitForbidBuy();
					local_card.period = DateUtil.getDate(card_detail_data.getPeriod(),'yyyy-MM-dd HH:mm:ss').getTime();
					local_card.purchaseDiscountNum = card_detail_data.getPurchaseDiscountNum();
					local_card.username = card_detail_data.getUsername();
					detail.detail = local_card;
				}
			}
			return detail;
		} catch (Exception e) {
			Logger.error("查询会员详情失败", e);
			detail.code = JsConstants.unknown;
			detail.msg = "查询会员详情失败"
			return detail;
		}
	}
	
	@Override
	public MCRet mc_recharge(def mct, String recharge_amount, String pay_amount, String serial_num,  Map<String,Object> param) {
		MCRet ret = new MCRet();
		String cinema_no = param['cinema_no'];
		Logger.error("mc_recharge " + recharge_amount + "___" +  mct.mc_num + "___" + cinema_no);
		try {
			String card = mct.mc_num;
			//实际充值金额
			String recharge_money = MoneyUtil.get_yuan_from_cent(recharge_amount);
			MCRechargeResult recharge_result = ssc_op.doCardRechargeRequest(cinema_no, card, recharge_money, serial_num);
			if(recharge_result.isSuccess()&&recharge_result.getResult().isRechargeSuccess()) {
				//充值成功 , 变更状态
				//即变为已收货状态
				ret.code = MCBringConstants._mcb_success_;
				ret.msg = "success";
			} else {
				//充值失败，做记录
				String err_msg = recharge_result.getCode() + "==" + recharge_result.getMsg() + ":" + recharge_result.getRmCode() + "==" + recharge_result.getRmMsg();
				//记录充值失败
				ret.code = MCBringConstants._mcb_failed_ ;
				ret.msg = recharge_result.msg
				ret.rmCode = recharge_result.getRmCode();
				ret.rmMsg = recharge_result.getRmMsg();
				Logger.error("account mc recharge error: " + err_msg);
			}
			return ret;
		} catch (Exception e) {
			Logger.error("会员卡充值失败", e);
			ret.code = JsConstants.unknown;
			ret.msg = "会员卡充值失败"
			return ret;
		}
	}
	
	
	@Override
	public MCRet pay_movie_mc(def mct, List<ItemDto> items, Map<String, Object> param) {
		McPayMovieRet ret = new McPayMovieRet();
		String card =  mct.mc_num;
		String c_id = param['c_id'];// 影城编号
		String play_id = param['play_no'];//排期编号
		String lock_flag = param['lock_flag'];// 
		String out_trade_no = param['order_no'];//订单编号
		String plan_update_time = param['update_time'];
		try {
			String handle_fee = "0";
			List<SeatBuy> seat_buys = new ArrayList<SeatBuy>();
			for(ItemDto dto : items) {
				SeatBuy sb = new SeatBuy();
				sb.setBuy_amount(dto.price);
				sb.setHandle_fee(dto.handle_fee);
				sb.setSeat_no(dto.id)
				seat_buys.add(sb);
				handle_fee = MoneyUtil.add(handle_fee, dto.handle_fee);
			}
			MCTicketPubResult result = ssc_op.pay_ticket_by_mc(c_id, card, play_id, handle_fee, lock_flag, seat_buys, out_trade_no, plan_update_time, null);
			Logger.info("鼎鑫会员卡支付结果:"+JackSonBeanMapper.toJson(result))
			if(!result.isSuccess()) {
				ret.code = result.getCode();
				ret.msg = result.getMsg();
				ret.rmCode  = result.getRmCode();
				ret.rmMsg  = result.getRmMsg();
				return (MCRet)ret;
			}
			ret.ticket_flag = result.ticket_flag;
			return (MCRet)ret;
		} catch (Exception e) {
			Logger.error("鼎鑫会员卡支付失败", e);
			ret.code = JsConstants.unknown;
			ret.msg = "鼎鑫会员卡支付失败"
			return (MCRet)ret;
		}
	}

	@Override
	public MCRet pay_goods_mc(def mct, List<ItemDto> items, Map<String, Object> param) {
		MCRet mcc = new MCRet();
		mcc.code = JsConstants.unknown;
		mcc.msg = "外部会员卡不支持购买商品"
		return mcc;
	}
	
	@Override
	public void drawback(String amount, Long apmcid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean deductions(String amount, Long apmcid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MCardLevelRet query_levels(String c_id) {
		MCardLevelRet ret = new MCardLevelRet();
		try {
			MCardLevelResult result = ssc_op.query_levels(c_id);
			if (!result.isSuccess()) {
				ret.code = result.getCode();
				ret.msg = result.getMsg();
				ret.rmCode  = result.getRmCode();
				ret.rmMsg  = result.getRmMsg();
				return ret;
			}
			
			List<McHierchHiDto> list = new ArrayList<McHierchHiDto>();
			for (def item : result.result) {
				McHierchHiDto mci = new McHierchHiDto();
				mci.cr_discount = MoneyUtil.multiple(item.getTicketDiscount(), "10");
				mci.cr_type = item.getType()
				mci.cr_type_desc= item.getTypeDesc()
				mci.day_buy_limit = item.getDayBuyLimit();
				mci.h_name = item.getLevelName()
				mci.h_num = item.getLevelId();
				mci.init_recharge = item.getInitMoney()
				mci.least_recharge = item.getLowestDepositMoney();
				mci.show_buy_limit = item.getShowBuyLimit();
				if(item.getAllowRecharge().equals("Y")) {
					mci.supp_recharge  = true;
				} else {
					mci.supp_recharge  = false;
				}
				list.add(mci);
			}
			ret.dtolist = list;
			return ret;
		} catch (Exception e) {
			Logger.error("查询会员等级列表失败", e);
			ret.code = JsConstants.unknown;
			ret.msg = "查询会员等级列表失败"
			return ret;
		}
	}

	@Override
	public McPayMovieRet query_ticket_code_by_mc(Object c_id, Object order_on, Object lock_flag) {
		McPayMovieRet ret = new McPayMovieRet();
		try {
			
			TicketFlagResult result = ssc_op.query_ticket_code_by_mc(c_id, order_on, lock_flag);
			if(!"0".equals(result.getCode())) {
				Logger.info("鼎鑫会员卡查询取票码失败:"+JackSonBeanMapper.toJson(result))
				ret.rmCode = result.rmCode;
				ret.rmMsg = result.rmMsg;
				ret.code = JsConstants.unknown;
				return ret;
			} else {
				MCTicketPub ticket_flag = new MCTicketPub();
				ticket_flag.setTicket_flag1(result.getResult().getTicketFlag1())
				ticket_flag.setTicket_flag2(result.getResult().getTicketFlag2())
				ret.ticket_flag  = ticket_flag;
			}
			return ret;
		} catch (Exception e) {
			Logger.error("会员支付查询取票码失败", e);
			ret.code = JsConstants.unknown;
			ret.msg = "会员支付查询取票码失败"
			return ret;
		}
		return ret;
	}

	@Override
	public TicketRefundRet tickes_refund_mc(String c_id, String inner_order_no, String ticket_flag1,
			String ticket_flag2, String seats, String lock_flag) {
			TicketRefundRet ret = new TicketRefundRet();
			try {
				TicketRefundResult result = ssc_op.ticket_refund(c_id, inner_order_no, "2", ticket_flag1, ticket_flag2, seats, lock_flag);
				Logger.info("鼎鑫会员卡退票结果:"+JackSonBeanMapper.toJson(result))
				if(!"0".equals(result.getCode())) {
					ret.rmCode = result.rmCode;
					ret.rmMsg = result.rmMsg;
					ret.code = JsConstants.unknown;
					return ret;
				} else {
					ret.result = result.getResult();
				}
				return ret;
			} catch (Exception e) {
				Logger.error("鼎鑫会员卡退票失败", e);
				ret.code = JsConstants.unknown;
				ret.msg = "退票失败"
				return ret;
			}
			return ret;
	}

}
