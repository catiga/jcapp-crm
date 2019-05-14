package com.jeancoder.crm.entry.predo

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.entity.McPreOrderInfo
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.jdbc.JcPage

PreMoService mo_service = PreMoService.INSTANCE();

def pn = JC.request.param('pn');
if(!pn) { pn = 1; }

def page = new JcPage<>();
page.pn = 1;
page.ps = 50;

def pub_ss = JC.request.param('pub_ss');
def card_num = JC.request.param('card_num');

def o_id = JC.request.param('id')?.trim();
def order = mo_service.get(o_id);
page = mo_service.get_items(page, order, pub_ss, card_num);

Result view = new Result();
view.setView('predo/detail');

view.addObject('order', order);
view.addObject('page', page);

view.addObject('pub_ss', pub_ss);
view.addObject('card_num', card_num);

return view;



