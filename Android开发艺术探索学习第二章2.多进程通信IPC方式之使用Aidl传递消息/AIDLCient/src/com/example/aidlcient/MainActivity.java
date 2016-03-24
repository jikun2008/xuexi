package com.example.aidlcient;

import com.example.aidlservice.aidl.Book;
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
	private String TAG = MainActivity.class.getSimpleName();

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
		unbindService(serviceConnection);
		iBookManager = null;
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
			Log.d(TAG, TAG + ":onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.d(TAG, TAG + ":onServiceConnected");
			iBookManager = IBookManager.Stub.asInterface(service);
		}
	};

	public void getListBook(View view) {
		textView.setText("");
		try {
			textView.setText(iBookManager.getBooks().toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, TAG + ":" + e.toString());
		}

	}

	public void addBook(View view) {
		Book book = new Book("数学", "123");
		try {
			iBookManager.addBook(book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, TAG + ":" + e.toString());
		}

	}

}
