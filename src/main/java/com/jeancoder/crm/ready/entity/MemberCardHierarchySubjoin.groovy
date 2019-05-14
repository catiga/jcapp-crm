package com.jeancoder.crm.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "mm_member_card_hierarchy_subjoin")
class MemberCardHierarchySubjoin {
	@JCID
	BigInteger id;
	BigInteger mch_id;
	String set_type;// 0010 分组 0011 商品 0000 通用折扣 100合成品  200套餐  300合成品
	BigInteger set_id;//商品类型   0堂食 1非堂食  单个商品 good_id   单个商品 100 200 300
	String set_discount;
	Date a_time = new Date();
	Timestamp c_time;
	Integer flag = 0;
}
