package com.jeancoder.crm.entry.h5.gucci_with_us

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.crm.ready.constant.SimpleAjax
import com.jeancoder.crm.ready.entity.AccountSession
import com.jeancoder.crm.ready.entity.DocReader
import com.jeancoder.crm.ready.service.SessionService
import com.jeancoder.jdbc.JcTemplate

def app_id = 'wxd0569da27f196643';

def latitude = JC.request.param('latitude')?.trim();
def longitude = JC.request.param('longitude')?.trim();
def openid = JC.request.param('openid');
def token = JC.request.param('token');

AccountSession session = token==null?null:SessionService.INSTANCE().flush_session(token, 0);

//保存读取记录
DocReader doc_read = new DocReader();
doc_read.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
doc_read.ap_id = session?.ap_id;
doc_read.doc_id = BigInteger.valueOf(0L);
doc_read.flag = 0;
doc_read.ip_addr = JC.request.get().getRemoteHost();
doc_read.user_id = session?.basic_id;
doc_read.user_key = openid;
doc_read.app_id = app_id;
doc_read.latitude = latitude;
doc_read.longitude = longitude;
JcTemplate.INSTANCE().save(doc_read);

return SimpleAjax.available();



