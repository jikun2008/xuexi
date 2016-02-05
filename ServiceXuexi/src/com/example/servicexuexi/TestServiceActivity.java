package com.example.servicexuexi;



import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class TestServiceActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		
	}
	
	public void startservice(View view) {
		Intent intent = new Intent(this, TestService.class);
		startService(intent);
	}
	
	public void stopservice(View view) {
		Intent intent = new Intent(this, TestService.class);
		stopService(intent);
	}
	
	public void bindservice(View view) {
		Intent intent = new Intent(this, TestService.class);
		bindService(intent, serviceConnection, Context.BIND_ABOVE_CLIENT);
	}
	
	public void unbindservice(View view) {
		unbindService(serviceConnection);
		
	}
	
	ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
		}
	};
	
}
