package com.jeancoder.crm.internal.h5.address

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountAddress
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.jdbc.JcTemplate

def pid = JC.internal.param('pid');
def ap_id = JC.internal.param('ap_id');

List<AccountAddress> addresses = JcTemplate.INSTANCE().find(AccountAddress, 'select * from AccountAddress where flag!=? and ap_id=?', -1, ap_id);

return SimpleAjax.available('', addresses);