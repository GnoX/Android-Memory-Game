package com.gnox.memorygame;

public class ScoreHolder {

	private String name;
	private String time;
	private boolean isNew = false;

	public ScoreHolder(String name, String time) {
		this.name = name;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setText(String string) {
		this.time = string;

	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isNew() {
		return isNew;
	}

}