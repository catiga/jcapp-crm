package com.jeancoder.crm.internal.h5

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.service.SessionService

def token = JC.internal.param('token')?.trim();
def pid = JC.internal.param('pid')?.trim();
if(token==null) {
	return SimpleAjax.notAvailable('token_invalid');
}
SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.flush_session(token, 0,pid);
if(session==null) {
	return SimpleAjax.notAvailable('token_invalid');
}

return SimpleAjax.available('', session);