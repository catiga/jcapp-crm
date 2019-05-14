package com.jeancoder.crm.internal.h5.mc

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.mc.McHierchHiDto
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.ret.MCardLevelRet
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService

/**查询当前pid对应的第一条可以用的会员规则对应的等级列表**/
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try{
	def mch_id = JC.internal.param("mch_id");
	def pid = JC.internal.param("pid");
	def sid = JC.internal.param("sid");
	//由当前pid查询启用会员卡规则列表
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(mch_id.toString()));
	if(null == mch){
		return AvailabilityStatus.notAvailable("会员等级不存在");
	}
	if (!"0".equals(mch.mcRule.outer_type)) {
		String c_id = null;
		SimpleAjax ajax = JC.internal.call(SimpleAjax,"ticketingsys", "/store/cinema_by_id", [pid:pid,store_id:sid]);
		if (ajax != null && ajax.isAvailable() && ajax.data != null && ajax.data.size() > 0) {
			c_id = ajax.data.get(0).store_no;
		}
		def bring =  MCFactory.generate(mch.mcRule);
		MCardLevelRet ret = bring.query_levels(c_id);
		if (!ret.isSuccess()) {
			return AvailabilityStatus.notAvailable(ret.rmCode + ":"+ret.rmMsg);
		}
		for (McHierchHiDto item : ret.dtolist) {
			if (mch.h_num.equals(item.h_num)) {
				mch.getpay = item.least_recharge;
				break;
			}
		}
	}
    List<MemberCardHierarchy> list = new ArrayList<MemberCardHierarchy>();
	list.add(mch);
	return AvailabilityStatus.available(list);
}catch(Exception e){
	Logger.error("查询等级列表失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}