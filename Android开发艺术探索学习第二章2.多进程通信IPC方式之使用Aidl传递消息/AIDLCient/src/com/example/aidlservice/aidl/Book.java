package com.example.aidlservice.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

	private String name;
	private String price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(price);

	}

	public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {

		@Override
		public Book[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Book[size];
		}

		@Override
		public Book createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Book(source);
		}
	};

	private Book(Parcel parcel) {
		name = parcel.readString();
		price = parcel.readString();

	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", price=" + price + "]";
	}

	public Book(String name, String price) {
		super();
		this.name = name;
		this.price = price;
	}

}
