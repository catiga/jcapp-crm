package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "mm_order_mc")
class OrderMc extends CommonOrder {
	@JCID
	BigInteger id;
	BigInteger pid;
	String o_c;
	String order_no;
	String total_amount;
	String order_status;
	
	// 会员相关信息
	String card_no;// 卡号
	String init_pwd;// 初始密码
	String mc_name;// 会员名称
	String mobile;// 手机号
	String id_card;// 身份证
	String gift_amount;
	BigInteger mch_id;
	BigInteger acmid;
	BigInteger basic_id;
	BigInteger pre_item_id;//预制卡id
	
	/**
	 * 0用户申领
	 * 1后台发卡
	 */
	Integer ops = 0;
	// 退款时间
	Date refund_time;
	Date drawback_time;
	Date take_time;
	
	Timestamp c_time;
	Date a_time;
	// 门店信息
	BigInteger store_id;
	String store_name;
	// 支付相关
	String pay_amount;
	String pay_type;
	Date pay_time;
	
	// 发卡人id
	BigInteger puid;
	// 发卡人账号
	String puname;
}
