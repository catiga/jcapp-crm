package com.jeancoder.crm.internal.token

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.GeneralUserService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.SessionService

JCLogger logger = LoggerSource.getLogger("crm_read_mc_list");

def token = JC.internal.param('token');
def pid = JC.internal.param('pid');

AccountSession session = SessionService.INSTANCE().flush_session(token, 0, pid);
if(!session) {
	return SimpleAjax.notAvailable('user_not_login,请重新登录');
}
//开始获取会员卡列表
AccountProjectMcService mc_service = AccountProjectMcService.INSTANSE;

//List<AccountProjectMC> result = mc_service.get_all_account_mc(pid, session.ap_id);

//查询用户注册手机号码
GeneralUserService user_service = GeneralUserService.INSTANCE()
GeneralUser user = user_service.get(session.basic_id);
def mobile = user?.mobile;
List<AccountProjectMC> result = mc_service.get_by_mobile(mobile, session.proj_id);

if(result) {
	for(x in result) {
		if(x.mch_id) {
			MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(x.mch_id);
			if(mch) {
				x.mch = mch;
			}
		}
	}
}
return SimpleAjax.available('', result);