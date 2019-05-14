package com.jeancoder.crm

import java.util.List
import java.util.Map

import com.jeancoder.app.sdk.JC
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.dto.sys.AppFunction
import com.jeancoder.crm.ready.dto.sys.SysFunction
import com.jeancoder.crm.ready.util.FuncUtil
import com.jeancoder.crm.ready.util.JackSonBeanMapper
import com.jeancoder.crm.ready.util.NativeUtil

JC.interceptor.add("project/PreInterceptor", null);
JC.interceptor.add("token/PreInterceptor", null);
JC.interceptor.add("mod/PreInterceptor", null);
JC.interceptor.add("trade/PreInterceptor", null);


SysFunction mod_g_1 = FuncUtil.build(1, '会员规则管理', null, 'index', 'fa-shopping-cart');
SysFunction mod_g_2 = FuncUtil.build(3, '注册用户与会员', null, 'user', 'fa-shopping-cart');

SysFunction mod_g_3 = FuncUtil.build(2, '预制卡管理', null, 'predo', 'fa-shopping-cart');
SysFunction mod_g_3_1 = FuncUtil.build(201, '预制卡订单', 2, 'predo/index', 'fa-shopping-cart', 2);

SysFunction mod_g_2_2 = FuncUtil.build(302, '会员管理', 3, 'mc/list', 'fa-shopping-cart', 2);
SysFunction mod_g_2_1 = FuncUtil.build(301, '注册用户管理', 3, 'user/index', 'fa-shopping-cart', 2);

List<SysFunction> result = [mod_g_1];
result.addAll([mod_g_3, mod_g_3_1]);
result.addAll([mod_g_2, mod_g_2_2, mod_g_2_1]);

NativeUtil.connect('project', '/incall/mod/reg_mods', [app_code:'crm', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);
