package com.jeancoder.crm.internal.api.admin

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.jdbc.JcTemplate

def ap_id = JC.internal.param('ap_id');
def pid = JC.internal.param('pid');

AccountInfo info = JcTemplate.INSTANCE().get(AccountInfo, 'select * from AccountInfo where flag!=? and ap_id=?', -1, ap_id);

return SimpleAjax.available('', info);