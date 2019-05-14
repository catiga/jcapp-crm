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
import com.jeancoder.crm.ready.dto.order.AccountMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.mcbridge.dto.ItemDto
import com.jeancoder.crm.ready.mcbridge.dto.MCCompute
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardHierarchySubjoinService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.RemoteUtil
import com.jeancoder.crm.ready.util.StringUtil

/*
 * 计算会员预约价
 */
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
def t_num =   new Date().getTime();
t_num = t_num.toString() + new Random().nextInt(1000).toString();
MCCompute mcc = new MCCompute();
try {
	String p = JC.internal.param("p"); //
	def pid = JC.internal.param("pid");
	Logger.info('{t_num=' + t_num + ',p='+p +  ',pid=' + pid);
	if (StringUtil.isEmpty(p) || pid == null || StringUtil.isEmpty(pid.toString())) {
		mcc.code = "1000";
		mcc.msg = JsConstants.input_param_null;
		return mcc;
	}
	pid = new BigInteger(pid.toString());
	def  deto  = JackSonBeanMapper.jsonToMap(p);
	def card_code = deto.card_code;
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
	def list = deto.g;
	String payAmount = "0";
	String totalAmount = "0";
	List<ItemDto> goods_List = new ArrayList<ItemDto>();
	for (def dto : list) {
		//通用字段定义
		def record_id = dto[0];
		def record_name = dto[1];
		def record_amount = dto[2];
		def record_num = dto[3];
		//获取会员卡等级的折扣
		String mch_discount = account.discount;	//默认折扣项目
		if(!mch_discount) {
			mch_discount = mch.cr_discount;	//默认等级折扣，为空则读取等级折扣
		}
		
		ItemDto g1 = new ItemDto();
		g1.id = record_id.toString();
		g1.price = record_amount.toString();
		g1.total_amount = record_amount.toString();
		g1.mc_price = compute(record_amount.toString(), mch_discount);
		g1.pay_amount = g1.mc_price;
		g1.discount =  MoneyUtil.get_yuan_from_cent(mch_discount)
		payAmount = MoneyUtil.add(payAmount, g1.pay_amount );
		totalAmount = MoneyUtil.add(totalAmount, g1.total_amount );
		println "会员卡等级的折扣计算成功____" + JackSonBeanMapper.toJson(g1);
		
		goods_List.add(g1);
	}
//	[goods_id, [cat_ids, cat_ids], price, num]
//	名字，手机号，等级，余额
	AccountMcDto amd = new AccountMcDto(account,mch);
	mcc.accountMc = amd;
	mcc.code = "0";
	mcc.items = goods_List;
	mcc.totalAmount = totalAmount;
	mcc.offerAmount = MoneyUtil.add(mcc.totalAmount, "-"+payAmount)
	println "计算成功"
	return mcc;
} catch (Exception e) {
	Logger.error("计算失败", e);
	mcc.code = "1000";
	mcc.msg = "计算失败"
	return mcc;
} finally {
	Logger.info('{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(mcc)+'}');
}


public String compute(String money,String set_discount) {
	BigDecimal new_money = new BigDecimal(money);
	
	BigDecimal discount_bd = new BigDecimal(set_discount);
	BigDecimal bd_1000 = new BigDecimal(1000);
	
	return new_money.multiply(discount_bd).divide(bd_1000).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
	
	
//	if (Integer.parseInt(set_discount) < 1 || Integer.parseInt(set_discount) > 999) {
//		return money;
//	}
//	String m = MoneyUtil.multiple(money, set_discount);
//	m = MoneyUtil.divide(m, "100");// 恢复折扣
//	m = MoneyUtil.divide(m, "10");// 恢复到百分号， 1折=10%
//	BigDecimal bd = new  BigDecimal(m);
//	bd= bd.setScale(0,BigDecimal.ROUND_HALF_UP);
//	return bd.toString();
}

