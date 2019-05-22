package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "mm_member_card_hierarchy")

public class MemberCardHierarchy {
	@JCID
	BigInteger id;

	BigInteger mc_id;

	Integer hindex;

	String htimes;
	
	String goods_htimes;
	
	String movie_hitmes;

	String h_num;

	String h_name;
	
	BigInteger h_point = 0l;
	
	Date a_time;
	
	Timestamp c_time = new Timestamp(new Date().getTime());
	
	Integer flag = 0;
	
	String mcr_period;
	
	// 首充金额
	String init_recharge;
	
	// 允许充值
	Integer supp_recharge;
	
	//最低充值金额
	String least_recharge;
	
	Integer close_recharge;
	
	String cr_type;
	
	String cr_type_desc;
	
	// 会员折扣
	String cr_discount;
	
	//每日限购
	Integer day_buy_limit;
	
	//针对影票，每一场次可购买折扣数
	Integer show_buy_limit;
	
	Long utimekey = 0l;
	
	/************* add field ************/
	//领用方式字段
	//默认为1，升级领用
	//0为直接领取
	Integer getmode = 1;
	//升级付费金额
	String getpay = "0";
	
	String img_url;
	
	String prefix;
	
	/** 20161116 new add field **/
	//会员线上交易手续费
	String handle_fee;
	//会员线上交易手续费收取方式，by每件商品，by每类商品，by每个订单
	String handle_fee_by;
	
	/** 20161207 new add field **/
	//是否允许余额支付
	Integer bapa;
	//是否允许优惠后低于最低限价
	Integer belop;
		//是否允许周末使用
	Integer hodu;
	
	/** 20161213 new add field **/
	String gift_recharge;
	String j_r;
	String bgc;
	String validate_type;
	String validate_period;
	String wx_card_id;
	String other_rule;
	
	@JCNotColumn
	MemberCardRule mcRule;
	
	@JCNotColumn
	MemberCardHierarchyDetail detail;
	
	public MemberCardRule getMcRule() {
		return mcRule;
	}
}
