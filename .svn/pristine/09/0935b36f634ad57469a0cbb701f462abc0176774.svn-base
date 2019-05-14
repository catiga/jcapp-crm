package com.jeancoder.crm.interceptor.token

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.sys.AuthRole
import com.jeancoder.crm.ready.dto.sys.AuthToken
import com.jeancoder.crm.ready.dto.sys.AuthUser
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.NativeUtil


@urlmapped("/")
@urlpassed(['/h5','/api/order/createOrder', '/api/order/recharge', '/api/order/compute_mc_movie_price', '/api/order/compute_mc_price'])

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
JCRequest req = JC.request.get();
JCResponse response = ResponseSource.getResponse();
String domain = req.getServerName();
String servletPath = req.getPathInfo().replaceFirst("/", "");
int port = req.getServerPort();
String login = "http://" + domain + ":" + port + "/project/index/index";
try {
	if (servletPath.startsWith("crm/h5/")) {
		return true;
	}
	
	String _c_u_k_ = null;
	JCCookie[] cookies = req.getCookies()
	if(cookies!=null&&cookies.length>0) {
		for(JCCookie c : cookies) {
			if(c.getName().equals("_c_u_k_adm_")) {
				try {
					_c_u_k_ = URLDecoder.decode(c.getValue(), "utf-8");
				}catch(Exception e){
				}
				break;
			}
		}
	}
	if (_c_u_k_ == null || _c_u_k_.length() == 0) {
		ResultSource.setResult(new Result().setRedirectResource("/project/index/index"));
		return new Result().setRedirectResource("/project/index/index");
	}
	
	AuthToken token = NativeUtil.connect(AuthToken.class, 'project', '/incall/token', ["pid":GlobalHolder.proj.id,"token":_c_u_k_]);
	if (token == null) {
		response.sendRedirect(login);
		return new Result().setRedirectResource("/project/index/index");
	}
 	req.setAttribute('current_token', token);
 	req.setAttribute('token_str', _c_u_k_);
	GlobalHolder.setToken(token);
	return true;
}catch(Exception e) {
	Logger.error("未登陆",e);
	ResultSource.setResult(new Result().setRedirectResource("/project/index/index"));
	return new Result().setRedirectResource("/project/index/index");
}
