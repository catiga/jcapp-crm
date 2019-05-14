package com.jeancoder.crm.ready.util

import java.text.SimpleDateFormat

class DateUtil {
	private static SimpleDateFormat _sdf_yyyyMMdd_ = new SimpleDateFormat("yyyy-MM-dd");
	
	//   得到当前时间
	public static String  getDateStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()
	}
	
	public static String getDate(Long dateStr){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(dateStr);
			return sdf.format(date);
		}catch(Exception e){
			return null;
		}
	}
	
	
	//  得到当前时间
	public static String  getDate(String format){
		try{
			SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
			return df.format(new Date());// new Date()
		}catch(Exception e){
			return "";
		}
	}
	
	public static Date  getDate(Date data, String format){
		try{
			SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
			return df.parse(df.format(data));
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
	}
	
	// 根据时间字符串 和格式得到时间
	public static Date getDate(String dateStr,String format){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		}catch(Exception e){
			return null;
		}
	}
	
	// 将格式format1 的时间字符串转换成 format2
	public static Date getDate(String dateStr,String format1,String format2){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format1);
			SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
			Date date =  sdf.parse(dateStr);
			sdf2.format(date);
			//System.out.println(sdf2.format(date));
			return sdf2.parse(sdf2.format(date));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//得到当天是星期几
	public static String getWeekDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(new Date());
		return week;
	}
	
	//得到日期是星期几
	public static int getWeekDate(Date date){
		String[] weeks = ["星期一","星期二","星期三","星期四","星期五","星期六","星期日"] as String[];
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(date);
		//System.out.println(week);
		for(int i = 1; i<= weeks.length; i++){
			if(weeks[i-1].equals(week)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * 判断当前时间是否在时间段呢  开闭
	 * @param dateStart  开始时间
	 * @param dateEnd   结束时间
	 * @param dateCurrent  当前时间
	 * @return
	 */
	public static boolean period(Date dateStart,Date dateEnd,Date dateCurrent){
		//before  在之前则返回true 否则返回 false
		//after  在之前则返回false 否则返回 true
		if(dateCurrent.compareTo(dateStart) == 0  ||  dateCurrent.compareTo(dateEnd) == 0){
			return true;
		}
		if(dateCurrent.before(dateEnd) && dateCurrent.after(dateStart)){
		   return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 卡劵在线选座  判断是否符合按日分时
	 * @param dayStart
	 * @param dayEnd
	 * @param timeStart
	 * @param timeEnd
	 * @param dateCurrent
	 * @return
	 */
	public static boolean period_online_date(String dayStart,String dayEnd,String timeStart,String timeEnd,Date dateCurrent){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(dayStart);
			Date end = sdf.parse(dayEnd);
			Date current = getDate(dateCurrent,"yyyy-MM-dd");
			if(!period(start, end, current)){
				//System.out.println("日期不在范围");
				System.out.println(dayStart+"__"+ dayEnd +"__" + dateCurrent);
				return false;
			}
			sdf = new SimpleDateFormat("HH:mm");
			start = sdf.parse(timeStart);
			end = sdf.parse(timeEnd);
			current = getDate(dateCurrent,"HH:mm");
			if(!period(start, end, current)){
				//System.out.println("时间不在范围");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			//System.out.println("错误");
			return false;
		}
	}
	 
	
	public static boolean period_online_week(String weeks,String timeStart,String timeEnd,Date dateCurrent){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			int week = getWeekDate(dateCurrent);
			//System.out.println(weeks +"星期"+week);
			if(weeks.indexOf(week+"") <= -1){
				//System.out.println("星期步子啊范围"+ weeks.indexOf(week) );
				return false;
			}
			sdf = new SimpleDateFormat("HH:mm");
			Date start = sdf.parse(timeStart);
			Date end = sdf.parse(timeEnd);
			Date current   =  getDate(dateCurrent,"HH:mm");
			if(!period(start, end, current)){
				return false;
			}
			return true;
		}catch(Exception e){
			e.printStackTrace()
			return false;
		}
	}
	
	public static String format_yyyyMMdd(Date yyyyMMdd) {
		if(yyyyMMdd==null) {
			return null;
		}
		return _sdf_yyyyMMdd_.format(yyyyMMdd);
	}
	
	//星期天转换方法
	public static String[] compareM_to_X(String[] s){
		for (ss in s) {
			switch(ss)
			{
				case "周一":
					ss = "星期一"
					break;
				case "周二":
					ss = "星期二"
					break;
				case "周三":
					ss = "星期三"
					break;
				case "周四":
					ss = "星期四"
					break;
				case "周五":
					ss = "星期五"
					break;
				case "周六":
					ss = "星期六"
					break;
				default:
					ss = "星期日"
					break;
			}
		}
	}
	//时分时间转化为分
	public static Integer compareH_to_L(String s){
		String[] ss = s.split(":");
		return ss[0]*60+s[1];
	}
	
	//周分时间格式转换
	/*public static String[] compareW_to_R(String s){
		StringBuffer wr = new StringBuffer();
		String[] ss = s.split("/");
		for (ssi in ss) {
			String[] sss = ssi.split("|");
			String[] sssi = sss[0].split(",");
			String[] sssj = sss[0].split("-");
			for (sssii in sssi) {
				switch(sssii)
				{
					case "周一":
						sssii = "1"
						break;
					case "周二":
						sssii = "2"
						break;
					case "周三":
						sssii = "3"
						break;
					case "周四":
						sssii = "4"
						break;
					case "周五":
						sssii = "5"
						break;
					case "周六":
						sssii = "6"
						break;
					default:
						sssii = "7"
						break;
				}
				wr.append(sssii);
			}
		}
	}*/
	
	public static String compareW_to_R(String s,String s1){
		if(s.equals("w")){
			return s1.replace("周一", "1").replace("周二", "2").replace("周三", "3").replace("周四", "4").replace("周五", "5").replace("周六", "6").replace("周日", "7").replace("|", ";").replace("-", ";");
		}else if(s.equals("d")){
			String[] ss = s1.split("/");
			StringBuffer wr = new StringBuffer("");
			for(int i=0;i<ss.length;i++){
				String[] sss = ss[i].split("[|]");
				if(i!=0){
					wr.append("/").append(sss[0].replace("——", ";")).append(";").append(sss[1].replace("-", ";"));
				}else{
				wr.append(sss[0].replace("——", ";")).append(";").append(sss[1].replace("-", ";"));}
			}
			return wr.toString();
		}
	}
	
	public static String compareR_to_W(String s,String s1){
		String[] ss = s1.split("/");
		StringBuffer wr = new StringBuffer("");
		if(s.equals("w")){
			for(int i=0;i<ss.length;i++){
				String[] sss = ss[i].split(";");
				if(i!=0){
				    wr.append("/").append(sss[0].replace("1", "周一").replace("2", "周二").replace("3", "周三").replace("4", "周四").replace("5", "周五").replace("6", "周六").replace("7", "周日")).append("|").append(sss[1]).append("-").append(sss[2]);
				}else{
					wr.append(sss[0].replace("1", "周一").replace("2", "周二").replace("3", "周三").replace("4", "周四").replace("5", "周五").replace("6", "周六").replace("7", "周日")).append("|").append(sss[1]).append("-").append(sss[2]);
				}
			}
			return wr.toString();
		}else if(s.equals("d")){
			for(int i=0;i<ss.length;i++){
				String[] sss = ss[i].split(";");
				if(i!=0){
				    wr.append("/").append(sss[0]).append("——").append(sss[1]).append("|").append(sss[2]).append("-").append(sss[3]);
				}else{
					wr.append(sss[0]).append("——").append(sss[1]).append("|").append(sss[2]).append("-").append(sss[3]);
				}
			}
			return wr.toString();
		}
	}
	
	public static void main(String[] arg) {
//		Date date = getDate("2018-09-05 10:00:00","yyyy-MM-dd HH:mm:ss");
//		Long a = date.getTime() + 10*24*60*60*1000;
//		println getDate(a);
		println period_online_week("1,2,3,4,5,6,7", "19:23", "21:00", new Date());
	}
}
