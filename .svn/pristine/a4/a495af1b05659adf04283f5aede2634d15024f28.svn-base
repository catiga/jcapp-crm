package com.jeancoder.crm.ready.mcbridge

import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.CommonOrder
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.bring.DXMCBringInstance
import com.jeancoder.crm.ready.mcbridge.bring.JCMCBringInstance
import com.jeancoder.crm.ready.mcbridge.dto.MCPrice
import com.jeancoder.crm.ready.mcbridge.bring.MCBring
class MCFactory {
	
	/**
	 * 只支持内部卡
	 * @param mct
	 * @return
	 */
	public static MCBring generate(MemberCardRule mcr) {
		if ("10010".equals(mcr.outer_type)) {
			return new DXMCBringInstance(mcr);
		} else {
			return new JCMCBringInstance();
		}
//		MCBring mcb = generate(mct);
//		MCBring default_bring = null;
//		//if(mct.getMch()!=null) {
//			MemberCardRule mc_rule = mct.getMch().getMcRule();
//			default_bring = generate(mc_rule);
////		} else {
////			default_bring = new DXMCBringInstance();
////		}
//		return default_bring;
	}
//	
//	/**
//	 * 储值卡
//	 * @param mc
//	 * @return
//	 */
//	public static def generate(def mc) {
//		return new MCBringRechargeInstance();
//	}
//	
//	public static MCPrice get_mc_price(CommonOrder order, AccountProjectMC mct) {
//		MCBring mcb = generate(mct);
//		if(mcb==null) {
//			return null;
//		}
//		MCPrice price = mcb.compute_mc_price(order, mct);
//		
//		return price;
//	}
//	
}
