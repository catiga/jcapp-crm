package com.jeancoder.crm.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.McDetailConstant
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.AccountProjectMcDetail
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.OrderRechargeMc
import com.jeancoder.crm.ready.order.OrderConstants
import com.jeancoder.crm.ready.util.CPISCoderTools
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.milepai.core.utils.data.DateUtil

class OrderRechargeService {
	static final OrderRechargeService INSTANSE = new OrderRechargeService();
	private static final JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();


	/**
	 * 充值成功
	 * @param order_no
	 * @param pid  记录当前充值门店
	 * @return
	 */
	public OrderRechargeMc recharge(AccountProjectMC account, OrderRechargeMc order, BigInteger pid) {
		//会员等级
		Integer grade = 0;
		MemberCardHierarchy account_mch = MemberCardHierarchyService.INSTANSE.getItem(account.mch_id);
		MemberCardHierarchy order_mch = null;
		if (order.mch_id != null) {
			order_mch = MemberCardHierarchyService.INSTANSE.getItem(order.mch_id);
		}
		// 构建充值记录
		AccountProjectMcDetail detail = new AccountProjectMcDetail();
		detail.pid =  pid;
		detail.acmid = account.id;
		detail.order_no = order.order_no;
		detail.o_c =  OrderConstants.OrderType._recharge_mc_;
		detail.a_time = new Date();
		detail.c_time = new Timestamp(new Date().getTime());
		detail.flag = 0;
		detail.remarks = "";
		detail.amount = order.pay_amount;
		detail.code = McDetailConstant.recharge_order;
		detail.num = CPISCoderTools.serialNum(detail.code);
		jcTemplate.save(detail);

		//根据最新的会员卡等级更新会员卡信息
		if (order_mch != null && order_mch.hindex>=account_mch.hindex) {
			account.discount=order_mch.cr_discount;
			account.goods_discount=order_mch.cr_discount;
			account.limit_discount_by_num=(order_mch.day_buy_limit ==null?0:order_mch.day_buy_limit);
			account.mch_id=order_mch.id;
			account.mc_level=order_mch.h_name;
			account.min_recharge_money=order_mch.least_recharge;
			account.period=null;
			try{
				if("FIXED".equals(order_mch.validate_type) && order_mch.validate_type != null){
					account.period = order_mch.validate_period+" 23:59:59";
				}else if("GETTIME".equals(order_mch.validate_type) && order_mch.validate_period != null){
					String[] s = order_mch.validate_period.split("_");
					Calendar c = Calendar.getInstance(TimeZone.getDefault());
					c.setTime(new Date());
					if("MONTH".equals(s[1])){
						c.add(Calendar.MONTH, Integer.valueOf(s[0]));
					}else if("YEAR".equals(s[1])){
						c.add(Calendar.YEAR, Integer.valueOf(s[0]));
					}
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND,59);
					account.period = DateUtil.format_yyyyMMddHHmmss(c.getTime());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			account.receive_time = new Timestamp(new Date().getTime());
		}
		account.c_time = new Timestamp(new Date().getTime());
		account.balance = AccountProjectMcDetailService.INSTANSE.getAmountSum(account.id);
		jcTemplate.update(account);
		return order;
	}


	//	public OrderRechargeMc create_mc_recharge_order(BigInteger pid, AccountProjectMC mc, String recharge_amount, String pay_type, AuthToken tolen) {
	//		OrderRechargeMc entity = init_recharge_order(pid, mc, recharge_amount, pay_type, 1,tolen);
	//		return entity;
	//	}

	public OrderRechargeMc getByNum(String order_no, BigInteger pid) {
		String sql = "select * from OrderRechargeMc where order_no=? and pid=? and flag!= -1";
		return jcTemplate.get(OrderRechargeMc.class, sql,order_no,pid);
	}
	//
	//	public OrderRechargeMc getAcmid(String mc_num,pid) {
	//		String sql = "select * from OrderRechargeMc where card_no=? and pid=? and flag!= -1";
	//		return jcTemplate.get(OrderRechargeMc.class, sql,mc_num,pid);
	//	}


	/**
	 * 
	 * @param pid
	 * @param mc
	 * @param amount
	 * @param pay_type
	 * @param ops   1 后台充值, 0是自充值
	 * @return
	 */
	public OrderRechargeMc create_mc_recharge_order(BigInteger pid,def sid,def sname, AccountProjectMC mc,BigInteger h_id, String amount, String pay_type, Integer ops, def tolen) {
		OrderRechargeMc entity = new OrderRechargeMc();
		entity.a_time  = new Date();
		entity.basic_id = mc.basic_id;
		entity.c_time = new Timestamp(new Date().getTime());
		entity.pay_time = new Timestamp(new Date().getTime());
		entity.flag = 0;
		entity.o_c =  OrderConstants.OrderType._recharge_mc_;
		String mall_num = nextInt(100000, 999999) + "";
		String t_r = nextInt(100, 999) + "";
		String order_no = CPISCoderTools.generateOrderNum(mall_num, t_r.substring(0, 1));
		entity.order_no = order_no;
		entity.order_status = OrderConstants._order_status_create_;
		entity.pay_amount = amount;
		entity.pid = pid;
		entity.total_amount = amount;
		entity.pay_type = pay_type;
		entity.mch_id = h_id;
		if (sid != null) {
			entity.store_id = new BigInteger(sid.toString());
			entity.store_name = sname;
		}
		if(mc!=null) {
			entity.acmid = mc.id;
			entity.card_no = mc.mc_num;
		}
		entity.ops = ops;
		if (ops.equals(1)) {
			if(tolen) {
				entity.puid = tolen.user.id;
				entity.puname = tolen.user.name;
			}
		}
		def id = jcTemplate.save(entity);
		entity.id = id;
		return entity;
	}
	
	
	def get_trade_order(JcPage<OrderRechargeMc> page, def pid, String mch_id, String start_data, String end_data,String order_status) {
		String sql = "select * from OrderRechargeMc Where 1=1 ";
		if (!StringUtil.isEmpty(start_data) && !StringUtil.isEmpty(end_data)) {
			sql +=  "and (a_time between '"+start_data+"' and '"+end_data+"') "
		}
		if (pid != null) {
			sql +=  " and pid="+ pid;
		}
		if (!StringUtil.isEmpty(order_status)) {
			sql += " and order_status in ("+order_status+")";
		}
		if (!StringUtil.isEmpty(mch_id)) {
			sql +=  " or mch_id =" + mch_id ;
		}
		sql +=  " and flag!="+ -1+" order by a_time desc";
		return jcTemplate.find(OrderRechargeMc,page, sql,null);
	}
	
	
	public OrderRechargeMc getByNo(String order_no, BigInteger pid,def o_c) {
		String sql = "select * from OrderRechargeMc where order_no=? and pid=? and flag!= -1 and o_c=?";
		return jcTemplate.get(OrderRechargeMc.class, sql,order_no,pid,o_c);
	}
	public OrderRechargeMc update_order_status(OrderRechargeMc order){
		OrderRechargeMc item = getByNo(order.order_no, order.pid,order.o_c);
		if (item == null) {
			return null;
		}
		item.order_status  = McConstants._order_status_closed_;
		item.c_time = new Timestamp(new Date().getTime());
		jcTemplate.update(item);
		return item ;
	}
	
	public void updateOrder (OrderRechargeMc order) {
		order.c_time = new Timestamp(new Date().getTime());
		jcTemplate.update(order);
	}

	protected int nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
	
	public static void main(String[] arg) {
		String str =  "123123.23123";
		println DataUtils.isNumber(str);
	}
}
