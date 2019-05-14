package com.jeancoder.crm.ready.dto

 enum SysDicConfig {

	PAYCONFIG("1000", "支付方式", "com.piaodaren.pgw.PgwOp"),
	DELIVERYTYPE("2000", "配送方式", null),
	LOGISTICS("3000", "物流公司", null),
	MEMBERCARD("4000", "会员卡类型", "com.piaodaren.mcbridge.MCBring"),
	GOODS("5000", "商品类型", "com.piaodaren.supp.goods.GoodsOp"),
	PROJTYPE("6000", "项目类型", null);
	
	private String code;
	private String name;
	private String entry;
	
	private SysDicConfig(String code, String name, String entry) {
		this.code = code;
		this.name = name;
		this.entry = entry;
	}

	public String key() {
		return this.code;
	}
	
	public String entry() {
		return entry;
	}

	public SelectModel toSlectModel() {
		SelectModelEntry sm = new SelectModelEntry();
		sm.key(this.code);
		sm.name(this.name);
		sm.entry(this.entry);
		return (SelectModel)sm;
	}
	
	public static List<SelectModel> toSelectList() {
		List<SelectModel> models = new ArrayList<SelectModel>();
		models.add(SysDicConfig.PAYCONFIG.toSlectModel());
		models.add(SysDicConfig.DELIVERYTYPE.toSlectModel());
		models.add(SysDicConfig.LOGISTICS.toSlectModel());
		models.add(SysDicConfig.MEMBERCARD.toSlectModel());
		models.add(SysDicConfig.GOODS.toSlectModel());
		models.add(SysDicConfig.PROJTYPE.toSlectModel());
		return models;
	}
	
	public static SysDicConfig key_to_obj(String type) {
		SysDicConfig ret = null;
		if(type.equals(SysDicConfig.DELIVERYTYPE.key())) {
			ret = SysDicConfig.DELIVERYTYPE;
		} else if(type.equals(SysDicConfig.GOODS.key())) {
			ret = SysDicConfig.GOODS;
		} else if(type.equals(SysDicConfig.LOGISTICS.key())) {
			ret = SysDicConfig.LOGISTICS;
		} else if(type.equals(SysDicConfig.MEMBERCARD.key())) {
			ret = SysDicConfig.MEMBERCARD;
		} else if(type.equals(SysDicConfig.PAYCONFIG.key())) {
			ret = SysDicConfig.PAYCONFIG;
		} else if(type.equals(SysDicConfig.PROJTYPE.key())) {
			ret = SysDicConfig.PROJTYPE;
		}
		return ret;
	}
}