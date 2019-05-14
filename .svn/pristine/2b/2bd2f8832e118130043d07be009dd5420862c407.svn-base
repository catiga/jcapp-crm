package com.jeancoder.crm.ready.sms

import com.jeancoder.crm.ready.util.MD5Util

class Sender {

	def static String sendMessage(String[] mobiles, String content) {
	long code = 0;
	String Info = null;
	for(String mobile : mobiles){
		String userName = "jiuaiyoupin";
		String passWord = "jiuaiyoupin123";
		String pwd = MD5Util.getMD5(userName + MD5Util.getMD5(passWord));
		String HttpURL = "http://118.178.228.184:9001/";
		String result = "";
		OutputStreamWriter out;
		BufferedReader export;
		StringBuilder params = new StringBuilder();
		params.append("username=").append(userName)
		.append("&password=").append(pwd)
		.append("&mobile=").append(mobile)
		.append("&content=").append(content)
		.append("&ext=").append("");
		try {
			URL realUrl = new URL(HttpURL + "smsSend.do");
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
			out.write(params.toString());
			out.flush();
			
			export = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF8"));
			String line = "";
			while ((line = export.readLine()) != null) {
				result += line + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//结果
		String strCode = result.split("\n")[0];
		code = Long.valueOf(strCode);
		if (code > 0) {// 成功提交
			Info = "发送成功";
		} else if (code == 0) {
			Info = "发送失败";
		} else if (code == -1) { // 用户名密码不正确
			Info = "用户名密码不正确";
		} else if (code == -2) { // 必填选项为空
			Info = "必填选项为空";
		} else if (code == -3) { // 短信内容0个字节
			Info = "短信内容0个字节";
		} else if (code == -4) { // 0个有效号码
			Info = "0个有效号码";
		} else if (code == -5) { // 余额不够
			Info = "余额不够";
		} else if (code == -10) { // 用户被禁用
			Info = "用户被禁用";
		} else if (code == -11) { // 短信内容过长
			Info = "短信内容过长";
		} else if (code == -12) { // 用户无扩展权限
			Info = "无扩展权限";
		} else if (code == -13) { // IP地址校验错
			Info = "IP校验错误";
		} else if (code == -14) { // 内容解析异常
			Info = "内容解析异常";
		} else {
			Info = "未知错误";
		}
		System.out.println("返回信息：" + Info + "--" + code + "--" + pwd);
	}
	return code+":"+Info;
}
}
