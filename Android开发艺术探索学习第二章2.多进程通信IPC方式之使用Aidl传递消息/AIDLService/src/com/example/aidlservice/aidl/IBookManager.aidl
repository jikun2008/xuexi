package com.example.aidlservice.aidl;
import com.example.aidlservice.aidl.Book;
import com.example.aidlservice.aidl.IBookArriveListener;
 interface IBookManager {
	 List<Book> getBooks();

	 void addBook(in Book book	);
	 
	 void resigner(IBookArriveListener i);
	 void unresigner(IBookArriveListener i);
}
