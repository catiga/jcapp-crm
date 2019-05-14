package com.jeancoder.crm.ready.service

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.crm.ready.entity.DicCommonConfig;
class DicCommonConfigService {
	static final DicCommonConfigService INSTANSE = new DicCommonConfigService();
	public List<DicCommonConfig> find_support_configs(def ... configs) {
		StringBuffer sb = new StringBuffer();
		if (configs != null) {
			for (String str : configs) {
				sb.append("'" + str + "',");
			}
		} 
		String hql = "select * from DicCommonConfig where flag!=? and cc_type in (" +sb.toString().substring(0,sb.length()-1) +")";
//		
		
		JcTemplate jcTemplate = JcTemplate.INSTANCE();
		return jcTemplate.find(DicCommonConfig.class,hql,-1);
	}
}
