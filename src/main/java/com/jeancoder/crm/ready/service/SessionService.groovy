package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.AccountThirdBind
import com.jeancoder.crm.ready.entity.GeneralUser
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.MD5Util
import com.jeancoder.jdbc.JcTemplate
import com.milepai.core.utils.security.DESPlus

class SessionService {

	JCLogger LOGGER = LoggerSource.getLogger(SessionService.class.getName());
	
	final static SessionService _INSTANCE_ = new SessionService();
	
	static final JcTemplate jc_template = JcTemplate.INSTANCE();
	
	GeneralUserService user_service = GeneralUserService.INSTANCE();
	
	public static SessionService INSTANCE() {
		return _INSTANCE_;
	}
	
	def invalid_session(String token, Integer lograns) {
		if(token==null||token.trim().equals('')) {
			return;
		}
		String hql = "select * from AccountSession where flag!=? and proj_id=? and token=? and lograns=?";
		List<AccountSession> user_sessions = jc_template.find(AccountSession, hql, -1, GlobalHolder.proj.id, token, lograns);
		if(user_sessions==null||user_sessions.isEmpty()) {
			return;
		}
		for(AccountSession session : user_sessions) {
			session.flag = -1;
			jc_template.update(session);
		}
	}
	
	public AccountSession login_session(String username, String passwd, AccountThirdBind third, Long validate_period, String logtype, def pid = GlobalHolder.proj.id) {
		def user_id = 0;
		def user_pwd = MD5Util.getMD5("99881122");
		
		if(username!=null&&passwd!=null) {
			def result = user_service.auth(username, passwd);
			if(result==null) {
				return null;
			}
			if(result.size()>1) {
				return null;
			}
			GeneralUser general_user = result.get(0);
			user_id = general_user?.id;
			user_pwd = general_user?.password;
		}
		
		AccountSession session = this.build_session(new Date(), user_id, user_pwd, third, validate_period, 0, logtype, pid);
		return session;
	}
	
	protected AccountSession build_session(Date a_time, BigInteger user_id, String user_key, AccountThirdBind third, Long validate_period, Integer lograns, String logtype, def pid = GlobalHolder.proj.id) throws Exception {
		//def sql = "select * from AccountSession where flag!=? and proj_id=? and basic_id=? and lograns=?";
		def sql = "select * from AccountSession where flag!=? and proj_id=? and lograns=?";
		def params = []; 
		SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
		params.add(-1); params.add(now_project.id); params.add(lograns);
		if(user_id.toString()!='0') {
			sql += ' and basic_id=?'; params.add(user_id);
		}
		if(third!=null) {
			sql += ' and ap_id=?';
			params.add(third.id);
		}
		
		List<AccountSession> user_sessions = jc_template.find(AccountSession, sql, params.toArray());
		String new_token = user_id + "|" + user_key + "|" + validate_period + "|" + third.id;
		DESPlus des = new DESPlus(now_project.proj_key);
		
		AccountSession session = null;
		if(user_sessions==null||user_sessions.empty) {
			session = new AccountSession();
			session.a_time = new Timestamp(a_time.getTime());
			session.rushed = a_time.getTime();
			session.basic_id = user_id;
			session.c_time = new Timestamp(a_time.getTime());
			session.flag = 0;
			session.proj_id = now_project.id;
			session.token = des.encrypt(new_token);
			session.lograns = lograns;
			session.ap_id = third?.id;
			if(logtype!=null) {
				session.logtype = logtype;
			}
			jc_template.save(session);
		} else if(user_sessions.size()==1) {
			session = user_sessions.get(0);
			String old_token = session.token;
			//judge token expired?
			String old_token_decode = null;
			try {
				old_token_decode = des.decrypt(old_token);
			} catch(Exception e) {
				session.flag = -1;
				jc_template.update(session);
			}
			if(old_token_decode==null) {
				//说明是一个有问题的密文，二次登陆会成功
				return null;
			}
			LOGGER.info(old_token_decode);
			String[] arr_old_token_decode = old_token_decode.split("\\|");
			
			long create_time = session.getRushed();
			long now_time = Calendar.getInstance().getTimeInMillis();
			//long valid_time = 9*365*24*60*60*1000;
			long valid_time = 0l;
			try {
				valid_time = Long.valueOf(arr_old_token_decode[2]);
			} catch(Exception e) {
			}
			if((now_time - create_time)<=valid_time) {
				//说明在有效期内，刷新有效期为当前时间
				session.setRushed(now_time);
			}
			jc_template.update(session);
		} else {
			//说明有多条，直接全部置为-1，并创建新的
			for(AccountSession s : user_sessions) {
				s.flag = -1;
				jc_template.update(s);
			}
			//创建session
			session = new AccountSession();
			session.a_time = new Timestamp(a_time.getTime());
			session.rushed = a_time.getTime();
			session.basic_id = user_id;
			session.c_time = new Timestamp(a_time.getTime());
			session.flag = 0;
			session.proj_id = now_project.id;
			session.setToken(des.encrypt(new_token));
			session.setLograns(lograns);
			session.ap_id = third?.id;
			jc_template.save(session);
		}
		return session;
	}
	
	protected AccountSession flush_session(String token, Integer lograns, def pid = GlobalHolder.proj.id) throws Exception {
		if(token==null||token.trim().equals('')) {
			return null;
		}
		String sql = "select * from AccountSession where flag!=? and proj_id=? and token=? and lograns=?";
		List<AccountSession> user_sessions = jc_template.find(AccountSession, sql, -1, pid, token, lograns);
		if(user_sessions==null||user_sessions.empty) {
			return null;
		}
		if(user_sessions.size()>1) {
			LOGGER.error('admin_session_token_repeat_error:token=' + token);
			return null;
		}
		try {
			SysProjectInfo now_project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
			DESPlus des = new DESPlus(now_project.proj_key);
			AccountSession session = user_sessions.get(0);
			String old_token = session.getToken();
			//需要判断token是否过期
			String old_token_decode = des.decrypt(old_token);
			LOGGER.info(old_token_decode);
			String[] arr_old_token_decode = old_token_decode.split("\\|");
			
			long create_time = session.getRushed();
			long now_time = Calendar.getInstance().getTimeInMillis();
			long valid_time = 0l;
			try {
				valid_time = Long.valueOf(arr_old_token_decode[2]);
			} catch(Exception e) {
			}
			if((now_time - create_time)<=valid_time) {
				//说明在有效期内，刷新有效期为当前时间
				session.rushed = now_time;
				session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				jc_template.update(session);
				return session;
			} else {
				//clear token
				session.flag = -1
				session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				jc_template.update(session);
				return null;
			}
		} catch(Exception e) {
			LOGGER.error("", e);
			return null;
		}
	}
}
