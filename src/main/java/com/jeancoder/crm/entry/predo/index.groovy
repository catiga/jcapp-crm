package com.jeancoder.crm.entry.predo

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.entity.McPreOrderInfo
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.jdbc.JcPage

def pn = JC.request.param('pn');
if(!pn) {
	pn = 1;
}

JcPage<McPreOrderInfo> page = new JcPage<>();
page.pn = pn;
page.ps = 20;

def order_num = JC.request.param('order_num');
def mcr_id = JC.request.param('mcr_id');
def mch_id = JC.request.param('mch_id');
def card_num = JC.request.param('card_num');

if(order_num=='') {
	order_num = null;
}
if(card_num=='') {
	card_num = null;
}
if (mcr_id=="null"||mcr_id=='') {
	mcr_id=null;
}
if (mch_id=="null"||mch_id=='') {
	mch_id=null;
}

PreMoService mo_service = PreMoService.INSTANCE();
page = mo_service.find(page, order_num, mcr_id, mch_id, card_num);

Result view = new Result();
view.setView('predo/index');

view.addObject('page', page);
view.addObject('order_num', order_num);
view.addObject('mcr_id', mcr_id);
view.addObject('mch_id', mch_id);
view.addObject('card_num', card_num);

MemberCardRuleService mc_service = MemberCardRuleService.INSTANSE;
List<MemberCardRule> mc_all = mc_service.findAll();
view.addObject('mc_all', mc_all);

MemberCardHierarchyService mch_service = MemberCardHierarchyService.INSTANSE;
def mch_all = mch_service.find_all_hierarchys();
view.addObject('mch_all', mch_all);

return view;



