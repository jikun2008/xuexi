package com.example.messengerclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	private Messenger mservice;
	private final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = new Intent(this, TestService.class);

		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mservice = new Messenger(service);

		}
	};

	public void sendMsg(View view) {

		Message message = Message.obtain(null, 1);
		Bundle bundle = new Bundle();
		bundle.putString("request", "Hell,我是客户端");
		message.setData(bundle);
		message.replyTo = new Messenger(clientHandler);

		try {
			mservice.send(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(serviceConnection);
		super.onDestroy();
	}

	private Handler clientHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(TAG, TAG + ":" + msg.getData().getString("rely"));
		};

	};

}
