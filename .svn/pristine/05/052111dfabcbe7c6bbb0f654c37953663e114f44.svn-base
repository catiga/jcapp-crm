package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = 'account_info')
class AccountInfo {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger ap_id;
	
	String head;
	
	String nickname;
	
	String realname;
	
	Integer sex = 0;
	
	String info;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	String user_no;
	
	@JCNotColumn
	String part_id;
	
	@JCNotColumn
	String mobile;
}
