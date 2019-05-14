package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "mm_account_project_mc")
class AccountProjectMC {
	@JCID
	BigInteger id;  //mc_detail acmid
	BigInteger basic_id;
	BigInteger pid;
	BigInteger sid;
	BigInteger mch_id;
	String mc_num;
	String mc_pwd;
	String mc_name;
	String mc_mobile;
	//卡级别
	String mc_level;
	//卡余额
	String balance;
	//卡积分
	String point;
	//有效期
	String period;
	//折扣
	String discount;
	//一次购买限制享受折扣数量
	//0表示不限制
	Integer limit_discount_by_num = 0;
	//最低充值额度
	String min_recharge_money;
	//会员卡绑定时间
	Date a_time;
	Integer flag = 0;
	Timestamp c_time;
	//是否可充值
	Integer can_recharge;
	//此会员卡是否允许与其他支付方式共用
	/**
	 * 0:会员卡余额支付
	 * 1:折扣卡，第三方支付
	 */
	String can_buy_with_online_pay;
	//影院指卖品折扣
	String goods_discount;
	String amremark;
	String id_card;
	String status;
	String hardware_id;
	// 会员等级生效时间
	Date receive_time;
	@JCNotColumn
	MemberCardHierarchy mch;
	public MemberCardHierarchy getMch() {
		return mch;
	}
}
