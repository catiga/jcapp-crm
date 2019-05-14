package com.jeancoder.crm.ready.dto.mc

import java.sql.Timestamp

class McRuleDto {
	BigInteger pid; //项目id
	BigInteger sid; //商店id
	String mcr_type; //积分类型
	//说明字段
	String title; //规则名称
	String info; //规则说明
	String mcr_status;
	Date a_time;
	Timestamp c_time;
	Integer flag ;
	String mcflk; //规则类型
	//默认是项目级
	Integer mc_level ; //是否通用
	String outer_type;
	String outer_uri;
	String outer_pc_num;
	String outer_pc_key;
	String prefix;// 会员卡规则前缀
	Integer a_mc_prefix ;
	Integer a_mch_prefix ;
	Integer start_cn; //开始编号
	Integer end_cn; //截止编号
	Integer ct_cn;
	Integer supp_jf; //支持积分
	Integer is_outer;
	String def_jf_add_ratio ;  //积分累积规则（元／积分）
	String def_jf_con_ratio; //积分消费规则（积分／元）
}
