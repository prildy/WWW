package com.hackaton.activityadvisor.model;

public class Acts {
	private String title;
	private String desc;
	private String user;
	public Acts(String title, String desc, String user) {
		super();
		this.title = title;
		this.desc = desc;
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
