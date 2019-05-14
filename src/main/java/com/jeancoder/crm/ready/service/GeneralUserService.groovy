package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import com.jeancoder.crm.ready.entity.AccountInfo
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.MD5Util
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate

class GeneralUserService {

	final static GeneralUserService _instance_ = new GeneralUserService();
	
	static JcTemplate jc_template = JcTemplate.INSTANCE();
	
	private GeneralUser() {}
	
	static INSTANCE() {
		return _instance_;
	}
	
	def init_account(def mobile, def password) {
		String sql = 'select * from GeneralUser where flag!=? and mobile=?';
		def result_set = jc_template.find(GeneralUser, sql, -1, mobile);
		def result = null;
		if(result_set==null||result_set.empty) {
			result = new GeneralUser();
			result.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			result.c_time = result.a_time;
			result.flag = 0;
			result.mobile = mobile;
			result.name = mobile;
			result.password = MD5Util.getMD5(password);
			result.id = jc_template.save(result);
		} else {
			result = result_set.get(0);
		}
		
		return result;
	}
	
	def find(JcPage<GeneralUser> page, def user_no, def mobile) {
		String sql = 'select * from GeneralUser where flag!=?';
		def param = [];
		param.add(-1);
		if(user_no) {
			sql += ' and user_no=?';
			param.add(user_no);
		}
		if(mobile) {
			sql += ' and mobile=?';
			param.add(mobile);
		}
		return jc_template.find(GeneralUser.class, page, sql, param.toArray());
	}
	
	def find_user(JcPage<AccountInfo> page,  def mobile,def pid) {
		String sql = 'select ai.* from AccountInfo ai, data_account_third ap, mm_data_account_basic ab where ai.flag !=? and ai.ap_id=ap.id and ap.flag!=? and ap.pid=? and ap.account_id=ab.id and ab.flag !=? ';
		def param = [];
		param.add(-1);
		param.add(-1);
		param.add(pid);
		param.add(-1);
		 
		if(mobile) {
			sql += ' and ab.mobile=? ';
			param.add(mobile);
		}
		return jc_template.find(AccountInfo.class, page, sql, param.toArray());
	}
	
	def get_third_account(def belong_id, def part_id, def union_id, def pid = GlobalHolder.proj.id) {
		String sql = 'select * from AccountThirdBind where flag!=? and part_id=? and belong_code=? order by c_time desc';
		AccountThirdBind data = jc_template.get(AccountThirdBind, sql, -1, part_id, belong_id);
		if(data==null) {
			AccountThirdBind ab = new AccountThirdBind(part_id:part_id,part_union_id:union_id,belong_code:belong_id,flag:0);
			ab.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			ab.c_time = ab.a_time;
			ab.pid = pid;
			ab.id = jc_template.save(ab);
			return ab;
		}
		return data;
	}
	
	def get_third_account_by(def pid, def account_id) {
		def sql = 'select * from AccountThirdBind where flag!=? and pid=? and account_id=?';
		AccountThirdBind third = jc_template.get(AccountThirdBind, sql, -1, pid, account_id);
		return third;
	}
	
	def get_third_account_by_id(def pid, def id) {
		def sql = 'select * from AccountThirdBind where flag!=? and pid=? and id=?';
		AccountThirdBind third = jc_template.get(AccountThirdBind, sql, -1, pid, id);
		return third;
	}
	
	def get_third_account_by_ids(def pid, def ids) {
		def sql = 'select * from AccountThirdBind where flag!=? and pid=? and id in (' +ids+ ')';
		return jc_template.find(AccountThirdBind, sql, -1, pid);
	}
	
	def get_third_account_by_single_id(def id) {
		def sql = 'select * from AccountThirdBind where flag!=? and id=?';
		AccountThirdBind third = jc_template.get(AccountThirdBind, sql, -1, id);
		return third;
	}
	
	def get(def id) {
		String sql = 'select * from GeneralUser where flag!=? and id=?';
		return jc_template.get(GeneralUser, sql, -1, id);
	}
	
	def auth(def uname, def upwd) {
		String sql = 'select * from GeneralUser where flag!=? and mobile=? and password=?';
		return jc_template.find(GeneralUser, sql, -1, uname, upwd);
	}
	
	def update_account_info(def ap_id, AccountInfo account) {
		def sql = 'select * from AccountInfo where ap_id=? and flag!=?';
		AccountInfo e = jc_template.get(AccountInfo, sql, ap_id, -1);
		if(e==null) {
			synchronized (this) {
				e = jc_template.get(AccountInfo, sql, ap_id, -1);
				if(e==null) {
					e = new AccountInfo();e.ap_id = ap_id; e.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis()); e.c_time = e.a_time;
					e.id = jc_template.save(e);
				}
			}
		}
		if(account.head) {
			e.head = account.head;
		}
		if(account.nickname) {
			e.nickname = account.nickname;
		}
		if(account.info) {
			e.info = account.info;
		}
		if(account.realname) {
			e.realname = account.realname;
		}
		if(account.sex) {
			e.sex = account.sex;
		} else {
			e.sex = 0;
		}
		e.user_no = account.user_no;
		e.flag = 0;
		jc_template.update(e);
		return e;
	}
}
