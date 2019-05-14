package com.jeancoder.crm.ready.util

import java.util.List
import java.util.Map

import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.security.DESPlus
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardHierarchySubjoinService

class AccountProjectMCUtil {

	static String strKey = "m@#ac123qeqw";
	/**
	 * 实体卡存的编号
	 * @param amc
	 * @return
	 */
	public static String getCardCode(AccountProjectMC amc) {
		return amc.mc_num;
	}
	
	/**
	 * 根据 cardCode 返回会员编号
	 * @param cardCode
	 * @return
	 */
	public static String getMcNum(String cardCode) {
//		if (cardCode.indexOf("__eree") < 0) {
//			return cardCode;
//		}
		try {
			String[] info = getCardInfo(cardCode);
			if (info!= null) { 
				return info[0];
			}
		} catch (Exception e) {
		}
		return cardCode;
		//return cardCode.substring(0,cardCode.lastIndexOf('__eree'));
	}
	
	public static String[] getCardInfo(String cardCode) {
		try {
			DESPlus dsp = new DESPlus(strKey);
			String card = dsp.decrypt(cardCode);
			return card.split("\\|")
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 二维码使用的编号
	 * @param amc
	 * @return
	 */
	public static String getH5CardCode(AccountProjectMC amc) {
		DESPlus dsp = new DESPlus(strKey);
		Long validity = new Date().getTime() + 5*60*1000;// 5 分钟有效期
		return dsp.encrypt(amc.mc_num + "|" + validity.toString());
	}
	
	/**
	 * 是否实体卡存的编号
	 * @param amc
	 * @return
	 */
	public static boolean isCard(String cardCode) {
//		if (cardCode.indexOf("__eree") < 0) {
//			return true;
//		}
		try {
			DESPlus dsp = new DESPlus(strKey);
			dsp.decrypt(cardCode);
		} catch (Exception e) {
			return true;
		}
		return false;
	}
	
	
	public Map<String,String> get(List<MemberCardHierarchySubjoin> list) {
		Map<String,String> map = new HashMap<String,String>();
		if (list == null || list.size() == 0) {
			return map;
		}
		for (MemberCardHierarchySubjoin dto : list) {
			String key = dto.set_type + "__"+ dto.set_id.toString();
			String value = dto.set_discount.toString();
			map.put(key,value);
		}
		return map;
	}

public String compute(String money,String set_discount) {
	if (Integer.parseInt(set_discount) < 1 || Integer.parseInt(set_discount) > 999) {
		return money;
	} 
	String m = MoneyUtil.multiple(money, set_discount);
	m = MoneyUtil.divide(m, "100");// 恢复折扣
	m = MoneyUtil.divide(m, "10");// 恢复到百分号， 1折=10%
	return m;
}

public def isCat(def set_cat, def cat_ids) {
	if (cat_ids == null) {
		return false;
	}
	for (def cat: cat_ids) {
		if(set_cat.equals(cat_ids)) {
			return true;
		}
	}
	return false;
}

}
