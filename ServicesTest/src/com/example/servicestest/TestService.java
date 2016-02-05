package com.example.servicestest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
	private final String TAG = "TestService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "²âÊÔ´úÂë" + TAG + "." + "onBind");
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "²âÊÔ´úÂë" + TAG + "." + "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG, "²âÊÔ´úÂë" + TAG + "." + "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG, "²âÊÔ´úÂë" + TAG + "." + "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "²âÊÔ´úÂë" + TAG + "." + "onDestroy");
		super.onDestroy();
	}

}
