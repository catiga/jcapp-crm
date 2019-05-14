package com.jeancoder.crm.internal.api.order.mc


import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.SysProjectInfo
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
import com.jeancoder.crm.ready.util.DateUtil
import com.jeancoder.crm.ready.util.NativeUtil

/**获取当前用户的会员卡列表 根据手机号查询会员列表唯一标识**/

//获取当前登录用户的注册号码
AccountProjectMcService apms=new AccountProjectMcService()
MemberCardHierarchyService mchs=new MemberCardHierarchyService();
MemberCardHierarchyDetailService mchds=new MemberCardHierarchyDetailService();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try {
	def mobile = JC.internal.param("mobile");
	def pid = JC.internal.param('pid');

	if (mobile == null || mobile.equals("") || pid == null) {
		return SimpleAjax.notAvailable("参数不能为空");
	}
	//def  proj = NativeUtil.connect(SysProjectInfo.class, 'project', '/incall/project_by_id',['pid':pid.toString()]);
	mobile = AccountProjectMCUtil.getMcNum(mobile);
	List<AccountProjectMC> resultList = apms.queryByMobileOrCard(new BigInteger(pid.toString()),mobile);
	if(resultList == null||resultList.isEmpty()) {
		return SimpleAjax.notAvailable("未找到会员卡");
	}

	List<AccountMcDto> resultMC =new ArrayList<AccountMcDto>();
	for (AccountProjectMC Apmc : resultList) {
		AccountMcDto acmd=new AccountMcDto()
		acmd.levelname=Apmc.mc_level;
		acmd.mch_id=Apmc.mch_id;
		acmd.balance=Apmc.balance;
		acmd.point=Apmc.point;
		acmd.mcname=Apmc.mc_name;
		acmd.mc_num=Apmc.mc_num;
		acmd.mcmobile = Apmc.mc_mobile;
		acmd.id_card = Apmc.id_card;
		if (Apmc.mch_id!=null){
			MemberCardHierarchy mch = mchs.getItem(Apmc.mch_id);
			if (mch == null || mch.mcRule == null || !McConstants.mc_rule_start_.equals(mch.mcRule.mcr_status)) {
				continue;
			}
			MemberCardHierarchyDetail content=mchds.getMemberCardHierarchyDetail(Apmc.mch_id);
			if(content!=null){
				acmd.content = content.content;
			}
			if(mch!=null){
				//TODO 这里需要改动
				//按领用日期
				if(mch.validate_type=="GETTIME"){
					String[] s = mch.validate_period.split("_");
					Calendar c = Calendar.getInstance(TimeZone.getDefault());
					c.setTime(Apmc.receive_time);
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
					acmd.validate_period =DateUtil.getDate(mch.validate_period,"yyyy-MM-dd").getTime();
					acmd.validate_type=mch.validate_type;
				}
				//永久
				if(mch.validate_type=="FOREVER"){
					acmd.validate_period = null;
					acmd.validate_type = mch.validate_type;
				}
				MemberCardRule rule=MemberCardRuleService.INSTANSE.getItem(mch.mc_id);
				if (rule!=null) {
					acmd.rule=rule;
				}
			}
		}
		acmd.card_code=AccountProjectMCUtil.getH5CardCode(Apmc);
		resultMC.add(acmd)
	}
	return SimpleAjax.available('',resultMC);
} catch (Exception e) {
	Logger.error("获取可用的会员规则失败", e);
	return SimpleAjax.notAvailable("获取失败");
}

