package com.example.bookproviderclient;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void test(View view) {
		Log.d("测试代码", "测试代码");
		Uri uri = Uri.parse("content://com.example.provider/book");
		ContentValues contentValues = new ContentValues();
		contentValues.put("_id", 16);
		contentValues.put("name", "程序设计艺术");
		getContentResolver().insert(uri, contentValues);

	}
}
