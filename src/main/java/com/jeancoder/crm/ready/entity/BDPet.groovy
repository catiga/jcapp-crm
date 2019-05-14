package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "mm_binddata_pet")
class BDPet {
	
	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger ap_id;
	
	@JCForeign
	BigInteger basic_id;
	
	@JCForeign
	BigInteger pid;
	
	String headpic;
	
	String nickname;
	
	String catcode;
	
	String catname;
	
	Integer gender;
	
	String nature;
	
	String birthday;
	
	String remark;
	
	String stature;	//身高
	
	String sterilisation;
	
	String give_birth;
	
	String hair_color;
	
	String disease_history;
	
	String hair_care_brand;
	
	String food_brand;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
