package com.jeancoder.crm.internal.api.order

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.JsConstants
import com.jeancoder.crm.ready.constant.McDetailConstant
import com.jeancoder.crm.ready.order.TradeResult
import com.jeancoder.crm.ready.service.AccountProjectMcDetailService
import com.jeancoder.crm.ready.util.CPISCoderTools
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.StringUtil

/**
 * 退款接口
 */
JCLogger Logger = LoggerSource.getLogger("mc_refund:");
TradeResult tr = new TradeResult();
tr.code = "1000";
def num =   new Date().getTime();
num = num.toString() + new Random().nextInt(1000).toString();
try {
	String detail_num = CPISCoderTools.serialNum(McDetailConstant.deduction);
	String pid  =  CommunicationSource.getParameter("pid").toString();
	String remarks  =  CommunicationSource.getParameter("remarks").toString();// 备注 可为空
	String o_num = CommunicationSource.getParameter("num");//扣款时 返回的流水号
	String on = CommunicationSource.getParameter("on");//单笔退款的订单号
	String oc = CommunicationSource.getParameter("oc");//单笔退款的订单号
	
	Logger.info('{num= '+num+ ', parameter:{pid = '+ pid + ',remarks= ' + remarks + ' ,num= '+ o_num+',on='+ on+ ',oc='+oc  + '}');
	if (StringUtil.isEmpty(pid) || StringUtil.isEmpty(o_num)) {
		tr.code = JsConstants.unknown;
		tr.err_code_des = "参数不能为空";
		return tr;
	}
	tr = AccountProjectMcDetailService.INSTANSE.refund(new BigInteger(pid), o_num, detail_num, on,oc );
	return tr;
} catch (Exception e) {
	tr.code = JsConstants.unknown;
	tr.err_code_des = "退款失败";
	Logger.error("退款失败", e);
	return tr;
} finally {
	Logger.info('{num= '+num+ ' , rules:'+ JackSonBeanMapper.toJson(tr)+'}');
}