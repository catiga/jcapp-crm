package com.jeancoder.crm.entry.h5.sys

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.idream.DPCODE
import com.jeancoder.crm.ready.idream.DreamProtocol
import com.jeancoder.crm.ready.idream.SecuritySandbox

DreamProtocol dpinst = new SecuritySandbox();

def p = JC.request.param('p')?.trim();

if(p.length()!=11) {
	return SimpleAjax.notAvailable('手机格式错误');
}

//下发验证码
DPCODE ret_code = dpinst.sms_code(p);
if(ret_code.code!=0) {
	println 'fail' + ret_code.code + ',' + ret_code.desc;
	return SimpleAjax.notAvailable('验证码发送失败，请重试' + ret_code.code);
}
//MemPower mem_power = MemSource.getMemPower();
//def code = ret_code.data;
//def success = mem_power.setUntil(p, code, 300);
//println success;
println p + '====' + ret_code.data;

return SimpleAjax.available();

