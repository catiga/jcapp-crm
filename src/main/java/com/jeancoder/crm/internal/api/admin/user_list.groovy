package com.jeancoder.crm.internal.api.admin

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate

def pn = JC.internal.param('pn');
def ps = JC.internal.param('ps');
def pid = JC.internal.param('pid');

def qk = JC.internal.param('qk');

String sql = 'select ai.* from AccountInfo ai, data_account_third ap, mm_data_account_basic ab where ai.flag !=? and ai.ap_id=ap.id and ap.flag!=? and ap.pid=? and ap.account_id=ab.id and ab.flag !=? ';
def param = [];
param.add(-1);
param.add(-1);
param.add(pid);
param.add(-1);
 
if(qk) {
	sql += ' and ab.mobile=?';
	param.add(qk);
	
	sql += " and ai.nickname like '%" + qk + "%'";
}

try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
try {
	ps = Integer.valueOf(ps);
	if(ps<1) {
		ps = 20;
	}
} catch(any) {
	ps = 20;
}
JcPage<AccountInfo> page = new JcPage<AccountInfo>();
page.setPn(pn);
page.setPs(ps);
page = JcTemplate.INSTANCE().find(AccountInfo, page, sql, param.toArray());

return SimpleAjax.available('', page);