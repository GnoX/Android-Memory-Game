package com.gnox.memorygame;

public class Model {

	private String name;
	private String time;

	public Model(String name, String time) {
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

}