package com.example.intentfilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void toTestActivity(View view) {
		Intent intent = new Intent();
		intent.setAction("com.test.action");
		startActivity(intent);
	}
	
	public void toTestActivity2(View view) {
		Intent intent = new Intent();
		intent.addCategory("com.test.category");
		intent.addCategory("com.test.category1");
		startActivity(intent);
	}
	
}
