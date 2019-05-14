package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import org.codehaus.groovy.transform.tailrec.VariableReplacedListener

import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.entity.McPreOrderInfo
import com.jeancoder.crm.ready.entity.McPreOrderItem
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.exception.McrException
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate

class PreMoService {

	final static PreMoService _instance_ = new PreMoService();
	
	static JcTemplate jc_template = JcTemplate.INSTANCE();
	
	private PreMoService() {}
	
	static INSTANCE() {
		return _instance_;
	}
	
	def save(McPreOrderInfo order) {
		return jc_template.save(order);
	}
	
	def update(McPreOrderInfo order) {
		return jc_template.update(order);
	}
	
	def get(def order_id) {
		if(!order_id) return null;
		
		String sql = 'select * from McPreOrderInfo where flag!=?';
		def param = []; param.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?';
			param.add(GlobalHolder.proj.id);
		}
		sql += ' and id=?'; param.add(order_id);
		return jc_template.get(McPreOrderInfo, sql, param.toArray());
	}
	
	def get_items(def page, def pre_order, def pub_ss, def card_num) {
		if(pre_order==null) return null;
		if(GlobalHolder.proj.root!=1&&GlobalHolder.proj.id!=pre_order.pid) return null;
		
		String sql = 'select * from McPreOrderItem where order_id=?';
		def param = [];
		param.add(pre_order.id);
		if(pub_ss) {
			sql += ' and pub_ss=?';
			param.add(pub_ss);
		}
		if(card_num) {
			sql += ' and card_num=?';
			param.add(card_num);
		}
		sql += ' order by c_time desc';
		return jc_template.find(McPreOrderItem, page, sql, param.toArray());
	}
	
	def find(def page, def order_num, def mcr_id, def mch_id, def card_num) {
		String sql = 'select * from McPreOrderInfo where flag!=?';
		def param = []; param.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and pid=?';
			param.add(GlobalHolder.proj.id);
		}
		if(!StringUtil.isEmpty(order_num)) {
			sql += ' and order_num=?';
			param.add(order_num);
		}
		if(!StringUtil.isEmpty(mcr_id)) {
			sql += ' and mcr_id=?'; param.add(mcr_id);
		}
		if(!StringUtil.isEmpty(mch_id)) {
			sql += ' and mch_id=?'; param.add(mch_id);
		}
		if(!StringUtil.isEmpty(card_num)) {
			sql += ' and id in (select order_id from McPreOrderItem where card_num=?)'; param.add(card_num);
		}
		sql += ' order by a_time desc';
		return jc_template.find(McPreOrderInfo, page, sql, param.toArray());
	}
	
	def batch_generate_item(def order_items) {
		order_items.each{it -> jc_template.save(it)}
	}
	//生成会员卡编号
	def get_cards_identifier(def mc_rule,def mch_rule, def append_current){
		String mc_rule_prefix = mc_rule.prefix;
		String mc_hier_prefix = mch_rule.prefix;
		if(mc_hier_prefix==null) {
			mc_hier_prefix = "";
		}
		//开始设定前缀
		String prefix = "";
		if(mc_rule.a_mc_prefix.equals(1)) {
			prefix = prefix + mc_rule_prefix;
		}
		if(mc_rule.a_mch_prefix.equals(1)) {
			prefix = prefix + mc_hier_prefix;
		}
		//生成校验位
		String card_no = prefix + append_current;
		card_no = card_no + this.nextInt(1, 9);
		String check_code = this.compute_suff(card_no);
		card_no = card_no + check_code;
		return card_no;
	}
	
	//通过order_id查询出会员卡编号
	public List<McPreOrderItem> get_item_by_mch_id(BigInteger mch_id){
		String sql="select moi.* from McPreOrderItem moi, McPreOrderInfo mo where moi.flag !=? and moi.pub_ss=? and   moi.order_id=mo.id  and mo.mch_id=?  and mo.flag !=? order by moi.id" ;
		return jc_template.find(McPreOrderItem.class, sql,-1,McConstants.mc_info_item_init_,mch_id,-1);
	}
	
	public List<McPreOrderItem> get_item_by_mc_num(BigInteger pid, String mc_num) {
		String sql="select item.* from McPreOrderItem item, McPreOrderInfo info, MemberCardRule mcr where " ; 
				sql += " mcr.flag !=? and   mcr.pid=?  and mcr.id = info.mcr_id "
				sql +="and info.flag !=?  and info.id = item.order_id "
				sql += "and item.flag !=? and item.pub_ss=? and item.cards_identifier=? ;" ;
		return jc_template.find(McPreOrderItem.class, sql,-1,pid,-1,-1,McConstants.mc_info_item_lock_,mc_num);
	}
	
	public McPreOrderItem getItem(BigInteger id) {
		String sql="select * from McPreOrderItem  where id=? and flag !=?" ;
		return jc_template.get(McPreOrderItem.class, sql,id,-1);
	}
	
	
	public McPreOrderInfo getInfo(BigInteger id) {
		String sql="select * from McPreOrderInfo where flag !=? and id=?" ;
		return jc_template.get(McPreOrderInfo.class, sql,-1,id);
	}
	
	/**
	 * @param mc_num
	 */
	public void lock_mc_num(McPreOrderItem  item) {
		item.pub_ss = McConstants.mc_info_item_lock_;
		item.c_time = new Timestamp(new Date().getTime());
		jc_template.update(item);
	}
	
	/**
	 * @param mc_num
	 */
	public void unlock_item(McPreOrderItem  item) {
		item.pub_ss = McConstants.mc_info_item_init_;
		item.c_time = new Timestamp(new Date().getTime());
		jc_template.update(item);
	}
	
	
	public void updae(McPreOrderItem item){
		item.c_time = new Timestamp(new Date().getTime());
		jc_template.update(item);
	}
	
	protected int nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
	protected String compute_suff(String aim_str) {
		def mod_array = [
			7,
			9,
			10,
			5,
			8,
			4,
			2,
			1,
			6,
			3,
			17,
			23,
			37,
			43,
			59,
			102,
			88,
			68,
			99,
			33,
			66,
			69,
			96,
			83,
			33,
			39
		];
		int sum = 0;
		for(int i=0; i<aim_str.length(); i++) {
			int c = aim_str.charAt(i);
			int tmp_index = Integer.valueOf(c);
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		return sum_x + "";
	}
}
