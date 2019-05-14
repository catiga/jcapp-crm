package com.jeancoder.crm.entry.hierarchy.equity

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.entity.MemberCardHierarchySubjoin
import com.jeancoder.crm.ready.service.MemberCardHierarchySubjoinService
import com.jeancoder.crm.ready.util.DataUtils
import com.jeancoder.crm.ready.util.MoneyUtil
import com.jeancoder.crm.ready.util.StringUtil

/*
 *保存会员对单个商品的折扣
 *
 */
//@RequestParam(value = "mch_id") Long mch_id,
//@RequestParam(value = "mchs_type") String mchs_type,
//@RequestParam(value = "set_id") String set_id,
//@RequestParam(value = "set_discount",required=false) String set_discount
//MemberCardHierarchySubjoin mchs = new MemberCardHierarchySubjoin();
//mchs.setMch_id(mch_id);
//mchs.setSet_type(mchs_type);
//mchs.setSet_id(Long.valueOf(set_id));
//Float dis = null;
//if(set_discount != null && !"".equals(set_discount)){
//	dis = Float.valueOf(set_discount)/10;
//}
//mchs.setSet_discount(dis);
//mcService.mchs_fetch_save(mchs);
//return AvailabilityStatus.available(new String[]{}, new RetObj() {
//	@Override
//	public Object getObj() {
//		return null;
//	}
//});
Result result =new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
MemberCardHierarchySubjoinService mchService=new MemberCardHierarchySubjoinService();
try{
	String mchr_id= request.getParameter("mch_id");
	String mchr_type=request.getParameter("set_type");
	String mchr_set_id=request.getParameter("set_id");
	String mchr_set_discount=request.getParameter("set_discount");

	if (StringUtil.isEmpty(mchr_id)||StringUtil.isEmpty(mchr_type)||StringUtil.isEmpty(mchr_set_id)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	String set_discount= mchr_set_discount.trim();;
	if (StringUtil.isEmpty(set_discount)) {
		
		set_discount="0";
	}else{
		set_discount=mchr_set_discount;
	}

	MemberCardHierarchySubjoin mchs =new MemberCardHierarchySubjoin();
	mchs.mch_id=new BigInteger(mchr_id.toString());
	mchs.set_type = mchr_type;
	mchs.set_id=new BigInteger(mchr_set_id.toString());
	mchs.set_discount = MoneyUtil.get_cent_from_yuan(set_discount);
	if (!(DataUtils.isNumber(mchr_id)&&DataUtils.isNumber(mchr_set_id)&&DataUtils.isNumber(set_discount))) {
		return AvailabilityStatus.notAvailable("输入的参数类型不符合要求");
	}

	Float num=Float.parseFloat(set_discount);
	if (num>=0&&num <=10) {
		String resultStr =mchService.saveItem(mchs);
		if (!StringUtil.isEmpty(resultStr)) {
			AvailabilityStatus.notAvailable(resultStr)
		}
		return AvailabilityStatus.available();
	}

	return AvailabilityStatus.notAvailable("输入的折扣不符合");

}catch (Exception e) {
	Logger.error("保存会员卡规则失败", e);
	return result.setData(AvailabilityStatus.notAvailable("添加失败"));
}