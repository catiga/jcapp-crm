package com.jeancoder.crm.ready.service

import com.jeancoder.jdbc.JcTemplate
import com.qiniu.util.StringUtils
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.util.StringUtil

import java.sql.Timestamp

class MemberCardHierarchySubjoinService {
	
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	public static final MemberCardHierarchySubjoinService INSTANSE = new MemberCardHierarchySubjoinService();

	public String saveItem(MemberCardHierarchySubjoin mchs) {
		String sql="select * from MemberCardHierarchySubjoin where mch_id=? and set_type=? and set_id=? and flag!=-1";
		MemberCardHierarchySubjoin items=jcTemplate.INSTANCE.get(MemberCardHierarchySubjoin.class, sql, mchs.mch_id,mchs.set_type,mchs.set_id);
		if (items==null) {
			MemberCardHierarchySubjoin item=new MemberCardHierarchySubjoin();
			item.set_discount=mchs.set_discount;
			item.c_time=new Timestamp(new Date().getTime());
			item.set_id=mchs.set_id;
			item.setSet_type(mchs.set_type);
			item.mch_id=mchs.mch_id;
			return jcTemplate.INSTANCE.save(item);
		}else {
			MemberCardHierarchySubjoin item=new MemberCardHierarchySubjoin();
			item.id=items.id;
			item.mch_id=items.mch_id;
			item.setSet_type(items.set_type);
			item.set_id=items.set_id;
			item.set_discount=mchs.set_discount;
			item.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.INSTANCE.update(item);
			
			
			
		}
	}
 
	public MemberCardHierarchySubjoin getBySet_type(BigInteger mch_id){
		String sql = "select * from MemberCardHierarchySubjoin where mch_id=? and set_type='0000' and flag!=-1"
		return jcTemplate.get(MemberCardHierarchySubjoin.class, sql, mch_id);
	}
	public List<MemberCardHierarchySubjoin> getByMch_id(BigInteger mch_id){
		String sql ="select * from MemberCardHierarchySubjoin where mch_id=? and set_type!='0000' and flag!=-1"
		return jcTemplate.find(MemberCardHierarchySubjoin.class, sql, mch_id);
	}
	
	public List<MemberCardHierarchySubjoin> getList(BigInteger mch_id){
		String sql ="select * from MemberCardHierarchySubjoin where mch_id=?  and flag!=?"
		return jcTemplate.find(MemberCardHierarchySubjoin.class, sql, mch_id,-1);
	}

}
