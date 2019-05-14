package com.jeancoder.crm.internal.binddata

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.PetInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.BDPet
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate

def token = JC.internal.param('token');
def pid = JC.internal.param('pid');

AccountSession session = SessionService.INSTANCE().flush_session(token, 0, pid);
if(!session) {
	return SimpleAjax.notAvailable('user_not_login,请重新登录');
}

def sql = 'select * from BDPet where flag!=? and ap_id=?';
def result = JcTemplate.INSTANCE().find(BDPet, sql, -1, session.ap_id);

return SimpleAjax.available('', result);




