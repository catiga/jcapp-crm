package com.jeancoder.crm.interceptor.project

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.crm.ready.dto.SysProjectInfo
import com.jeancoder.crm.ready.util.GlobalHolder

GlobalHolder.remove();
JCRequest request = RequestSource.getRequest();
String domain = request.getServerName();
SysProjectInfo project = JC.internal.call(SysProjectInfo.class, 'project', '/incall/project', ["domain":domain]);
GlobalHolder.setProj(project);
request.setAttribute("current_project", project);
//request.setAttribute('pub_bucket', 'https://cdn.iplaysky.com/static/');

request.setAttribute('pub_bucket', 'http://static.jcloudapp.chinaren.xyz/static/')

return true;