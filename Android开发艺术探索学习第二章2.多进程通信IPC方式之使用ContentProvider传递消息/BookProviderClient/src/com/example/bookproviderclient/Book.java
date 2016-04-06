package com.example.bookproviderclient;

public class Book {
	public Integer _id;
	public String name;

	public Book(Integer _id, String name) {
		super();
		this._id = _id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Book [_id=" + _id + ", name=" + name + "]";
	}

}
