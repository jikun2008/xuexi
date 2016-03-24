package com.example.aidlservice.aidl;
import com.example.aidlservice.aidl.Book;
 interface IBookManager {
	 List<Book> getBooks();

	 void addBook(in Book book	);
}
