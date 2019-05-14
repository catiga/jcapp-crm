package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.McDetailConstant
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.ret.MCRetDetail
import com.jeancoder.crm.ready.util.CPISCoderTools
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcTemplate

import groovy.util.logging.Log

import com.jeancoder.crm.ready.order.TradeResult

class AccountProjectMcDetailService {
	
	static final AccountProjectMcDetailService INSTANSE = new AccountProjectMcDetailService();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	static final JCLogger Logger = LoggerSource.getLogger(this.class);
	
/**********************************************************查询开始*************************************************************/
	/**
	 *  查询交易记录中是否有重复的订单号
	 * @param pid
	 * @param oc
	 * @param order_no
	 * @return
	 */
	public List<AccountProjectMcDetail> get_order_on_oc(BigInteger pid, String oc, String order_no) {
		String sql = " select * from AccountProjectMcDetail where o_c=? and pid=? and order_no=? and flag!=?";
		return jcTemplate.find(AccountProjectMcDetail.class, sql, oc, pid, order_no, -1);
	}
	
	public List<AccountProjectMcDetail> get_order_on_oc_code(BigInteger pid, String oc, String order_no,String code) {
		String sql = " select * from AccountProjectMcDetail where o_c=? and pid=? and order_no=? and code=? and flag!=?";
		return jcTemplate.find(AccountProjectMcDetail.class, sql, oc, pid, order_no,code, -1);
	}
	
	
	
	/**
	 * 查询交易记录是否存在
	 * @param pid
	 * @param num
	 * @param codes
	 * @param on
	 * @param oc
	 * @return
	 */
	public List<AccountProjectMcDetail> get_detail_num(BigInteger pid,String num,String codes,String on, String oc) {
		StringBuffer sql = new StringBuffer(" select * from AccountProjectMcDetail where pid=? and num=?  and code in ("+codes+") and flag!=?");
		def params = [];
		params.add(pid)
		params.add(num)
		params.add(-1)
		if (!StringUtil.isEmpty(on)) {
			sql.append(" and order_no=? and o_c=? ")
			params.add(on);
			params.add(oc);
		}
		Logger.info("get_detail_num_:"+sql.toString() + "__" + params.toArray())
		return jcTemplate.find(AccountProjectMcDetail.class, sql.toString(),params.toArray());
	}
	
	/**
	 * 查询当前编号是否已经退款记录
	 * @param pid
	 * @param num
	 * @param codes
	 * @return
	 */
	public List<AccountProjectMcDetail> get_detail_d_num(BigInteger pid,String num,String codes,String on, String oc ) {
		StringBuffer sql = new StringBuffer("select * from AccountProjectMcDetail where pid=? and d_num=?  and code in ("+codes+") and flag!=? and  order_no=? and o_c=?")
		def params = [];
		params.add(pid)
		params.add(num)
		params.add(-1)
		params.add(on);
		params.add(oc);
		return jcTemplate.find(AccountProjectMcDetail.class, sql.toString(),params.toArray());
	}
/**********************************************************查询结束*************************************************************/
	
	/**
	 * 扣款
	 * @param detailList
	 * @param mc
	 * @param total_amount
	 * @return
	 */
	public String deduction(List<AccountProjectMcDetail> detailList, AccountProjectMC mc, String total_amount) {
		synchronized (this) {
			String amount_sum = AccountProjectMcDetailService.INSTANSE.getAmountSum(mc.id);
			String balance = MoneyUtil.add(amount_sum, "-" + total_amount);
			if( Double.parseDouble(balance)< 0){
				return "余额不足";
			}
			for (AccountProjectMcDetail detail : detailList) {
				jcTemplate.save(detail);
			}
			mc.balance = getAmountSum(mc.id);
			mc.c_time = new Timestamp(new Date().getTime());
			jcTemplate.update(mc);
		}
		return null;
	} 
	
	
	/**
	 * 外部会员卡扣款
	 * @param detailList
	 * @param mc
	 * @param total_amount
	 * @return
	 */
	public String outerDeduction(List<AccountProjectMcDetail> detailList, AccountProjectMC mc, String total_amount) {
		synchronized (this) {
			String amount_sum = AccountProjectMcDetailService.INSTANSE.getAmountSum(mc.id);
			String balance = MoneyUtil.add(amount_sum, "-" + total_amount);
			for (AccountProjectMcDetail detail : detailList) {
				jcTemplate.save(detail);
			}
		}
		return null;
	}
	
	public  TradeResult refund(BigInteger pid, String num, String refund_num,String on, String oc ) {
		TradeResult tr = new TradeResult();
		tr.code = JsConstants.unknown;
		
		// 单笔退款的时候需要 查询次订单有没有退款
		String refundcode = McDetailConstant.getRefundCodes();
		if (!StringUtil.isEmpty(on)) {
			List<AccountProjectMcDetail> refundlist = get_detail_d_num(pid, num, refundcode,on,oc );
			if (refundlist != null && !refundlist.isEmpty()) {
				tr.err_code_des = "已经退款不能重复操作";
				return tr;
			}
		}
		
		// 查询交易是否存在
		String deductionCodes = McDetailConstant.getDeductionCodes();
		List<AccountProjectMcDetail> deductionlist = get_detail_num(pid, num, deductionCodes,on,oc);
		if (deductionlist == null || deductionlist.isEmpty()) {
			tr.err_code_des = "没有查到交易信息";
			return tr;
		}
		
		String amount = "0";
		BigInteger acmid = null;
		synchronized (this) {
			for (AccountProjectMcDetail detail : deductionlist) {
				AccountProjectMcDetail refund = new AccountProjectMcDetail();
				// 查询交易记录是否已经退款
				List<AccountProjectMcDetail> refundlist = get_detail_d_num(pid, num, refundcode,detail.order_no,detail.o_c );
				if (refundlist != null && !refundlist.isEmpty()) {
					continue;
				}
				String refund_amount = detail.amount.replace("-", "");
				amount = MoneyUtil.add(amount, refund_amount);
				refund.pid =  pid;
				refund.acmid = detail.acmid;
				refund.order_no = detail.order_no;
				refund.o_c =  detail.o_c;
				refund.a_time = new Date();
				refund.c_time = new Timestamp(new Date().getTime());
				refund.flag = 0;
				refund.remarks = "";
				refund.amount = refund_amount;
				refund.code = McDetailConstant.refund;
				refund.num = refund_num;
				refund.d_num = detail.num;
				jcTemplate.save(refund);
				acmid = detail.acmid;
			}
		}
		tr.code = "0";
		tr.trans_total_amount = amount;
		tr.trans_id = refund_num;
		if (acmid == null) {
			return tr;
		} 
		try {
			AccountProjectMC mc = jcTemplate.get(AccountProjectMC, "select * from AccountProjectMC where id=? and flag!=?",acmid,-1);
			if (mc == null) {
				return tr;
			}
			mc.balance = getAmountSum(mc.id);
			mc.c_time = new Timestamp(new Date().getTime());
			jcTemplate.update(mc);
		} catch (e) {
		}
		return tr;
	}
	
	/**
	 * 充值订单 退款后需要从余额中减去响应的部分
	 * @return
	 */
	public String refundRechargeOrder(BigInteger pid, String on,String oc) {
		List<AccountProjectMcDetail> rechargelist = get_order_on_oc_code(pid, oc,on,McDetailConstant.recharge_order);
		if (rechargelist == null || rechargelist.isEmpty()) {
			return "没有查到交易信息";
		}
		AccountProjectMcDetail detail =  rechargelist.get(0);
		def acmid = null;
		List<AccountProjectMcDetail> refundlist = get_detail_d_num(pid, rechargelist.get(0).getNum(), McDetailConstant.refund_recharge_order);
		if (refundlist != null && !refundlist.isEmpty()) {
			return "已经退单不能重复操作";
		}
		synchronized (this) {
			AccountProjectMcDetail refund = new AccountProjectMcDetail();
			String refund_amount = "-" + rechargelist.get(0).amount;
			refund.pid =  pid;
			refund.acmid = detail.acmid;
			refund.order_no = detail.order_no;
			refund.o_c =  detail.o_c;
			refund.a_time = new Date();
			refund.c_time = new Timestamp(new Date().getTime());
			refund.flag = 0;
			refund.remarks = "";
			refund.amount = refund_amount;
			refund.code = McDetailConstant.refund_recharge_order;
			refund.num = CPISCoderTools.serialNum(McDetailConstant.refund_recharge_order);
			refund.d_num = detail.num;
			jcTemplate.save(refund);
			acmid = detail.acmid;
		}
		try {
			AccountProjectMC mc = jcTemplate.get(AccountProjectMC, "select * from AccountProjectMC where id=? and flag!=?",acmid,-1);
			if (mc == null) {
				return "";
			}
			mc.balance = getAmountSum(mc.id);
			mc.c_time = new Timestamp(new Date().getTime());
			jcTemplate.update(mc);
		} catch (e) {
		}
		return "";
	}
	
	
	
	
	public String getAmountSum(BigInteger acmid){
		String sql = "select amount from AccountProjectMcDetail where acmid=? and flag!=?";
		List<AccountProjectMcDetail> amount_list =jcTemplate.find(AccountProjectMcDetail.class, sql,acmid,-1);
		String amount_sum = "0";
		for(AccountProjectMcDetail item : amount_list){
			amount_sum = MoneyUtil.add(amount_sum, item.amount)
		}
		return new BigDecimal(amount_sum).setScale(0,BigDecimal.ROUND_DOWN).toString();
	}
	
	//计算用户积分
	public String getPoint(String rules,String amount, MemberCardHierarchy mch){
		String basenum = "100";
		String htimes = "0";
		if(McConstants._mch_goods_htimes_.equals(rules)){
			htimes = mch.goods_htimes;
		}
		if(McConstants._mch_movie_htimes_.equals(rules)){
			htimes = mch.movie_hitmes;
		}
		basenum = MoneyUtil.multiple(htimes, basenum);// 0.02 * 100 = 2
		basenum = MoneyUtil.multiple(amount, basenum);// 2 *  10000 分
		basenum = MoneyUtil.divide(basenum, "100");// 2* 100  元
		return  MoneyUtil.divide(basenum, "100");// 0.02 * 100 元
	}
	
	
	
	public static void main (String[] arg) {
		BigDecimal bd = new BigDecimal("99880285.20").setScale(0,BigDecimal.ROUND_DOWN);
		System.out.println(new BigDecimal("99880285.20").setScale(0,BigDecimal.ROUND_DOWN).toString());
//		"065d5f4998e1ddc01055e32b6e32ce5b".getString("UTF-8");
//		http://jcloudapp.pdr365.com/crm//api/order/createOrder?oc=8000&on=gmc1809110039542800010&sign=065d5f4998e1ddc01055e32b6e32ce5b
//		gmc1809110039542800010
//		8000
//		sign___B97DECA3FC11772CCB9BDFF0C5D683DF
//		singKey__065d5f4998e1ddc01055e32b6e32ce5b
//		token__335b53dc108b6e7c59febbde28d7ac3cc976110ff1000ac6207d63934a1f9ecf
//		065d5f4998e1ddc01055e32b6e32ce5b
	}

}
