package com.jeancoder.crm.ready.dto.h5

import java.sql.Timestamp

import com.jeancoder.crm.ready.entity.AccountInfo

class AccountInfoDto {
	BigInteger id;
	BigInteger ap_id;
	BigInteger pid;
	String head;
	String nickname;
	String realname;
	Integer sex;
	String info;
	Timestamp a_time;
	Timestamp c_time;
	Integer flag = 0;
	
	String mobile;
	String token;
	
	String part_id;
	String session_key;
	
	String birthday;
	BigDecimal weight;
	BigDecimal height;
	String email;
	String postcode;
	String countrycode;
	String countryname;
	String ethnicitycode;	//种族信仰代码
	String ethnicityname;	//种族信仰名称
	
	//管理界面
	def mt;
	
	public AccountInfoDto() {}
	
	public AccountInfoDto(AccountInfo info) {
		if(info!=null) {
			this.id = info.id;
			this.ap_id = info.ap_id;
			this.head = info.head;
			this.nickname = info.nickname;
			this.realname = info.realname;
			this.sex = info.sex;
			this.info = info.info;
			this.a_time = info.a_time;
			this.c_time = info.c_time;
			this.flag = info.flag;
			this.part_id = info.part_id;
			
			this.birthday = info.birthday;
			this.weight = info.weight;
			this.height = info.height;
			this.email = info.email;
			this.postcode = info.postcode;
			this.countrycode = info.countrycode;
			this.countryname = info.countryname;
			this.ethnicitycode = info.ethnicitycode;
			this.ethnicityname = info.ethnicityname;
		}
	}
}
