package com.jeancoder.crm.internal.api.hy

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchyDetail
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.jdbc.JcTemplate

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName());

def pid = JC.internal.param('pid');

def sql = "select * from MemberCardRule where flag!=? and mcr_status=?";
def params = []; params.add(-1); params.add(McConstants.mcr_status_normal);
if(pid) {
	sql += ' and pid=?';
	params.add(pid);
}
sql += ' order by a_time desc';
List<MemberCardRule> mcrList = JcTemplate.INSTANCE().find(MemberCardRule, sql, params.toArray());

if(null==mcrList || mcrList.empty){
	return SimpleAjax.notAvailable('mcrule_empty,未设置会员卡规则');
}

params = [];
sql = "select * from MemberCardHierarchy where flag!=? and getmode=? and mc_id in (";
params.add(-1); params.add(McConstants._mch_get_upgrade_);
for(x in mcrList) {
	sql += '?,';
	params.add(x.id);
}
sql = sql.substring(0, sql.length() - 1) + ')';
sql += ' order by hindex asc';

List<MemberCardHierarchy> mchList = JcTemplate.INSTANCE().find(MemberCardHierarchy, sql, params.toArray());

if(mchList && !mchList.empty) {
	for(x in mchList) {
		for(y in mcrList) {
			if(x.mc_id==y.id) {
				x.mcRule = y;
				break;
			}
		}
		
		MemberCardHierarchyDetail detail = JcTemplate.INSTANCE().get(MemberCardHierarchyDetail, 'select * from MemberCardHierarchyDetail where flag!=? and mch_id=?', -1, x.id);
		if(detail!=null) {
			x.detail = detail;
		}
	}
}
return SimpleAjax.available('', mchList);
