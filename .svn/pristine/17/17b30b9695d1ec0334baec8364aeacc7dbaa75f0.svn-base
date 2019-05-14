package com.jeancoder.crm.ready.constant

import java.io.Serializable

public final class SimpleAjax implements Serializable{
	
	private boolean available;
	
	private String[] messages;
	
	private Object data;
	
	public SimpleAjax() {}
	
	private SimpleAjax(boolean result, String msgs, Object data) {
		this.available = result;
		this.messages = msgs.split(',');
		this.data = data;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static SimpleAjax available() {
		return new SimpleAjax(true, '', null);
	}
	
	public static SimpleAjax available(String messages) {
		return new SimpleAjax(true, messages, null);
	}
	
	public static SimpleAjax available(String messages, Object data) {
		return new SimpleAjax(true, messages, data);
	}
	
	public static SimpleAjax notAvailable() {
		return new SimpleAjax(false, '', null);
	}
	
	public static SimpleAjax notAvailable(String messages) {
		return new SimpleAjax(false, messages, null);
	}
	
	public static SimpleAjax notAvailable(String messages, Object data) {
		return new SimpleAjax(false, messages, data);
	}
	
}