package com.jeancoder.crm.ready.util

class CPISCoderTools {
	/**
	 * 生成会员编号
	 * @return
	 */
	public static String generateUserNum() {
		
		String timestamp = System.currentTimeMillis() + "";
		timestamp = timestamp.substring(8);

		String timestamp_index = timestamp;
		int random_index = new Random().nextInt(10);
		
		int random_index_1 = new Random().nextInt(10);
		int random_index_2 = new Random().nextInt(10);
		
		//String r_hash = Math.abs(UUID.randomUUID().hashCode()) + "" + System.currentTimeMillis();
		
		String random = "" + random_index_1 + random_index_2 + random_index;
		
		String aim_str = "16" + random + timestamp_index;
		Integer[] mod_array = CoderConstants.getModeArray(16);
		int sum = 0;
		for(int i=0; i<aim_str.length(); i++) {
			int tmp_index = Integer.valueOf(aim_str.charAt(i));
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		return aim_str + sum_x;
	}
	
	/**
	 * 生成会员编号
	 * @return
	 */
	public static String generateUserNum(String gen) {
		
		String timestamp = System.currentTimeMillis() + "";
		
		if(gen!=null&&gen.length()>0) {
			int tmp = 13 - gen.length();
			timestamp = timestamp.substring(tmp) + gen;
		} else {
			timestamp = timestamp.substring(8);
		}

		String timestamp_index = timestamp;
		int random_index = new Random().nextInt(10);
		
		int random_index_1 = new Random().nextInt(10);
		int random_index_2 = new Random().nextInt(10);
		
		//String r_hash = Math.abs(UUID.randomUUID().hashCode()) + "" + System.currentTimeMillis();
		
		String random = "" + random_index_1 + random_index_2 + random_index;
		
		String aim_str = "16" + random + timestamp_index;
		Integer[] mod_array = CoderConstants.getModeArray(16);
		int sum = 0;
		for(int i=0; i<aim_str.length(); i++) {
			int tmp_index = Integer.valueOf(aim_str.charAt(i));
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		return aim_str + sum_x;
	}
	
	/**
	 * 生成优惠券编号
	 * @param mallSernum
	 * @param ordertype
	 * @return
	 */
	public static String generateCouponNum() {
		
		String timestamp = System.currentTimeMillis()/1000 + "";

		String timestamp_index = timestamp;
		int random_index = new Random().nextInt(10);
		
		int random_index_1 = new Random().nextInt(10);
		int random_index_2 = new Random().nextInt(10);
		
		String aim_str = "6" + random_index_1 + random_index_2 + timestamp_index + random_index;
		Integer[] mod_array = CoderConstants.getModeArray(16);
		int sum = 0;
		for(int i=0; i<aim_str.length(); i++) {
			int tmp_index = Integer.valueOf(aim_str.charAt(i));
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		return aim_str + sum_x;
	}

	/**
	 * 生成订单编号
	 * @param mallSernum
	 * @param ordertype
	 * @return
	 */
	public static String generateOrderNum(String mallSernum, String ordertype) {
		String mallSernum_index = null;
		
		Long currentTimeMillis = System.currentTimeMillis();
		
		if(mallSernum!=null&&mallSernum.length()>=3)
			mallSernum_index = mallSernum.substring(mallSernum.length() - 3, mallSernum.length());
		else
			mallSernum_index = currentTimeMillis.toString().substring(currentTimeMillis.toString().length() - 3, currentTimeMillis.toString().length());
		
		String timestamp = currentTimeMillis/1000 + "";

		String timestamp_index = timestamp;
		int random_index = new Random().nextInt(10);
		
		String aim_str = mallSernum_index + timestamp_index + ordertype + random_index;
		Integer[] mod_array = CoderConstants.getModeArray(16);
		int sum = 0;
		
		int aim_len = aim_str.length()>15?15:aim_str.length();
		for(int i=0; i<aim_len; i++) {
			char a = aim_str.charAt(i);
			int inA = a;
			int tmp_index = Integer.valueOf(inA);
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		String order_num = OrderNoGenerator.generateNo() + sum_x;
		if(order_num.startsWith("0")) {
			Random r = new Random();
			Double d = r.nextDouble();
			int i = (int)(d*1000);
			Integer r_i = Integer.valueOf(i);
			String s = r_i.toString().charAt(0) + "";
			if(s.equals("0")) {
				order_num = "8" + order_num;
			}else {
				order_num = s + order_num;
			}
		}
		return order_num;
	}
	
	/**
	 * 生成机构编号
	 * @param type
	 * @param parent_num
	 * @return
	 */
	public static String generateOrgNum(String type, String parent_num) {
		int index = new Random().nextInt(10);
		if(index==0) {
			index = 8;
		}
		String type_index = index + type;
		
		String mid_index = "";
		if(parent_num!=null) {
			mid_index = parent_num.substring(parent_num.length()-2, parent_num.length());
		}else{
			mid_index = (new Random().nextInt(10) + "") + (new Random().nextInt(10) + "");
		}
		
		String timestamp = System.currentTimeMillis()/1000 + "";

		String timestamp_index = timestamp.substring(timestamp.length()-4, timestamp.length());
		
		String aim_str = mid_index + type_index + timestamp_index;
		Integer[] mod_array = CoderConstants.getModeArray(10);
		int sum = 0;
		for(int i=0; i<aim_str.length(); i++) {
			int tmp_index = Integer.valueOf(aim_str.charAt(i));
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		return aim_str + sum_x;
	}
	
	
	public static String serialNum(String code) {
		String mallSernum_index = null;
		String ordertype = "detail";
		Long currentTimeMillis = System.currentTimeMillis();
		
		if(code!=null&&code.length()>=4)
			mallSernum_index = code.substring(code.length() - 4, code.length());
		else
			mallSernum_index = currentTimeMillis.toString().substring(currentTimeMillis.toString().length() - 4, currentTimeMillis.toString().length());
		
		String timestamp = currentTimeMillis/1000 + "";

		String timestamp_index = timestamp;
		int random_index = new Random().nextInt(10);
		
		String aim_str = mallSernum_index + timestamp_index + ordertype + random_index;
		Integer[] mod_array = CoderConstants.getModeArray(16);
		int sum = 0;
		
		int aim_len = aim_str.length()>15?15:aim_str.length();
		for(int i=0; i<aim_len; i++) {
			char a = aim_str.charAt(i);
			int inA = a;
			int tmp_index = Integer.valueOf(inA);
			int tmp_index_mod = mod_array[i];
			sum += tmp_index*tmp_index_mod;
		}
		int sum_yu = sum%11;
		int sum_x = (12-sum_yu)/11;
		String order_num = OrderNoGenerator.generateNo() + sum_x;
		if(order_num.startsWith("0")) {
			Random r = new Random();
			Double d = r.nextDouble();
			int i = (int)(d*1000);
			Integer r_i = Integer.valueOf(i);
			String s = r_i.toString().charAt(0) + "";
			if(s.equals("0")) {
				order_num = "8" + order_num;
			}else {
				order_num = s + order_num;
			}
		}
		return order_num;
	}
	
}
