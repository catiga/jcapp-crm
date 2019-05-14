package com.jeancoder.crm.internal.binddata

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.PetInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.BDPet
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate

def token = JC.internal.param('token');
def pid = JC.internal.param('pid');
def p = JC.internal.param('p');

AccountSession session = SessionService.INSTANCE().flush_session(token, 0, pid);
if(!session) {
	return SimpleAjax.notAvailable('user_not_login,请重新登录');
}

PetInfo pp = JackSonBeanMapper.fromJson(p, PetInfo);

def id = pp.id;

BDPet bdp = null;
def update = false;
if(id) {
	bdp = JcTemplate.INSTANCE().get(BDPet, 'select * from BDPet where flag!=? and id=?', -1, id);
	if(!bdp) {
		return SimpleAjax.notAvailable('obj_not_found,宝贝信息跑丢了，稍后试试看吧');
	}
	update = true;
} else {
	bdp = new BDPet();
	bdp.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	bdp.pid = new BigInteger(pid);
	bdp.flag = 0;
}

//增加一个宠物
bdp.birthday = pp.birth;
bdp.catcode = pp.catcode;
bdp.catname = pp.cat;
bdp.disease_history = pp.sicken;

bdp.food_brand = pp.food;
try {
	bdp.gender = Integer.valueOf(pp.gender);
}catch(any) {
	bdp.gender = 0;
}
bdp.give_birth = pp.bearing;
bdp.hair_care_brand = pp.nursing;
bdp.hair_color = pp.color;
bdp.headpic = pp.headimg;
bdp.nickname = pp.name;

bdp.remark = pp.remark;
bdp.stature = pp.high;
bdp.sterilisation = pp.sterilisation;
bdp.nature = pp.character;
bdp.ap_id = session.ap_id;
bdp.basic_id = session.basic_id;

//直接保存，并返回信息
if(update) {
	JcTemplate.INSTANCE().update(bdp);
} else {
	bdp.id = JcTemplate.INSTANCE().save(bdp);
}

return SimpleAjax.available('', bdp);




