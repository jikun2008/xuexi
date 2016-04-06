package com.example.bookproviderclient;

import org.w3c.dom.UserDataHandler;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
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
		Log.d("测试代码", "测试代码插入数据");
		Uri uri = Uri.parse("content://com.example.provider/book");
		ContentValues contentValues = new ContentValues();
		contentValues.put("_id", 16);
		contentValues.put("name", "我的代码");
		getContentResolver().insert(uri, contentValues);

	}

	public void query(View view) {
		Log.d("测试代码", "测试代码--query");
		Uri uri = Uri.parse("content://com.example.provider/book");
		ContentValues contentValues = new ContentValues();
		Cursor cursor = getContentResolver().query(uri,
				new String[] { "_id", "name" }, null, null, null);
		while (cursor.moveToNext()) {
			Book book = new Book(cursor.getInt(0), cursor.getString(1));
			Log.d("测试代码", "测试代码--query" + book.toString());
		}

	}
}
