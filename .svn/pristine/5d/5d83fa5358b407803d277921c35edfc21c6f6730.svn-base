package com.jeancoder.crm.internal.wechat.app

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.power.MemPower
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.sms.Sender
import com.jeancoder.crm.ready.util.JackSonBeanMapper

def p = JC.internal.param('p')?.toString()?.trim();

def pid = 1;
def result = JC.internal.call('project', '/incall/project_by_id', [pid:pid]);
def proj_name = '';
if(result) {
	try {
		result = JackSonBeanMapper.jsonToMap(result);
		def logo = result['logo'];
		proj_name = result['proj_name'];
		proj_name = '【' + proj_name + '】';
	} catch(any) {
		
	}
}

Random rand= new Random();
int tmp = Math.abs(rand.nextInt());
tmp = tmp % (9999 - 1000 + 1) + 1000;
def random = tmp + "";

println p + '==========' + random;
MemPower mem = MemSource.getMemPower();
mem.set('_login_sms_code_' + p, random);

def content = random + '，是您的账户验证码。' + proj_name;
Sender.sendMessage([p] as String[], content)

return SimpleAjax.available('', random);





