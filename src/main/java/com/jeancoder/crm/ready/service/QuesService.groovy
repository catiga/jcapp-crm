package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.entity.QuestionChoise
import com.jeancoder.crm.ready.entity.QuestionItem
import com.jeancoder.crm.ready.entity.QuestionResult
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.jdbc.JcTemplate

class QuesService {

	private static final QuesService instance = new QuesService();
	
	static QuesService INSTANCE() {
		return instance;
	}
	
	static final JcTemplate jc_template = JcTemplate.INSTANCE();
	
	def get_questionnaire(def id) {
		return jc_template.get(Questionnaire, 'select * from Questionnaire where flag!=? and id=?', -1, id);
	}
	
	def get_ques_items(Questionnaire pack) {
		def sql = 'select * from QuestionItem where flag!=? and pack=?';
		return jc_template.find(QuestionItem, sql, -1, pack.id);
	}
	
	def get_ques_choise(def pack_id) {
		def sql = 'select * from QuestionChoise where flag!=? and item_id in (select id from QuestionItem where flag!=? and pack=?)';
		
		return jc_template.find(QuestionChoise, sql, -1, -1, pack_id);
	}
	
	def save_ques_result(QuestionResult e) {
		e.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		e.c_time = e.a_time;
		e.flag = 0;
		e.ip_addr = JC.request.get().getRemoteHost();
		return jc_template.save(e);
	}
}
