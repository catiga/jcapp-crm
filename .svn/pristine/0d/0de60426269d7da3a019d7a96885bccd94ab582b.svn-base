package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.entity.MemberCardHierarchyDetail
import com.jeancoder.jdbc.JcTemplate

class MemberCardHierarchyDetailService {
	
	private static final JCLogger LOGGER = LoggerSource.getLogger(MemberCardHierarchyService.class);

	public static final MemberCardHierarchyDetailService INSTANSE = new MemberCardHierarchyDetailService();

	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	
	public MemberCardHierarchyDetail getMemberCardHierarchyDetail(BigInteger mch_id){
		String sql = "select * from MemberCardHierarchyDetail where mch_id=? and flag!=?";
		return jcTemplate.get(MemberCardHierarchyDetail.class, sql, mch_id,-1);
	}
	
	public Integer updateMemberCardHierarchyDetail(BigInteger mch_id,String content){
		MemberCardHierarchyDetail item = getMemberCardHierarchyDetail(mch_id);
		if(null==item){
			item = new MemberCardHierarchyDetail();
			item.mch_id = mch_id;
			item.content = content;
			item.c_time = new Timestamp(new Date().getTime());
			return jcTemplate.save(item);
		}
		item.content = content;
		return jcTemplate.update(item);
	}
}
