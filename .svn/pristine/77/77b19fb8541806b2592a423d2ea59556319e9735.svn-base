package com.jeancoder.crm.entry.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.service.SessionService

def token = JC.request.param('token')?.trim();
if(token==null) {
	return SimpleAjax.notAvailable('token_invalid');
}
SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.flush_session(token, 0);
if(session==null) {
	return SimpleAjax.notAvailable('token_invalid');
}

return SimpleAjax.available('', session);