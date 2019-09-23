package com.jeancoder.crm.internal.h5.address

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountAddress
import com.jeancoder.jdbc.JcTemplate

def pid = JC.internal.param('pid');
def id = JC.internal.param('id');

AccountAddress addr = JcTemplate.INSTANCE().get(AccountAddress, 'select * from AccountAddress where flag!=? and id=?', -1, id);

return SimpleAjax.available('', addr);

