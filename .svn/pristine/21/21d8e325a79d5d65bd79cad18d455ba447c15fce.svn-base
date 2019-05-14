package com.jeancoder.crm.ready.idream

import groovy.json.JsonSlurper

class DreamResp {

	int code;
	
	String desc;
	
	static def main(def argc) {
		def param = '''
			{
				"code":0, "desc":"success", 
				"result": {
					"code":"9341"
				}
			}
		''';
		JsonSlurper json = new JsonSlurper();
		def retobj = json.parseText(param);
		println retobj;
		println retobj['result']['code'];
		
		if(retobj['code']==0) {
			println 'test'
		}
	}
}




