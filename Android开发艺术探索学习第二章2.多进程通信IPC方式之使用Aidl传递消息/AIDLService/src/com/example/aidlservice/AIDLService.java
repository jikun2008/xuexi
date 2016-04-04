package com.example.aidlservice;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.aidlservice.aidl.Book;
import com.example.aidlservice.aidl.IBookArriveListener;
import com.example.aidlservice.aidl.IBookManager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class AIDLService extends Service {

	private CopyOnWriteArrayList<Book> copyOnWriteArrayList = new CopyOnWriteArrayList<Book>();

	private RemoteCallbackList<IBookArriveListener> remoteCallbackList = new RemoteCallbackList<IBookArriveListener>();

	private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return binder;
	}

	public void onCreate() {
		atomicBoolean.set(true);
		new Thread(new WorkRunnalbe()).start();
	};

	public void onDestroy() {
		atomicBoolean.set(false);
	};

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

		@Override
		public void resigner(IBookArriveListener i) throws RemoteException {
			// TODO Auto-generated method stub
			remoteCallbackList.register(i);
			int size = remoteCallbackList.beginBroadcast();
			Log.d(Book.TAG, Book.TAG+":+unresigner------" + "size=" + size);

			remoteCallbackList.finishBroadcast();

		}

		@Override
		public void unresigner(IBookArriveListener i) throws RemoteException {
			// TODO Auto-generated method stub

			remoteCallbackList.unregister(i);
			int size = remoteCallbackList.beginBroadcast();
			Log.d(Book.TAG, Book.TAG+":+unresigner------" + "size=" + size);

			remoteCallbackList.finishBroadcast();
		}
	};

	private class WorkRunnalbe implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (atomicBoolean.get()) {

				try {
					Thread.sleep(500);

					int size = remoteCallbackList.beginBroadcast();

					for (int i = 0; i < size; i++) {

						Book book = new Book("测试代码", "价格"
								+ copyOnWriteArrayList.size());
						copyOnWriteArrayList.add(book);
						remoteCallbackList.getBroadcastItem(i)
								.onNewBookArrived(book);
					}

					remoteCallbackList.finishBroadcast();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}
