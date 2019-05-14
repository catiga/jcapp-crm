package com.jeancoder.crm.entry.predo

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.entity.McPreOrderInfo
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.JackSonBeanMapper

PreMoService mo_service = PreMoService.INSTANCE();

def o_id = JC.request.param('id');
def order = mo_service.get(o_id);
if(order==null) {
	order = new McPreOrderInfo();
}

Result view = new Result();
view.setView('predo/add');

view.addObject('order', order);
view.addObject('o_id', o_id);

MemberCardRuleService mc_service = MemberCardRuleService.INSTANSE;
List<MemberCardRule> mc_all = mc_service.findAll();
view.addObject('mc_all', mc_all);
MemberCardHierarchyService mch_service = MemberCardHierarchyService.INSTANSE;
def mch_all = mch_service.find_all_hierarchys();
view.addObject('mch_all', mch_all);

return view;



