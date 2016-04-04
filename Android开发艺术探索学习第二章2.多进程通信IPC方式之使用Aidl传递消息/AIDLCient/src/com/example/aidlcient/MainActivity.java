package com.example.aidlcient;

import com.example.aidlservice.aidl.Book;
import com.example.aidlservice.aidl.IBookArriveListener;
import com.example.aidlservice.aidl.IBookManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private IBookManager iBookManager;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		bindService();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		try {
			
			if (iBookManager != null && iBookManager.asBinder().isBinderAlive()) {

				iBookManager.unresigner(arriveListener);
				Log.d(Book.TAG, Book.TAG
						+ ":iBookManager.unresigner(arriveListener)");

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unbindService(serviceConnection);

		super.onDestroy();
	}

	private void bindService() {
		Intent intent = new Intent();
		intent.setPackage("com.example.aidlservice");
		intent.setAction("com.test.aidl");
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.d(Book.TAG, Book.TAG + ":onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub

			try {
				Log.d(Book.TAG, Book.TAG + ":onServiceConnected");
				iBookManager = IBookManager.Stub.asInterface(service);
				iBookManager.resigner(arriveListener);
				Log.d(Book.TAG, Book.TAG
						+ ":iBookManager.resigner(arriveListener)");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	};

	public void getListBook(View view) {
		textView.setText("");
		try {
			textView.setText(iBookManager.getBooks().toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public void addBook(View view) {
		Book book = new Book("数学", "123");
		try {
			iBookManager.addBook(book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	IBookArriveListener arriveListener = new IBookArriveListener.Stub() {

		@Override
		public void onNewBookArrived(Book book) throws RemoteException {
			// TODO Auto-generated method stub
			Log.d(Book.TAG, Book.TAG + "测试代码：" + book.toString());
		}
	};

}
