package com.jeancoder.crm.ready.mcbridge

import com.milepai.core.mc.McProtocol
import com.milepai.core.ssc.TcSsConstants

class McBuilder {
	public static McProtocol build(MCEnum mc_type, String url, String auth_num, String auth_code) {
		String real_key = null;
		if(MCEnum.SSC_DX.key().equals(mc_type.key())) {
			real_key = TcSsConstants._tc_ss_dx_;
		} else if(MCEnum.SSC_1905.key().equals(mc_type.key())) {
			real_key = TcSsConstants._tc_ss_m1905_;
		} else if(MCEnum.SSC_MTX.key().equals(mc_type.key())) {
			real_key = TcSsConstants._tc_ss_cmts_;
		} else if(MCEnum.SSC_ORISTAR.key().equals(mc_type.key())){
			real_key = TcSsConstants._tc_ss_oristar;
		}else if(MCEnum.SSC_YUEKE.key().equals(mc_type.key())){
			real_key = TcSsConstants._tc_ss_yueke;
		}
		return new McProtocol(real_key, url, auth_num, auth_code);
	}
}
