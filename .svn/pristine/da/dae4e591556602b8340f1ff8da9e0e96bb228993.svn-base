package com.jeancoder.crm.entry.h5.ques

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.QuestionChoise
import com.jeancoder.crm.ready.entity.QuestionItem
import com.jeancoder.crm.ready.entity.QuestionResult
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.crm.ready.service.QuesService
import com.jeancoder.crm.ready.util.JackSonBeanMapper

QuesService ques_service = QuesService.INSTANCE();

Questionnaire pack = ques_service.get_questionnaire(1);

if(pack==null) {
	return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
}

List<QuestionItem> items = ques_service.get_ques_items(pack);
if(items==null||items.empty) {
	return SimpleAjax.notAvailable('obj_not_found,问卷尚未设置问题');
}

List<QuestionChoise> item_choises = ques_service.get_ques_choise(pack.id);

/**
 *	1050:1050|哈哈哈;1051:5472|star;
 */
def answ = JC.request.param('answ')?.trim()?.split(';');

// 
def result = [];

for(x in answ) {
	def ans_item = x.split(':');
	QuestionItem real_item = null;
	
	for(QuestionItem item : items) {
		if(ans_item[0].toString()==item.id.toString()) {
			real_item = item; break;
		}
	}
	if(real_item==null) { continue; }
	
	def ans_item_1 = ans_item[1];
	def choise_id = ans_item_1.substring(0, ans_item_1.indexOf("|"));
	def choise_value = ans_item_1.substring(ans_item_1.indexOf("|") + 1);
	
	QuestionResult ques_answer = new QuestionResult();
	if(real_item.qt=='10') {
		//文本
		ques_answer.choise_value = choise_value;
	} else if(real_item.qt=='21') {
		//单选
		QuestionChoise choise = null;
		for(qc in item_choises) {
			if(qc.id.toString()==choise_id) {
				choise = qc; break;
			}
		}
		if(choise==null) continue;
		
		ques_answer.choise_id = choise.id;
		ques_answer.choise_value = choise.awname;
	} else if(real_item.qt=='22') {
		//多选
		//TODO 目前没有多选情况
	}
	
	ques_answer.item_id = real_item.id;
	ques_answer.item_name = real_item.question;
	ques_answer.pack_id = pack.id;
	
	if(!(ques_answer.choise_id==null&&ques_answer.choise_value==null)) {
		println JackSonBeanMapper.toJson(ques_answer)
		ques_service.save_ques_result(ques_answer);
	}
}
return SimpleAjax.available();



