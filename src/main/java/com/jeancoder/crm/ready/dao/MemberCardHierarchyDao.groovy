package com.jeancoder.crm.ready.dao
//
//import java.nio.ShortBuffer
//import java.sql.Timestamp
//
//import com.jeancoder.app.sdk.source.LoggerSource
//import com.jeancoder.core.log.JCLogger
//import com.jeancoder.crm.ready.entity.MemberCardHierarchy;
//import com.jeancoder.crm.ready.util.StringUtil
//import com.jeancoder.crm.ready.common.Page;
//
//public class MemberCardHierarchyDao extends Dao {
//	
//	private static final JCLogger LOGGER = LoggerSource.getLogger(MemberCardHierarchyDao.class);
//	
//	public static final MemberCardHierarchyDao INSTANSE = new MemberCardHierarchyDao();
//	
//	
//	
//	public Integer saveItem(MemberCardHierarchy mch) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("INSERT INTO `mm_member_card_hierarchy` ( `card_rule_id`, `num`, `name`, `type`, `a_time`, `c_time`, `flag`, `rules`) ");
//		sql.append("  VALUES ");
//		sql.append("  VALUES ("+ mch.getCardRuleId() +", "+ mch.getNum()+", '"+mch.getName()+"', ");
//		sql.append(" ' " + mch.getType() + "', '"+ new Timestamp(mch.getaTime().getTime()) + "', '"+mch.getcTime() +"', 0, '" +mch.getRules() +"'" );
//		this.saveOrUpdate(sql);
//	}
//	
//	
//	public Integer updateItem(MemberCardHierarchy mch) {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" update  mm_member_card_hierarchy set  ");
//		sql.append(" num="+ mch.getNum() + ",name=" + mch.getName() + ",c_time="+ mch.getcTime() + ",rules=" + mch.getRules());
//		sql.append(" where flag!=-1 and id=" + mch.getId());
//		this.saveOrUpdate(sql);
//	}
//	
//	public Integer deleteItem(Long id) {
//		String sql = " update mm_member_card_hierarchy set flag=-1,c_time='" + new Timestamp(new Date().getTime()) + "' where id=" + id;
//		this.saveItem(sql);
//	}
//	
//	public Page<MemberCardHierarchy> getPage(Long mc_id, Integer pn,Integer ps) {
//		String sql = "select * from mm_member_card_hierarchy where card_rule_id=" + mc_id + " flag!=-1";
//		List<MemberCardHierarchy> index = this.selectList(new MemberCardHierarchy(), sql);
//		Page<MemberCardHierarchy> page = new Page<MemberCardHierarchy>();
//		page.setPn(pn)
//		page.setPs(ps);
//		page.setResultAll(index);
//		return page;
//	}
//	
//	public MemberCardHierarchy  getItem(Long id) {
//		String sql = "select * select from mm_member_card_hierarchy where id=" + id + "and flag!=-1";
//		return this.selectUnique(new MemberCardHierarchy(), sql);
//	}
//	
//}
