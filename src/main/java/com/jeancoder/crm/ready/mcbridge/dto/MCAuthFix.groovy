package com.jeancoder.crm.ready.mcbridge.dto

class MCAuthFix {
	
	private String pwd;
	
	private String mobile;
	
	private String idnum;
	
	public MCAuthFix() {}
	
	public MCAuthFix(String idnum, String mobile, String pwd) {
		this.idnum = idnum;
		this.mobile = mobile;
		this.pwd = pwd;
	}
	
		
}
