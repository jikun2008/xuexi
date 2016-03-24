package com.example.aidlservice;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.aidlservice.aidl.Book;
import com.example.aidlservice.aidl.IBookManager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLService extends Service {

	private CopyOnWriteArrayList<Book> copyOnWriteArrayList = new CopyOnWriteArrayList<Book>();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return binder;
	}

	Binder binder = new IBookManager.Stub() {

		@Override
		public void addBook(Book book) throws RemoteException {
			// TODO Auto-generated method stub
			copyOnWriteArrayList.add(book);
		}

		@Override
		public List<Book> getBooks() throws RemoteException {
			// TODO Auto-generated method stub
			return copyOnWriteArrayList;
		}
	};
}
