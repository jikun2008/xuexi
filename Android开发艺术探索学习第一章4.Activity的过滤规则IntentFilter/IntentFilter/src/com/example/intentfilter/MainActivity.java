package com.example.intentfilter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

	public void toTestActivity4(View view) {

//		Intent intent = new Intent();
//		intent.setAction("com.test.data");
//		intent.setDataAndType(Uri.parse("http://test"), "image/*");
//		ResolveInfo info = getPackageManager().resolveActivity(intent,
//				PackageManager.MATCH_DEFAULT_ONLY);
//		if (info != null) {
//			startActivity(intent);
//		} else {
//			Toast.makeText(getApplicationContext(), "无匹配", Toast.LENGTH_SHORT)
//					.show();
//		}
		toTestActiviyTest4();

	}

	private void toTestActiviyTest4() {
		Intent intent = new Intent();
		intent.setAction("com.test.data1");
		intent.setDataAndType(Uri.parse("http://test"), "image/*");
		ComponentName name = intent.resolveActivity(getPackageManager());
		if (name != null) {
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "Intent无匹配", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
