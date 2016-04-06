package com.example.bookproviderclient;

public class User {
	public Integer _id;
	public String name;
	public Integer sex;

	public User(Integer _id, String name, Integer sex) {
		super();
		this._id = _id;
		this.name = name;
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", name=" + name + ", sex=" + sex + "]";
	}

}
