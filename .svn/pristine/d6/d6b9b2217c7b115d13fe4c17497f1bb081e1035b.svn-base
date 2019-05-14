package com.jeancoder.crm.entry.user

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.entity.AccountBasic
import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.service.AccountBasicService
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage

GeneralUserService user_service = GeneralUserService.INSTANCE();

//def unum = JC.request.param('unum');
def umob = JC.request.param('umob');
def pid =  RemoteUtil.getProj().id;
def pn = JC.request.param('pn');


Result result = new Result();
result.setView('user/index');
result.addObject('unum', "");
result.addObject('umob', umob);
def all_stores = RemoteUtil.connect(JcPage.class, 'project', '/incall/pure/stores', null);
result.addObject('all_stores', all_stores);


JcPage page = new JcPage<AccountInfo>();
if(!pn) {
	pn = 1;
} else  {
	pn = Integer.parseInt(pn.toString())
}
page.pn = pn;page.ps = 20;
page = user_service.find_user(page, umob,pid);


String ids = "";
for (AccountInfo info : page.result) {
	ids += info.ap_id.toString() + ",";
}
if (StringUtil.isEmpty(ids)) {
	result.addObject('page', page);
	return result;
}
ids = ids.substring(0, ids.length()-1)
List<AccountThirdBind> list =  user_service.get_third_account_by_ids(pid,ids);


ids = "";
for (AccountThirdBind info : list) {
	ids += info.account_id.toString() + ",";
}
if (StringUtil.isEmpty(ids)) {
	result.addObject('page', page);
	return result;
}
ids = ids.substring(0, ids.length()-1)
AccountBasicService abcs=new AccountBasicService();
List<AccountBasic> basicList =  abcs.getItemByids(ids);
if (basicList == null || basicList.isEmpty()) {
	result.addObject('page', page);
	return result;
}



Map<String,String> mobile = new HashMap<String,String>();
for (AccountThirdBind atb : list) {
	for (AccountBasic basic : basicList) {
		if (atb.account_id.toString().equals(basic.id.toString())) {
			mobile.put(atb.id.toString(), basic.mobile)
		}
	}
}

for (AccountInfo info : page.result) {
	info.mobile = mobile.get(info.ap_id.toString());
	if (StringUtil.isEmpty(info.mobile)) {
		info.mobile = '';
	}
	info.head =info.head+"?" + new Date().getTime();
}
result.addObject('page', page);
return result;
