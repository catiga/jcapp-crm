package com.jeancoder.crm.ready.dto


class CatalogViewDto {
	
	String data;
	
	CatalogViewAttr attr;

	String text;
	
	String id;
	
	String parent;
	
	String state = "opened";
	
	boolean children = true;
	
	CatalogViewDto(CatalogDto e) {
		this.text = e.cat_name_cn;
		this.id = e.id.toString();
		this.parent = e.parent_id.toString();
		if(e.parent_id==0||e.parent_id==null) {
			this.parent = "#";
		}
		
		this.attr = new CatalogViewAttr();
		this.attr.id = e.id.toString();
		this.data = e.cat_name_en;
	}
}
