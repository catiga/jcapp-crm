package com.jeancoder.crm.ready.util

import com.jeancoder.crm.ready.dto.GoodsInfoDto

public class StringUtil {

	public static boolean isEmpty(String str) {
		if(str == null) {
			return true;
		}
		return str.isEmpty();
	}
	
	public static String trim(String str){
		if(str == null){
			return null;
		}
		return str.trim();
	}
	
	public static void main(String[] agr) {
		GoodsInfoDto x =  new GoodsInfoDto();
		x.goods_id = 1L;
		x.unit = 2;
		def param = [];
		param.add([x.goods_id,  x.unit]);
		def str = JackSonBeanMapper.listToJson(param);
		println str;
		def a = JackSonBeanMapper.jsonToList(str);
		println a;
	}
	
}
