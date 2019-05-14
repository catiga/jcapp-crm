package com.jeancoder.crm.entry.h5.sys

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.dto.JsapiTicket
import com.jeancoder.crm.ready.dto.ProjectFrontConfig
import com.jeancoder.crm.ready.dto.WxAccessToken
import com.jeancoder.crm.ready.dto.WxJsObj
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.Questionnaire
import com.jeancoder.crm.ready.service.QuesService
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.crm.ready.util.EncodeUtils
import com.jeancoder.crm.ready.util.GlobalHolder
import com.jeancoder.crm.ready.util.MD5Util


def domain = GlobalHolder.proj.domain;
// 取 微信公众号 类型 配置
ProjectFrontConfig supp_config = JC.internal.call(ProjectFrontConfig, 'project', '/incall/frontconfig', [pid:GlobalHolder.proj.id, app_type:'20']);

def app_id = supp_config.app_id;
def app_secret = supp_config.app_key;
def back_url = 'http://' + domain + '/crm/h5/sys/check_code';

println app_id + '======' + app_secret;


back_url = URLEncoder.encode(back_url, "UTF-8");
def redirect_url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + app_id + '&redirect_uri=' + back_url + '&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect';

ResponseSource.getResponse().sendRedirect(redirect_url);




