package com.jeancoder.crm.ready.mcbridge.bring

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.mcbridge.dto.ItemDto
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.dto.MCCompute
import com.jeancoder.crm.ready.mcbridge.ret.MCLocalDetail
import com.jeancoder.crm.ready.mcbridge.ret.MCRet
import com.jeancoder.crm.ready.mcbridge.ret.MCRetDetail
import com.jeancoder.crm.ready.mcbridge.ret.MCardLevelRet
import com.jeancoder.crm.ready.mcbridge.ret.McPayMovieRet
import com.jeancoder.crm.ready.mcbridge.ret.TicketRefundRet
import com.jeancoder.crm.ready.service.MemberCardHierarchySubjoinService
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.StringUtil
import com.piaodaren.ssc.model.TicketRefundResult

class JCMCBringInstance implements MCBring  {
	
	static  final JCLogger Logger = LoggerSource.getLogger(JCMCBringInstance.class);
	
	/**
	 * 计算订单会员价格
	 */
	@Override
	public MCCompute compute_mc_goods_price(def account, String c_id, List<ItemDto> item, Map<String,Object> param) {
		MCCompute mcc = new MCCompute();
		try {
			MemberCardHierarchy mch = param['mch']
			String payAmount = "0";
			String totalAmount = "0";
			List<ItemDto> goods_List = new ArrayList<ItemDto>();
			List<MemberCardHierarchySubjoin> mchsLisat = MemberCardHierarchySubjoinService.INSTANSE.getList(mch.id);
			def map = get(mchsLisat);
			for (def dto : item) {
				def goods_id = dto.id;
				def tycode = dto.tycode;
				def cat_ids = dto.cat_ids;
				def price = dto.price;
				def num = dto.num;
				
				// 获取所有商品的通用折扣
				String all_discount = map.get("0000__0");
				String mch_discount = mch.cr_discount;
				//if (!StringUtil.isEmpty(all_discount)) {
				// 100 商品 直接用会员通用折扣
				if (!"100".equals(tycode) && StringUtil.isEmpty(all_discount) && !StringUtil.isEmpty(mch_discount)) {
					// 非单品 且所有卖品折扣的通用折扣为空 且会员卡等级的折扣不为空， 则取会员卡等级的折扣
					ItemDto g1 = new ItemDto();
					g1.id = goods_id.toString();
					g1.price = price;
					g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
					g1.mc_price = compute(price.toString(),mch_discount);
					g1.pay_amount = MoneyUtil.multiple(g1.mc_price, num.toString());
					g1.discount = MoneyUtil.get_yuan_from_cent(mch_discount);
					payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
					totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
					goods_List.add(g1);
					continue;
				} else if (!"100".equals(tycode) && !StringUtil.isEmpty(all_discount)) {
					// 非单品 且所有卖品折扣的通用折扣不为空， 则取所有卖品折扣的通用折扣
					ItemDto g1 = new ItemDto();
					g1.id = goods_id.toString();
					g1.price = price;
					g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
					g1.mc_price = compute(price.toString(),all_discount);
					g1.pay_amount =  MoneyUtil.multiple(g1.mc_price, num.toString());
					g1.discount = MoneyUtil.get_yuan_from_cent(all_discount);
					payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
					totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
					goods_List.add(g1);
					continue;
				}
				
				// 先寻找商品折扣
				String set_discount = map.get("0011__"+goods_id.toString());
				if (!StringUtil.isEmpty(set_discount)) {
					ItemDto g1 = new ItemDto();
					
					g1.id = goods_id.toString();
					g1.price = price;
					g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
					
					g1.mc_price = compute(price.toString(),set_discount);
					g1.pay_amount =  MoneyUtil.multiple(g1.mc_price, num.toString());
					g1.discount = MoneyUtil.get_yuan_from_cent(set_discount);
					
					payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
					totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
					goods_List.add(g1);
					continue;
				}
				// 寻找分组
				if (cat_ids != null) {
					boolean flag = false;
					for (def cat : cat_ids) {
						String cat_discount = map.get("0010__" + cat.toString());
						if (!StringUtil.isEmpty(cat_discount)) {
							ItemDto g1 = new ItemDto();
							g1.id = goods_id.toString();
							g1.price = price;
							g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
							g1.mc_price = compute(price.toString(),cat_discount);
							g1.pay_amount =  MoneyUtil.multiple(g1.mc_price, num.toString());
							g1.discount = MoneyUtil.get_yuan_from_cent(cat_discount);
							payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
							totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
							goods_List.add(g1);
							flag = true;
							break;
						}
					}
					if (flag) {
						continue;
					}
				}
				//取所有商品
				//String all_discount = map.get("0000__0");
				if (!StringUtil.isEmpty(all_discount)) {
					ItemDto g1 = new ItemDto();
					g1.id = goods_id.toString();
					g1.price = price;
					g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
					g1.mc_price = compute(price.toString(),all_discount);
					g1.pay_amount =  MoneyUtil.multiple(g1.mc_price, num.toString());
					g1.discount = MoneyUtil.get_yuan_from_cent(all_discount);
					payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
					totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
					goods_List.add(g1);
					continue;
				}
				//获取会员卡等级的折扣
		//		println "等级信息" + JackSonBeanMapper.toJson(mch);
				if (!StringUtil.isEmpty(mch_discount)) {
					ItemDto g1 = new ItemDto();
					g1.id = goods_id.toString();
					g1.price = price;
					g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
					g1.mc_price = compute(price.toString(),mch_discount);
					g1.pay_amount = MoneyUtil.multiple(g1.mc_price, num.toString());
					g1.discount = MoneyUtil.get_yuan_from_cent(mch_discount);
					payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
					totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
					goods_List.add(g1);
					continue;
				}
				// 按原价返回
				ItemDto g1 = new ItemDto();
				g1.id = goods_id.toString()
				g1.price = price;
				g1.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
				g1.mc_price = price;
				g1.pay_amount = MoneyUtil.multiple(g1.mc_price, num.toString());
				g1.discount = "0";
				payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
				totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
				goods_List.add(g1);
			}
			mcc.code = "0";
			mcc.items = goods_List;
			mcc.totalAmount = totalAmount;
			mcc.offerAmount = MoneyUtil.add(mcc.totalAmount, "-"+payAmount);
			return mcc;
		} catch (Exception e) {
			Logger.error("计算会员价失败", e);
			mcc.code = JsConstants.unknown;
			mcc.msg = "计算会员价失败"
			return mcc;
		}
	}

	@Override
	public MCCompute compute_mc_movie_price(def mct, String c_id, List<ItemDto> items, Map<String, Object> param) {
		MCCompute mcc = new MCCompute();
		try {
			MemberCardHierarchy mch = param['mch']
			String payAmount = "0";
			String totalAmount = "0";
			List<ItemDto> movies = new ArrayList<ItemDto>()
			for (def dto : items) {
				def goods_id = dto.id;// 座位编号
				def price = dto.price;
				def level_price = dto.level_price;
				//记录价格高于最低价
				boolean flag = false;
				if(Double.parseDouble(price.toString()) > Double.parseDouble(level_price.toString())){
					flag = true;
				}
				//获取会员卡等级的折扣
				String mch_discount = mch.cr_discount;
				if (!StringUtil.isEmpty(mch_discount)) {
					ItemDto g1 = new ItemDto();
					g1.id = goods_id.toString();
					g1.price = price;
					g1.total_amount = price;
					g1.mc_price = compute(price.toString(),mch_discount);
					if(flag==true){
						if(Double.parseDouble(g1.mc_price) < Double.parseDouble(level_price.toString())){
							g1.mc_price = level_price.toString();
						}
					}
					g1.pay_amount = g1.mc_price;
					g1.discount =  MoneyUtil.get_yuan_from_cent(mch_discount)
					payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
					totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
					movies.add(g1);
					continue;
				}
				// 按原价返回
				ItemDto g1 = new ItemDto();
				g1.id = goods_id.toString();
				g1.price = price;
				g1.total_amount = price;
				g1.mc_price = price;
				g1.pay_amount = price;
				g1.discount = "0";
				payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
				totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
				movies.add(g1);
			}
			mcc.code = "0";
			mcc.items = movies;
			mcc.totalAmount = totalAmount;
			mcc.offerAmount = MoneyUtil.add(mcc.totalAmount, "-"+payAmount);
			return mcc;
		} catch (Exception e) {
			Logger.error("计算会员价失败", e);
			mcc.code = JsConstants.unknown;
			mcc.msg = "计算会员价失败"
			return mcc;
		}
	}
	
	/**
	 * 校验会员卡权限
	 */
	@Override
	public MCRet auth_mc(def mc_rule, String c_id, String mc_num, String mc_pwd, MCAuthFix auth_fix, Map<String, Object> param) {
		MCRet ret = new MCRet();
		ret.code = "00001";
		ret.msg = "内部会员卡不支持校验"
		return ret;
	}

	/**
	 * 查询会员卡详情
	 */
	@Override
	public MCRetDetail get_detail( String c_id, String mc_num, MCAuthFix fix, Map<String,Object> param) {
		MCRetDetail retDetail = new MCRetDetail();
		try {
			def pid = param['pid'];
			AccountProjectMC account = param['account'];
			MemberCardHierarchy mch = param['mch'];
			MCLocalDetail local_card = new MCLocalDetail();
			local_card.id = account.id.toString();
			local_card.balance = account.balance;
			local_card.discount = account.discount;
			local_card.purchaseDiscountNum   = account.limit_discount_by_num
			local_card.canBuyWithOnlinePay = account.can_buy_with_online_pay;
			local_card.canRecharge = account.can_recharge;
			local_card.cardLevel = account.mc_level;
			local_card.cardLevelId = account.mch_id.toString();
			local_card.cardNumber = account.mc_num;
			local_card.cardStatus = account.status;
			local_card.discount = account.discount;
			local_card.goodsDiscount = account.goods_discount
			local_card.minAddMoney = account.min_recharge_money
			local_card.mobile = account.mc_mobile;
			local_card.purchaseDiscountNum = account.limit_discount_by_num;
			local_card.username = account.mc_name;
			if(mch!=null){
				//TODO 这里需要改动
			   //按领用日期
			   if(mch.validate_type=="GETTIME"){
				   String[] s = mch.validate_period.split("_");
				   Calendar c = Calendar.getInstance(TimeZone.getDefault());
				   c.setTime(account.receive_time);
				   if("MONTH".equals(s[1])){
					   c.add(Calendar.MONTH, Integer.valueOf(s[0]));
				   }else if("YEAR".equals(s[1])){
					   c.add(Calendar.YEAR, Integer.valueOf(s[0]));
				   }
				   //local_card.periodType = mch.validate_type;
				   local_card.period = c.getTime().getTime();
			   }
			   //固定日期
			   if(mch.validate_type=="FIXED"){
				   SimpleDateFormat _sdf_yyyymmddhhmmss_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   //acmd.validate_period =DateUtil.getDate(mch.validate_period,"yyyy-MM-dd HH:mm:ss").getTime();
				   try {
					  local_card.period = _sdf_yyyymmddhhmmss_.parse(mch.validate_period).getTime();
				   }catch(any) {}
				   //acmd.validate_type=mch.validate_type;
			   }
			   //永久
			   if(mch.validate_type=="FOREVER"){
				   local_card.period = null;
				   //acmd.validate_type = mch.validate_type;
			   }
		   }
		   retDetail.detail = local_card;
		   return retDetail;
		} catch (Exception e) {
			Logger.error("查询会员卡详情错误", e);
			retDetail.code = JsConstants.unknown;
			retDetail.msg = "系统错误"
			return retDetail;
		}
	}

	@Override
	public MCRet pay_movie_mc(def mct, List<ItemDto> items, Map<String, Object> param) {
		// 内部会员卡走专门的扣款的接口
		return null;
	}

	@Override
	public MCRet pay_goods_mc(def mct, List<ItemDto> items, Map<String, Object> param) {
		// 内部会员卡走专门的扣款的接口
		return null;
	}

	/**
	 * 会员卡充值
	 */
	@Override
	public MCRet mc_recharge(def mct, String recharge_amount, String pay_amount, String serial_num,  Map<String,Object> param) {
		// 内部会员卡走专门的充值接口
		return null;
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

	
	private Map<String,String> get(List<MemberCardHierarchySubjoin> list) {
		Map<String,String> map = new HashMap<String,String>();
		if (list == null || list.size() == 0) {
			return map;
		}
		for (MemberCardHierarchySubjoin dto : list) {
			String key = dto.set_type + "__"+ dto.set_id.toString();
			String value = dto.set_discount.toString();
			map.put(key,value);
		}
		return map;
	}
	
	private String compute(String money,String set_discount) {
		if (Integer.parseInt(set_discount) < 1 || Integer.parseInt(set_discount) > 999) {
			return money;
		}
		String m = MoneyUtil.multiple(money, set_discount);
		m = MoneyUtil.divide(m, "100");// 恢复折扣
		m = MoneyUtil.divide(m, "10");// 恢复到百分号， 1折=10%
		BigDecimal bd = new  BigDecimal(m);
		bd= bd.setScale(0,BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}

	@Override
	public MCardLevelRet query_levels(String c_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public McPayMovieRet query_ticket_code_by_mc(Object c_id, Object order_on, Object lock_flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TicketRefundRet tickes_refund_mc(String c_id, String inner_order_no, String ticket_flag1,
			String ticket_flag2, String seats, String lock_flag) {
		// TODO Auto-generated method stub
		return null;
	}

}
