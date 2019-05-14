package com.jeancoder.crm.ready.mcbridge

public  enum MCEnum {

	SSC_DX("10010", "鼎新会员卡"),
	SSC_1905("10011", "1905会员卡"),
	SSC_MTX("10012", "满天星会员卡"),
	SSC_ERDOS_1436("10013", "1436会员卡"),
	SSC_ORISTAR("10014", "晨星会员卡"),
	SSC_YUEKE("10015", "凤凰佳影会员卡");
	
	private String code;
	private String name;
	
	private MCEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String key() {
		return this.code;
	}

	public SelectModel toSlectModel() {
		SelectModel sm = new SelectModel();
		sm.setKey(code);
		sm.setName(name);
		return sm;
	}

	public static MCEnum key_to_obj(String key) {
		if(key.equals(SSC_DX.key())) {
			return SSC_DX;
		} else if(key.equals(SSC_1905.key())) {
			return SSC_1905;
		} else if(key.equals(SSC_MTX.key())) {
			return SSC_MTX;
		}else if(key.equals(SSC_ORISTAR.key())) {
			return SSC_ORISTAR;
		}else if(key.equals(SSC_YUEKE.key())) {
			return SSC_YUEKE;
		}
		
		return null;
	}
}
