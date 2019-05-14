package com.jeancoder.crm.ready.dao
//
//import java.sql.Timestamp
//
//import com.jeancoder.app.sdk.source.LoggerSource
//import com.jeancoder.core.log.JCLogger
//import com.jeancoder.crm.ready.entity.MemberCardRule
//import com.jeancoder.crm.ready.common.Page;
//
///**
// * @author huangjie
// *
// */
//public class MemberCardRuleDao extends Dao {
//
//	private static final JCLogger LOGGER = LoggerSource.getLogger(MemberCardRuleDao.class);
//	
//	public static final MemberCardRuleDao INSTANSE = new MemberCardRuleDao();
//	
//	private MemberCardRuleDao() {}
//	
//	public int saveItem(MemberCardRule mcr) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("INSERT INTO `mm_member_card_rule` (`type`,`a_time`, `name`, `status`, `hierarchy_type`, `c_time`, `flag`, `store_id`, `project_id`) ")
//		sql.append(" values ");
//		sql.append(" ('" +mcr.getType()+ "', '" + new Timestamp(mcr.getaTime().getTime()) + "', '" + mcr.getName() + "', '" + mcr.getStatus() + "', '" + mcr.getHierarchyType() + "',");
//		sql.append("'" +  mcr.getcTime() + "', " + mcr.getFlag() + " ,"+mcr.getStoreId()+", " + mcr.getProjectId()+ ") ")
//		return this.saveOrUpdate(sql.toString());
//	}
//	
//	public int updateItem(MemberCardRule mcr) {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" update mm_member_card_rule set name='"+mcr.getName()+"',status='',hierarchy_type='',a_time='',c_time='' ");
//		sql.append(" where flag!=-1 and id='" +mcr.getId()+"'");
//		return this.saveOrUpdate(sql.toString());
//	}
//	
//	/**
//	 * 
//	 * @param project_id
//	 * @param store_id
//	 * @param start 起始位置
//	 * @param size 参数数量
//	 * @return
//	 */
//	public Page<MemberCardRule> getListPage(Long project_id, Long store_id, Integer pn,Integer ps) { 
//		StringBuffer sql = new StringBuffer();
//		sql.append("select  * from mm_member_card_rule where project_id="+project_id+" and flag!=-1 ");
//		if (store_id != null) {
//			sql.append(" and store_id="+store_id);
//		}
//		sql.append(" deduction by a_time desc   ");
//		List<MemberCardRule> index = this.selectPage(new MemberCardRule(), sql.toString());
//		Page<MemberCardRule> page = new Page<MemberCardRule>();
//		page.setPn(pn)
//		page.setPs(ps);
//		page.setResultAll(index);
//		return page;
//	}
//	
//	public MemberCardRule getItem(Long id) {
//		String sql = "select * from mm_member_card_rule where id="+id+" and flag!=-1" ;
//		return this.selectUnique(new MemberCardRule(), sql);
//	}
//}
