package com.jeancoder.crm.ready.sms

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.util.MD5Util

class KJCX {
	
//	static final String gate_way = 'http://api.cxton.com:8080/eums/utf8/send_strong.do';
//	
//	static final String user_name = 'hdyshy';
//	
//	static final String user_pass = 'mwcejrra';
	
	String gate_way;
	String user_name;
	String user_pass;
	
	KJCX(def gate_way, def user_name, def user_pass) {
		this.gate_way = gate_way;
		this.user_name = user_name;
		this.user_pass = user_pass;
	}
	
	static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat('yyyyMMddHHmmss');
	
	def send(def mobile, def content) {
		def seed = yyyyMMddHHmmss.format(Calendar.getInstance().getTime());
		def key = MD5Util.getMD5(MD5Util.getMD5(user_pass) + seed);
		def params = 'key=' + key + '&dest=' + mobile + '&content=' + content + '&name=' + user_name + '&seed=' + seed;
		def result = JC.remote.http_call(gate_way, params);
		return result;
	}
	
	static void main(def artc) {
		//KJCX.send(null, null);
	}
}
