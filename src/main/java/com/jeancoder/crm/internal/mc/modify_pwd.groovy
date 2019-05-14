package com.jeancoder.crm.internal.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.util.MD5Util

def pid = JC.internal.param('pid');
def acmid = JC.internal.param('acmid');
def new_pwd = JC.internal.param('new_pwd');

AccountProjectMcService mcservice = AccountProjectMcService.INSTANSE;
try {
	acmid = new BigInteger(acmid);
} catch(any) {
	return SimpleAjax.notAvailable('param_error,参数错误');
}
AccountProjectMC account_mc = mcservice.getItem(acmid);
if(!account_mc) {
	return SimpleAjax.notAvailable('obj_not_found,会员卡未找到');
}
account_mc.setMc_pwd(MD5Util.getMD5(new_pwd));
mcservice.update_account_project(account_mc);

return SimpleAjax.available();
