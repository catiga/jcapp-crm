package com.jeancoder.crm.internal.api.order

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.order.AccountMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.bring.MCBring
import com.jeancoder.crm.ready.mcbridge.dto.ItemDto
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.dto.MCCompute
import com.jeancoder.crm.ready.mcbridge.ret.MCRetDetail
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardHierarchySubjoinService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/*
 * 计算会员票价
 */
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
def t_num =   new Date().getTime();
t_num = t_num.toString() + new Random().nextInt(1000).toString();
MCCompute mcc = new MCCompute();
try {
	String p = CommunicationSource.getParameter("p"); //  
	def pid = CommunicationSource.getParameter("pid");
	def sid = CommunicationSource.getParameter("sid");
	Logger.info('mc_movie_price:{t_num=' + t_num + ',p='+p +  ',pid=' + pid+  ',sid=' + sid);
	if (StringUtil.isEmpty(p) || pid == null || StringUtil.isEmpty(pid.toString())) {
		mcc.code = "1000";
		mcc.msg = JsConstants.input_param_null;
		return mcc;
	}
	pid = new BigInteger(pid.toString());
	def  deto  = JackSonBeanMapper.jsonToMap(p);
	def card_code = deto.card_code;
	def lock_flag = deto.lock_flag;
	if (StringUtil.isEmpty(card_code)) {
		mcc.code = "1000";
		mcc.msg = JsConstants.input_param_null;
		return  mcc;
	}
	card_code = AccountProjectMCUtil.getMcNum(card_code);
	AccountProjectMC account =  AccountProjectMcService.INSTANSE.get_normal_mc_by_num(pid,card_code);
	if (account == null) {
		mcc.code = "1000";
		mcc.msg = JsConstants.mc_not_found;
		return  mcc;
	}
	MemberCardHierarchy mch = MemberCardHierarchyService.INSTANSE.getItem(account.mch_id);
	if (account == null || !pid.equals(mch.mcRule.pid)) {
		mcc.code = "1000";
		mcc.msg = JsConstants.object_not_exist;
		return  mcc;
	}
//	def list = deto.g;
//	String payAmount = "0";
//	String totalAmount = "0";
//	List<ItemDto> goods_List = new ArrayList<ItemDto>();
//	for (def dto : list) {
//		def goods_id = dto[0];// 座位编号
//		def price = dto[1];
//		def level_price = dto[2];
//		//记录价格高于最低价
//		boolean flag = false;
//		if(Double.parseDouble(price.toString()) > Double.parseDouble(level_price.toString())){
//			flag = true;
//		}
//		//获取会员卡等级的折扣
//		String mch_discount = mch.cr_discount;
//		if (!StringUtil.isEmpty(mch_discount)) {
//			ItemDto g1 = new ItemDto();
//			g1.id = goods_id.toString();
//			g1.price = price;
//			g1.total_amount = price;
//			g1.mc_price = compute(price.toString(),mch_discount);
//			if(flag==true){
//				if(Double.parseDouble(g1.mc_price) < Double.parseDouble(level_price.toString())){
//					g1.mc_price = level_price.toString();
//				}
//			}
//			g1.pay_amount = g1.mc_price;
//			g1.discount =  MoneyUtil.get_yuan_from_cent(mch_discount)
//			payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
//			totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
//			goods_List.add(g1);
//			println "会员卡等级的折扣计算成功____" + JackSonBeanMapper.toJson(g1);
//			continue;
//		}
//		// 按原价返回
//		ItemDto g1 = new ItemDto();
//		g1.id = goods_id.toString();
//		g1.price = price;
//		g1.total_amount = price;
//		g1.mc_price = price;
//		g1.pay_amount = price;
//		g1.discount = "0";
//		payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
//		totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
//		goods_List.add(g1);
//	}
////	[goods_id, [cat_ids, cat_ids], price, num]
////	名字，手机号，等级，余额
//	AccountMcDto amd = new AccountMcDto(account,mch);
//	mcc.accountMc = amd;
//	mcc.code = "0";
//	mcc.items = goods_List;
//	mcc.totalAmount = totalAmount;
//	mcc.offerAmount = MoneyUtil.add(mcc.totalAmount, "-"+payAmount)
	String c_id = null;
	SimpleAjax ajax = JC.internal.call(SimpleAjax,"ticketingsys", "/store/cinema_by_id", [pid:pid,store_id:sid]);
	if (ajax != null && ajax.isAvailable() && ajax.data != null && ajax.data.size() > 0) {
		c_id = ajax.data.get(0).store_no;
	}
	def param = [:]
	param['pid'] = pid;
	param['account'] = account;
	param['mch'] = mch;
	param['lock_flag'] = lock_flag;
	def bring =  MCFactory.generate(mch.mcRule);
	MCAuthFix auth =  new MCAuthFix(account.mc_num, account.mc_mobile, account.mc_pwd);
	MCRetDetail detail = bring.get_detail(c_id, card_code, auth, param);
	// 查询成功
	if (!detail.isSuccess()) {
		mcc.code = detail.code;
		mcc.msg = detail.msg;
		return  mcc;
	}
	mcc.accountMc = new AccountMcDto(detail.detail);
	def list = deto.g;
	List<ItemDto> movies = new ArrayList<ItemDto>();
	for (def dto : list) {
		ItemDto item = new ItemDto();
		item.id = dto[0].toString();
		item.price = dto[1].toString();
		item.level_price = dto[2].toString();
		movies.add(item);
	}
	mcc = bring.compute_mc_movie_price(account, c_id, movies, param);
	AccountMcDto amd = new AccountMcDto(account,mch);
	mcc.accountMc = amd;
	return mcc;
} catch (Exception e) {
	e.printStackTrace();
	Logger.error("计算失败", e);
	mcc.code = "1000";
	mcc.msg = "计算失败"
	return mcc;
} finally {
	Logger.info('mc_movie_price:{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(mcc)+'}');
}


public String compute(String money,String set_discount) {
	if (Integer.parseInt(set_discount) < 1 || Integer.parseInt(set_discount) > 999) {
		return money;
	} 
	String m = MoneyUtil.multiple(money, set_discount);
	m = MoneyUtil.divide(m, "100");// 恢复折扣
	m = MoneyUtil.divide(m, "10");// 恢复到百分号， 1折=10%
	BigDecimal bd = new  BigDecimal(m);
	bd= bd.setScale(0,BigDecimal.ROUND_HALF_UP);
	return bd.toString();
}

