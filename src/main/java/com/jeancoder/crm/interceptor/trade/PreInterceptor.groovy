package com.jeancoder.crm.interceptor.trade

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.common.TradeCommon
import com.jeancoder.crm.ready.dto.sys.AuthRole
import com.jeancoder.crm.ready.dto.sys.AuthToken
import com.jeancoder.crm.ready.dto.sys.AuthUser
import com.jeancoder.crm.ready.util.GlobalHolder


 
@urlmapped("/")
JCRequest req = JC.request.get();
JCResponse response = ResponseSource.getResponse();
JCCookie[] cokie = req.getCookies();

try {
	// 注册
	 TradeCommon.INSTANSE.getRechargeToken();
	 TradeCommon.INSTANSE.getCreateToken();
}catch(Exception e) {
	e.printStackTrace();
}
return true;