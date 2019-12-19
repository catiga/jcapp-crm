package com.jeancoder.crm.internal.h5.sys

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.power.MemPower
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.sms.KJCX
import com.jeancoder.crm.ready.sms.Sender
import com.jeancoder.crm.ready.util.JackSonBeanMapper

JCLogger logger = JCLoggerFactory.getLogger('sms_code');
def p = JC.internal.param('p')?.toString()?.trim();

def pid = 1;
def tmp_pid = JC.internal.param('pid');
if(!tmp_pid) {
	pid = tmp_pid;
}
def result = JC.internal.call('project', '/incall/project_by_id', [pid:pid]);
def proj_name = '';
logger.info('pid=' + pid + ',,,project_result=' + result);
if(result) {
	try {
		result = JackSonBeanMapper.jsonToMap(result);
		def logo = result['logo'];
		proj_name = result['proj_name'];
		proj_name = '【' + proj_name + '】';
	} catch(any) {
		any.printStackTrace();
	}
}

Random rand= new Random();
int tmp = Math.abs(rand.nextInt());
tmp = tmp % (9999 - 1000 + 1) + 1000;
def random = tmp + "";

MemPower mem = MemSource.getMemPower();
mem.set('_login_sms_code_' + p, random);

def content = random + '，是您的账户验证码。' + proj_name;

logger.info p + '==========' + random + ', sign=' + content;

//开始获取短信网关配置
SimpleAjax sms_config = JC.internal.call(SimpleAjax, 'project', '/sys/get_sms_config', null);

def sms_config_data = null;
if(sms_config && sms_config.available) {
	sms_config_data = sms_config.data;
}
if(!sms_config_data) {
	//走默认配置短信接口
	logger.info('sender by own');
	Sender.sendMessage([p] as String[], content)
} else {
	//走横店特殊发送接口
	logger.info('sender by hengdian film');
	def gate_way = null; def user_name = null; def user_pass = null;
	sms_config_data = JackSonBeanMapper.jsonToList(sms_config_data);
	for(x in sms_config_data) {
		if(x['key']=='uri') {
			gate_way = x['value'];
		} else if(x['key']=='un') {
			user_name = x['value'];
		} else if(x['key']=='up') {
			user_pass = x['value'];
		}
	}
	KJCX kj = new KJCX(gate_way, user_name, user_pass);
	
	//修改content
	content = '【横店影视】您的账号注册验证码为:' + random + '，验证码有效时间为5分钟。';
	def send_result = kj.send(p, content);
	logger.info('hengdianfilm_sender_result=' + send_result);
}

return SimpleAjax.available('', random);

