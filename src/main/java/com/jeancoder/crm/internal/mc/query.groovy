package com.jeancoder.crm.internal.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil
import com.jeancoder.jdbc.JcTemplate

//JCRequest request = RequestSource.getRequest();
//JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
//Result result = new Result();
//try {
//	def query_key = request.getParameter("query_key");
//	
//	def pid = RemoteUtil.getProj().id;
//	def list = AccountProjectMcService.INSTANSE.queryByMobileOrCard(pid,query_key);
//	return result.setData(AvailabilityStatus.available(list));
//} catch (Exception e) {
//	Logger.error("查询失败", e);
//	return result.setData(AvailabilityStatus.notAvailable("查询失败"));
//}


def mck = JC.internal.param('mck')?.toString()?.trim();
def pid = JC.internal.param('pid')?.toString();

try {
	pid = new BigInteger(pid);
} catch(any) {
	return SimpleAjax.notAvailable('pid_error,pid参数错误');
}

//def result = AccountProjectMcService.INSTANSE.queryByMobileOrCard(pid, mck);
//通过手机号或会员卡号查找
def sql = 'select * from AccountProjectMC where flag!=? and pid=? and ( mc_mobile=? or mc_num=? ) ';
def result = JcTemplate.INSTANCE().find(AccountProjectMC, sql, -1, pid, mck, mck);

if(result) {
	for(AccountProjectMC x in result) {
		def mch_id = x.mch_id;
		if(mch_id) {
			MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(mch_id);
			x.mch = mch;
		}
		def card_code = AccountProjectMCUtil.getH5CardCode(x);
		x.amremark = card_code;
	}
}
return SimpleAjax.available('', result);