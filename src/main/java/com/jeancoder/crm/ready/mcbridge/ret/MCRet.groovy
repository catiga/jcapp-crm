package com.jeancoder.crm.ready.mcbridge.ret

import com.jeancoder.crm.ready.mcbridge.constants.MCBringConstants

class MCRet {
	
	public  String code = MCBringConstants._mcb_success_;

	public String msg = "success";

	public String rmCode;

	public String rmMsg;

	public MCRet() {
		super();
		code = "0";
		msg = "success";
	}

	public MCRet(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}


	public boolean isSuccess() {
		if (code.equals(MCBringConstants._mcb_success_)) {
			return true;
		}
		return false;
	}
}
