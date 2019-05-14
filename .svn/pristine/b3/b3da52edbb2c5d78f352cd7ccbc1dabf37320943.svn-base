package com.jeancoder.crm.internal.api.order.mc

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.mc.AccountMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchyDetail
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyDetailService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil

/**
 * 根据会员卡号查询会员
 */

AccountProjectMcService apms=new AccountProjectMcService()
MemberCardHierarchyService mchs=new MemberCardHierarchyService();
MemberCardHierarchyDetailService mchds=new MemberCardHierarchyDetailService();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	def card_code = JC.internal.param("mc_num");
	card_code = AccountProjectMCUtil.getMcNum(card_code);
	def pid = JC.internal.param('pid');
	
	if (card_code == null || card_code.equals("") || pid == null) {
		return SimpleAjax.notAvailable("参数不能为空")
	}
	
	
	List<AccountMcDto> resultMC =new ArrayList<AccountMcDto>();
	List<AccountProjectMC> resultList=apms.get_by_num(card_code,new BigInteger(pid.toString()));
	if(resultList==null||resultList.isEmpty()) {
		return SimpleAjax.available("",resultMC);
	}
	
	for (AccountProjectMC mc : resultList) {
		AccountMcDto acmd = new AccountMcDto()
		acmd.levelname = mc.mc_level;
		acmd.balance = mc.balance;
		acmd.point = mc.point;
		acmd.mcname = mc.mc_name;
		acmd.mc_num = mc.mc_num;
		if (mc.mch_id != null){
			MemberCardHierarchy mch = mchs.getItem(mc.mch_id);
			if (mch == null || mch.mcRule == null || !McConstants.mc_rule_start_.equals(mch.mcRule.mcr_status)) {
				continue;
			}
			MemberCardHierarchyDetail content=mchds.getMemberCardHierarchyDetail(mc.mch_id);
			if(content!=null){
				acmd.content = content.content;
			}
			if(mch!=null){
				 //TODO 这里需要改动
				//按领用日期
				if(mch.validate_type=="GETTIME"){
					String[] s = mch.validate_period.split("_");
					Calendar c = Calendar.getInstance(TimeZone.getDefault());
					c.setTime(mc.receive_time);
					if("MONTH".equals(s[1])){
						c.add(Calendar.MONTH, Integer.valueOf(s[0]));
					}else if("YEAR".equals(s[1])){
						c.add(Calendar.YEAR, Integer.valueOf(s[0]));
					}
					acmd.validate_type=mch.validate_type;
					acmd.validate_period = c.getTime().getTime();
				}
				//固定日期
				if(mch.validate_type=="FIXED"){
					SimpleDateFormat _sdf_yyyymmddhhmmss_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						acmd.validate_period = _sdf_yyyymmddhhmmss_.parse(mch.validate_period).getTime();
					}catch(any) {}
					acmd.validate_type = mch.validate_type;
				}
				//永久
				if(mch.validate_type == "FOREVER"){
					acmd.validate_period = null;
					acmd.validate_type = mch.validate_type;
				}
				MemberCardRule rule = MemberCardRuleService.INSTANSE.getItem(mch.mc_id);
				if (rule!=null) {
					acmd.rule=rule;
				}
			}
		}
		acmd.card_code = AccountProjectMCUtil.getH5CardCode(mc);
		resultMC.add(acmd)
	}
	return SimpleAjax.available("",resultMC)
} catch (Exception e) {
	Logger.error("获取可用的会员规则失败", e);
	return SimpleAjax.notAvailable("查询会员卡规则失败")
} 

