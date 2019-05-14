package com.jeancoder.crm.ready.mcbridge.bring

import com.jeancoder.crm.ready.mcbridge.dto.ItemDto
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.dto.MCCompute
import com.jeancoder.crm.ready.mcbridge.ret.MCRet
import com.jeancoder.crm.ready.mcbridge.ret.MCRetDetail
import com.jeancoder.crm.ready.mcbridge.ret.MCardLevelRet
import com.jeancoder.crm.ready.mcbridge.ret.McPayMovieRet
import com.jeancoder.crm.ready.mcbridge.ret.TicketRefundRet
import com.piaodaren.ssc.model.TicketFlagResult
import com.piaodaren.ssc.model.TicketRefundResult

//import com.piaodaren.mcbridge.dto.MCAuthFix;
//import com.piaodaren.pe1.entity.account.AccountProjectMC;
//import com.piaodaren.pe1.entity.mc.MemberCardRule;
//import com.piaodaren.pe1.entity.order.IOrder;
//import com.jeancoder.crm.ready.mcbridge.ret.binding.MCPrice;

public interface MCBring {
	//计算商品订单会员价格
	public MCCompute compute_mc_goods_price(def mct, String c_id, List<ItemDto> items, Map<String,Object> param);
	
	//计算票务订单会员价格
	public MCCompute compute_mc_movie_price(def mct, String c_id, List<ItemDto> items, Map<String,Object> param);
	
	//校验会员卡权限
	public MCRet auth_mc(def mc_rule, String c_id, String mc_num, String mc_pwd, MCAuthFix auth_fix,Map<String, Object> param);
	
	/**
	 * @param mc_rule 会员卡对应的规则
	 * @param c_id  影院编号
	 * @param mc_num 会员卡编号
	 * @param fixx   会员相关信息
	 * @param param
	 * @return
	 */
	public MCRetDetail get_detail(String c_id, String mc_num, MCAuthFix fixx, Map<String,Object> param);
	
	//票务订单使用会员卡完成付款
	public MCRet pay_movie_mc(def mct, List<ItemDto> items, Map<String,Object> param);
	public MCRet pay_goods_mc(def mct, List<ItemDto> items, Map<String,Object> param);
	
	// 查询取票码
	public McPayMovieRet query_ticket_code_by_mc(def c_id, def order_on, def lock_flag);
	// 会员退票
	public TicketRefundRet tickes_refund_mc(String c_id, String inner_order_no, String ticket_flag1, String ticket_flag2, String seats,String lock_flag);
	
	
	//会员卡充值
	public MCRet mc_recharge(def mct, String recharge_amount, String pay_amount, String serial_num,  Map<String,Object> param);
	
	public MCardLevelRet query_levels(String c_id);
	
	
	public void drawback(String amount,Long apmcid);
	
	public boolean deductions(String amount,Long apmcid);
}
