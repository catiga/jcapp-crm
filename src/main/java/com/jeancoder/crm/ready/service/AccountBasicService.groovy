package com.jeancoder.crm.ready.service

import java.util.List

import com.jeancoder.crm.ready.entity.AccountBasic
import com.jeancoder.jdbc.JcTemplate

class AccountBasicService {
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	
	/**
	 * 通过电话查询出用户信息
	 * 
	 */
	public AccountBasic getItemByMobile(String mobile){
		String sql="select * from AccountBasic where mobile=? and flag!=?";
		return jcTemplate.get(AccountBasic.class, sql, mobile,-1);
	}
	
	public List<AccountBasic> getItemByids(def ids) {
		String sql="select * from AccountBasic where flag!=? and id in ("+ids+")";
		return jcTemplate.find(AccountBasic.class, sql,-1);
	}
}
