
package com.jeancoder.crm.ready.order

class OrderConstants {
	public class OrderType {
		//充值类订单，会员卡充值
		public static final String _recharge_mc_ = "8001";
		//会员卡购买或获取订单
		public static final String _get_mc_ = "8000";
		//会员卡购买或获取订单
		public static final String deduction = "8002";
		// 赠送
		public static final String gift = "8003";
		//组合订单
		public static final String _combinated_ = "9000";
	}
	
	/**
	 * 可以接受支付通知的订单状态
	 */
	public static final String _order_status_new_status_ = "00";
	//订单创建
	public static final String _order_status_create_ = "0000";
	//影票类订单锁定座位失败
	public static final String _order_status_remote_lock_failed_ = "0011";
	//影票类订单锁定座位成功
	public static final String _order_status_remote_lock_succed_ = "0012";
	//影票类订单出票失败
	public static final String _order_status_remote_ticket_failed_ = "0013";
	//用户主动座位解锁
	public static final String _order_status_remote_unlock_by_front_ = "0021";
	//后台管理座位释放
	public static final String _order_status_remote_unlock_by_admin_ = "0022";
	
	//已支付等同等待发货，简化处理;
	//如果是充值类订单，变更为该状态代表已经支付成功，但是还未充值
	public static final String _order_status_payed_ = "1000";

	//已支付 待确认订单
	public static final String _order_status_payed_confirm_ = "1001";

	//货到付款状态订单
	public static final String _order_status_payed_cod_ = "1010";
	//支付成功，发货／出票失败／充值失败
	public static final String _order_status_payed_pub_failed_ = "1013";
	//支付成功，订单超时
	public static final String _order_status_payed_timeout_ = "1014";
	
	//支付成功 发卡失败或充值失败
	public static final String _order_status_payed_op_failure_ = "1020";
	
	
	
	//已发货状态，对于票务类等同于已出票\
	public static final String _order_status_delivering_ = "2000";
	//已发货状态，对于票务类等同于已出票
	public static final String _order_status_delivering_nopub_ = "2900";
	public static final String _order_status_delivering_nopub_ok_ = "2901";
	
	//已收货状态，对于票务类等同于已入场，对于充值类订单直接变成已充值
	public static final String _order_status_taked_ = "3000";
	//电子票入场
	public static final String _order_status_taked_tcss_by_elec_ = "3010";
	//纸质票入场
	public static final String _order_status_taked_tcss_by_paper_ = "3020";
	
	//用户申请退款状态，用于此状态的相关代码段为8000
	public static final String _order_status_apply_refund_ = "8001";
	//执行退票操作，退票成功未退款；商品叫做退单成功未退款
	public static final String _order_status_refund_ok_drawback_no_ = "8100";
	//执行退票操作，退票成功退款成功；退单成功退款成功
	public static final String _order_status_refund_ok_drawback_ok_ = "8110";
	//执行退票操作，出票失败，退款成功
	public static final String _order_status_refund_ok_ticket_failed_ = "8120";
	//退款成功
	public static final String _order_status_drawback_ok_ = "8200";
	
	//规定时间未付款的订单，自动处理为关闭状态
	public static final String _order_status_closed_ = "9000";
	
	/**
	 * 未支付前 取消订单
	 */
	public static final String _order_status_cancel_ = "9001";
	public static final String _order_status_payed_cancel_ = "9101";
	
	public static final String _order_deliver_type_self_ = "1000";
	public static final String _order_deliver_type_express_ = "2000";
	public static final String _order_deliver_type_by_seats_ = "3000";
	
	/**
	 * 获取已支付订单列表
	 * @return
	 */
	public static List<String> payed_orders() {
		List<String> payed_orders = new ArrayList<String>();
		payed_orders.add(OrderConstants._order_status_payed_);
		payed_orders.add(OrderConstants._order_status_payed_confirm_);
		payed_orders.add(OrderConstants._order_status_payed_cod_);
		payed_orders.add(OrderConstants._order_status_delivering_);
		payed_orders.add(OrderConstants._order_status_taked_);

		payed_orders.add(OrderConstants._order_status_apply_refund_);
		payed_orders.add(OrderConstants._order_status_refund_ok_drawback_no_);
		payed_orders.add(OrderConstants._order_status_refund_ok_drawback_ok_);
		payed_orders.add(OrderConstants._order_status_refund_ok_ticket_failed_);
		payed_orders.add(OrderConstants._order_status_drawback_ok_);

		payed_orders.add(OrderConstants._order_status_payed_pub_failed_);
		payed_orders.add(OrderConstants._order_status_taked_tcss_by_elec_);
		payed_orders.add(OrderConstants._order_status_taked_tcss_by_paper_);
		
		return payed_orders;
	}
}
