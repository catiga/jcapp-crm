package com.jeancoder.crm.internal.api.order.mc

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.common.AvailabilityStatus
import com.jeancoder.crm.ready.constant.McConstants
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.mc.AccountMcDto
import com.jeancoder.crm.ready.entity.AccountProjectMC
import com.jeancoder.crm.ready.entity.MemberCardHierarchy
import com.jeancoder.crm.ready.entity.MemberCardHierarchyDetail
import com.jeancoder.crm.ready.entity.MemberCardRule
import com.jeancoder.crm.ready.mcbridge.MCFactory
import com.jeancoder.crm.ready.mcbridge.dto.MCAuthFix
import com.jeancoder.crm.ready.mcbridge.ret.MCRetDetail
import com.jeancoder.crm.ready.service.AccountProjectMcService
import com.jeancoder.crm.ready.service.MemberCardHierarchyDetailService
import com.jeancoder.crm.ready.service.MemberCardHierarchyService
import com.jeancoder.crm.ready.service.MemberCardRuleService
import com.jeancoder.crm.ready.util.AccountProjectMCUtil
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.NativeUtil

/**获取当前用户的会员卡列表 根据手机号查询会员列表唯一标识**/

//获取当前登录用户的注册号码
AccountProjectMcService apms=new AccountProjectMcService()
MemberCardHierarchyService mchs=new MemberCardHierarchyService();
MemberCardHierarchyDetailService mchds=new MemberCardHierarchyDetailService();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

Result result =new Result();
try {
	def mobile = JC.internal.param("mobile");
	def pid = JC.internal.param('pid');
	
	
	if (mobile == null || mobile.equals("") || pid == null) {
		return AvailabilityStatus.notAvailable("参数不能为空");
	}
	def  proj = NativeUtil.connect(SysProjectInfo.class, 'project', '/incall/project_by_id',['pid':pid.toString()]);
	
	
	List<AccountProjectMC> resultList=apms.get_by_mobile(mobile,new BigInteger(pid.toString()));
	if(resultList==null||resultList.isEmpty()) {
		return AvailabilityStatus.available(new ArrayList<AccountMcDto>());
	}
	
	def sid = null;
	try {
		SimpleAjax ajax = JC.internal.call(SimpleAjax,"ticketingsys", "/store/store_list", [pid:pid])
		if (!ajax.available) {
			return ajax;
		}
		if (ajax.data == null || ajax.data.size()  == 0) {
			return SimpleAjax.notAvailable("请添加门店")
		}
		sid = ajax.data.get(0).store_no;
	} catch (e) {
		Logger.error("查询影院编号失败 ",e);
	}
	
	List<AccountMcDto> resultMC =new ArrayList<AccountMcDto>();
	for (AccountProjectMC account : resultList) {
		AccountMcDto acmd = new AccountMcDto()
		acmd.levelname= account.mc_level;
		acmd.balance= account.balance;
		acmd.point= account.point;
		acmd.mcname= account.mc_name;
		acmd.mc_num= account.mc_num;
		if (account.mch_id!=null){
			MemberCardHierarchy mch = mchs.getItem(account.mch_id);
			if (mch == null || mch.mcRule == null || !McConstants.mc_rule_start_.equals(mch.mcRule.mcr_status)) {
				continue;
			}
			MemberCardHierarchyDetail content=mchds.getMemberCardHierarchyDetail(account.mch_id);
			if(content!=null){
				acmd.content = content.content;
			}
			if(mch!=null){
				MemberCardRule mcr =  mch.getMcRule();
				acmd.outer_type = mcr.outer_type;
				acmd.rule = mcr;
				def  store_no = sid;
				def bring = MCFactory.generate(mcr)
				def param = [:];
				MCAuthFix fix = new MCAuthFix();
				fix.pwd = account.mc_pwd;
				fix.idnum = account.mc_num;
				fix.mobile = account.mc_mobile;
				param['pid'] = pid;
				param['account'] = account;
				param['mch'] = mch;
				if (account.sid != null) {
					SimpleAjax ajax = JC.internal.call(SimpleAjax,"ticketingsys", "/store/cinema_by_id", [store_id:account.sid.toString()])
					if (!ajax.available) {
						continue;
					}
					if (ajax.data != null && ajax.data.size()  != 0) {
						store_no = ajax.data.get(0).store_no;
					}
				}
				MCRetDetail detail = bring.get_detail(store_no,account.mc_num,fix,param);
				def card_level_id = null;
				if (detail.isSuccess()) {
					//MCLocalDetail detail
					acmd.levelname= account.mc_level;
					acmd.balance= detail.detail.balance;
					acmd.point= account.point;
					acmd.mcname= detail.detail.username;
					acmd.mc_num= account.mc_num;
					card_level_id = detail.detail.cardLevelId;
				} else {
					Logger.info("校验失败")
					continue;
				}
				acmd.mch_id = account.mch_id;
				if (!"0".equals(mcr.outer_type)) {
					// 外部会员卡需要更新等级id
					List<MemberCardHierarchy> result_hs = MemberCardHierarchyService.INSTANSE.getAllByMcId(mcr.id);
					def mch_id = null
					for (MemberCardHierarchy mch1 : result_hs) {
						if (card_level_id.equals(mch1.h_num)) {
							mch_id = mch.id;
							break;
						}
					}
					if (mch_id ==  null) {
						mch_id = result_hs.get(0).id;
					}
					acmd.mch_id = mch_id;
				}
				 //TODO 这里需要改动
				//按领用日期
				if(mch.validate_type=="GETTIME"){
					String[] s = mch.validate_period.split("_");
					Calendar c = Calendar.getInstance(TimeZone.getDefault());
					c.setTime(account.receive_time);
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
					//acmd.validate_period =DateUtil.getDate(mch.validate_period,"yyyy-MM-dd HH:mm:ss").getTime();
					try {
						acmd.validate_period = _sdf_yyyymmddhhmmss_.parse(mch.validate_period).getTime();
					}catch(any) {}
					acmd.validate_type=mch.validate_type;
				}
				//永久
				if(mch.validate_type=="FOREVER"){
					acmd.validate_period = null;
					acmd.validate_type = mch.validate_type;
				}
				acmd.rule = mcr;
			}
		}
		acmd.card_code=AccountProjectMCUtil.getH5CardCode(account);
		resultMC.add(acmd)
	}
	return AvailabilityStatus.available(resultMC);
} catch (Exception e) {
	Logger.error("获取可用的会员规则失败", e);
	return AvailabilityStatus.notAvailable("获取失败");
}  finally {
	 
}

