package com.jeancoder.crm.ready.dto.mc

import com.jeancoder.crm.ready.entity.MemberCardRule

class McCardAvailableDto {
	
	BigInteger id;
	
	String title;
		
	String info;
		
	Integer mcLevel;
		
	boolean outer;
		
	String prefix;
		
	Integer aMcPrefix;
		
	Integer aMchPrefix;
		
	Integer startCn;
		
	Integer endCn;
		
	Integer ctCn;
		
	/********** add field 20161005 **********/
		
	boolean suppJf = true;
		
	String defJfAddRatio;
		
	String defJfConRatio;
	
	List<McHierchHiDto>  hierarchyList;
	public McCardAvailableDto(MemberCardRule e) {
		this.id = e.id;
		this.title = e.title;
		this.info = e.info;
		this.mcLevel = e.mc_level;
		this.outer = get(e.is_outer);
		this.prefix = e.prefix;
		this.aMcPrefix = e.a_mc_prefix;
		this.aMchPrefix = e.a_mch_prefix;
		this.startCn = e.start_cn;
		this.endCn = e.end_cn;
		this.suppJf = get(e.supp_jf);
		this.defJfAddRatio = e.def_jf_add_ratio;
		this.defJfConRatio = e.def_jf_con_ratio;
	}
	
	private boolean get(Integer a) {
		if (0.equals(a)) {
			return false;
		} else {
			return false;
		}
	}
	
}
