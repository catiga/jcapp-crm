package com.jeancoder.crm.ready.entity;

import java.sql.Timestamp
import java.util.Date

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID


@JCBean(tbname = "mm_account_project_mc_detail")
class AccountProjectMcDetail {
	@JCID
	BigInteger id;
	BigInteger pid;
	BigInteger acmid;//标志位
	String order_no;
	String o_c;
	Date a_time;
	Timestamp c_time;
	Integer flag;
	String remarks;
	String amount;
	String num;// 交易编号
	String d_num;//退款对应的原交易编号
	String code;
//	BigDecimal   DECIMAL amount;
}
