package com.jeancoder.crm.internal.h5.user.upnew

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.StoreInfoDto
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.order.NotifyObj
import com.jeancoder.crm.ready.dto.order.OrderMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.McPreOrderItem
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.entity.OrderMc
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.service.OrderMcService
import com.jeancoder.crm.ready.service.PreMoService
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MD5Util
import com.jeancoder.crm.ready.util.NativeUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil
/**
 * 创建会员卡购买订单
 */
JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName());

def pid = JC.internal.param('pid');
String h_id = JC.internal.param("h_id");//等级id
String mc_pwd = JC.internal.param("mc_pwd");
String apid = JC.internal.param("apid");
// 根据apid 查询用户的手机号
String mobile = JC.internal.param("mobile");
def sid = JC.internal.param("sid")//等级id

try {
	pid = new BigInteger(pid);
	StoreInfoDto store = null;
	if(sid != null) {
		store = JC.internal.call(StoreInfoDto, 'project', '/incall/store_by_id', ['id':sid]);	//h5获取门店不限制pid，所以不传pid
	}
	def sname = null;
	if (store != null) {
		sid = store.id;
		sname = store.store_name;
	}
	if (StringUtil.isEmpty(h_id)) {
		return SimpleAjax.notAvailable("param_h_empty,会员等级参数为空");
	}
	if (StringUtil.isEmpty(mc_pwd)) {
		mc_pwd = "987654";
//		return result.setData(AvailabilityStatus.notAvailable("密码为空"));
	}
	if (StringUtil.isEmpty(mobile)) {
		return SimpleAjax.notAvailable("param_mobile_empty,手机号码参数为空");
	}
	
	mc_pwd = MD5Util.getMD5(mc_pwd);
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(new BigInteger(h_id));
	
	if(mch==null) {
		return SimpleAjax.notAvailable("result_h_empty,当前会员等级不存在");
	}
	// 只有升级可以
	if(!McConstants._mch_get_upgrade_.equals(mch.getmode)) {
		return SimpleAjax.notAvailable("result_h_getmode_upgrade,当前会员等级需升级获取");
	}
	
	MemberCardRule mcr = MemberCardRuleService.INSTANSE.getItem(mch.mc_id);
	if(mcr==null ) {
		return SimpleAjax.notAvailable("result_mr_empty,当前会员规则不存在");
	}
	
	if(!mch.flag.equals(0)){
		return SimpleAjax.notAvailable("net_sale_forbit,当前会员卡禁止网络办理");
	}
	
	if(!McConstants.mc_rule_start_.equals(mcr.mcr_status)){
		return SimpleAjax.notAvailable("result_mr_status_invalid,当前会员规则暂停办理");
	}
	
	def  proj = NativeUtil.connect(SysProjectInfo.class, 'project', '/incall/project_by_id',['pid':pid.toString()]);
	List<AccountProjectMC> all_mchs = AccountProjectMcService.INSTANSE.get_by_mobile(mobile,pid);
	if(all_mchs!=null&&!all_mchs.isEmpty()) {
		for (AccountProjectMC item: all_mchs) {
			if (pid.toString().equals(item.pid.toString()) || proj.getDbpid().toString().equals(item.pid.toString())) {
				return SimpleAjax.notAvailable("mobile_repeat,该手机号已经拥有会员卡");
			}
		}
	}
//	
//	if(AccountProjectMcService.INSTANSE.getsum(mobile)){
//		return result.setData(AvailabilityStatus.notAvailable("该用户已拥有会员卡不能再次购卡"));
//	}
	List<McPreOrderItem> orderItem= PreMoService._instance_.get_item_by_mch_id(new BigInteger(h_id));
	if (orderItem==null || orderItem.size() == 0) {
		return SimpleAjax.notAvailable("mc_limit_exceed,会员卡已经抢光啦");
	}
	OrderMc orderMc = OrderMcService.INSTANSE.create_order(new BigInteger(apid),pid,sid,sname,mobile, mch, mc_pwd);
	OrderMcDto orderDto = new OrderMcDto();
	orderDto.order_no = orderMc.order_no;
	orderDto.total_amount = orderMc.total_amount;
	orderDto.pay_amount = orderMc.pay_amount;
	orderDto.o_c = orderMc.o_c;
	orderDto.init_pwd = orderMc.init_pwd;
	def order_data = JackSonBeanMapper.toJson(orderDto);
	order_data = URLEncoder.encode(order_data);
	order_data = URLEncoder.encode(order_data);
	
	SimpleAjax trade = JC.internal.call(SimpleAjax, 'trade', "/incall/create_trade", ['oc':'8000', 'od':order_data, 'pid':pid]);
	return trade
} catch (Exception e) {
	Logger.error("创建充值订单失败", e);
	return SimpleAjax.notAvailable("mc_exception,创建失败"+e.message);
}


