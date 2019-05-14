package com.jeancoder.crm.entry.predo.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.McPreOrderInfo
import com.jeancoder.crm.ready.entity.McPreOrderItem
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.exception.McrException
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.StringUtil

PreMoService mo_service = PreMoService.INSTANCE();

def id = JC.request.param('id');
McPreOrderInfo order = mo_service.get(id);
if(order==null) {
	return SimpleAjax.notAvailable('订单未找到');
}
if(order.ss!='00') {
	return SimpleAjax.notAvailable('订单状态无效');
}

MemberCardRuleService rule_service = MemberCardRuleService.INSTANSE;
MemberCardRule rule = rule_service.getItem(order.mcr_id);
//生成卡编号
String cards_identifier=""
MemberCardHierarchy mch_rule=MemberCardHierarchyService.INSTANSE.getItem(order.mch_id);
if(rule==null) {
	return SimpleAjax.notAvailable('会员规则未找到');
}
String mc_rule_prefix = rule.prefix;
String mc_hier_prefix = mch_rule.prefix;
if(StringUtil.isEmpty(mc_rule_prefix)) {
	return SimpleAjax.notAvailable("会员规则前缀为空");
}
if(rule.start_cn==null||rule.end_cn==null) {
	return SimpleAjax.notAvailable("没有设置会员发卡规则");
}
def start = rule.start_cn;
def end = rule.end_cn;
def current = rule.ct_cn;
if(current==null) current = 0;
if(end - current +1 < order.total_num) {
	return SimpleAjax.notAvailable('该批次会员卡已发完');
}
def new_current = current;
synchronized (this) {
	new_current = current + order.total_num;
	rule.ct_cn = new_current;
	//首先将规则序号更新锁死
	rule_service.doupdate_rule(rule);
}

order.ss = '10';
mo_service.update(order);

//开始生成制卡明细
Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
def items = [];
while(current<new_current) {
	(current+=1);
	String append_current = (String.format("%0" + end.toString().length() + "d", current)); 	//append prefix
	cards_identifier= mo_service.get_cards_identifier(rule,mch_rule,append_current);
	McPreOrderItem item = new McPreOrderItem(order_id:order.id,card_num:append_current,pub_ss:'00',flag:0,a_time:t,c_time:t,cards_identifier:cards_identifier);
	items.add(item);
}

mo_service.batch_generate_item(items);

return SimpleAjax.available('', order.id);



