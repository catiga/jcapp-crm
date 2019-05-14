package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID


@JCBean(tbname = "mm_order_recharge_mc")
class OrderRechargeMc extends CommonOrder {
	@JCID
	BigInteger id;
	
	BigInteger mch_id;
	String card_no; 
	
	Date taked_time;
	
	BigInteger acmid;
	
	//新增三个字段，区分充值渠道
	/**
	 * 0:用户充值
	 * 1:后台充值
	 */
	Integer ops = 0;
	
	BigInteger puid;
	
	String puname;
	BigInteger basic_id;
	
	String order_no;
	
	String total_amount;
	
	String pay_amount;
	
	String order_status;
	
	Date a_time;
	
	Date refund_time;
	
	Date pay_time;
	
	String pay_type;
	
	//String jcTemplate.update(item);
	
	String o_c;
	
	String pic_url;
	
	BigInteger pid;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	BigInteger com_order;
	
	BigInteger store_id
	String store_name;
}
