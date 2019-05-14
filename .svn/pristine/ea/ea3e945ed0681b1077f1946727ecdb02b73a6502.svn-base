package com.jeancoder.crm.ready.idream

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.util.MD5Util

import groovy.json.JsonSlurper

abstract class DreamProtocol {

	def sms_code(def mobile, def type = 'login') {
		def param = 'appkey=' + getAppkey() + '&mobile=' + mobile + '&type=' + type;
		def sign_str = param + '&' + getAppsecret();
		def sign = MD5Util.getMD5(sign_str);
		param = param + '&sign=' + sign;
		
		def uri = getUrl() + '/sns/getcode';
		
		def result = JC.remote.http_call(uri, param);
		
		JsonSlurper json = new JsonSlurper();
		def retobj = json.parseText(result);
		
		if(retobj['code']!=0) {
			return DPCODE.faile(retobj['code'], retobj['desc']);
		}
		return DPCODE.success(retobj['result']['code']);
	}
	
	/**
	 * 
	 * @param login_name
	 * @param code 这个code应为用户输入
	 * @return
	 */
	def login(def login_name, def code) {
		def param = 'appkey=' + getAppkey() + '&code=' + code + '&login_name=' + login_name + '&mobile=' + login_name;
		def sign_str = param + '&' + getAppsecret();
		def sign = MD5Util.getMD5(sign_str);
		param = param + '&sign=' + sign;
		
		def uri = getUrl() + '/sns/login';
		
		def result = JC.remote.http_call(uri, param);
		
		JsonSlurper json = new JsonSlurper();
		def retobj = json.parseText(result);
		
		if(retobj['code']!=0) {
			return DPCODE.faile(retobj['code'], retobj['desc']);
		}
		return DPCODE.success(retobj['result']);
	}
	
	abstract def getUrl();
	
	abstract def getAppkey();
	
	abstract def getAppsecret();
}
