package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_account_address")
class AccountAddress {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger basic_id;
	
	@JCForeign
	BigInteger ap_id;
	
	String name;
	
	String country;
	
	String province;
	
	String city;
	
	String zone;
	
	String address;
	
	String mobile;
	
	Integer flag;
	
	Timestamp c_time;
	
	String province_code;
	
	String city_code;
	
	String zone_code;
	
	Integer is_def = 0;
	
	String zipcode;
	
	@JCForeign
	BigInteger proj_id;
	
	Date a_time;
}
