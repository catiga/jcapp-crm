package com.jeancoder.crm.ready.util

import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.sys.AuthToken
import com.jeancoder.crm.ready.entity.GeneralUser;

class GlobalHolder {

	private static final ThreadLocal<SysProjectInfo> _sys_project_ = new ThreadLocal<SysProjectInfo>();
	private static final ThreadLocal<AuthToken> _token_ = new ThreadLocal<AuthToken>();
	
	public static void setProj(SysProjectInfo e) {
		_sys_project_.set(e);
	}
	
	public static SysProjectInfo getProj() {
		return _sys_project_.get();
		
	}
	
	public static void setToken(AuthToken token) {
		_token_.set(token);
	}
	
	public static AuthToken getToken() {
		return _token_.get();
	}
	
	public static void  remove() {
		_sys_project_.remove();
		_token_.remove();
	}
	
}
