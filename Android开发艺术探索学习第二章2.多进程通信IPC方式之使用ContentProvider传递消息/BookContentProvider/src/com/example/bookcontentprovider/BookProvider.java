package com.example.bookcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class BookProvider extends ContentProvider {

	public static final String authorities = "com.example.provider";
	public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"
			+ authorities + "/book");
	public static final Uri USER_CONTENT_URI = Uri.parse("content://"
			+ authorities + "/user");

	public static final int BOOK_URI_CODE = 0;
	public static final int USER_URI_CODE = 1;

	private static final UriMatcher mUriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		mUriMatcher.addURI(authorities, "book", BOOK_URI_CODE);
		mUriMatcher.addURI(authorities, "user", USER_URI_CODE);
	}

	private SQLiteDatabase database;

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub

		initProvidrData();
		return true;
	}

	private void initProvidrData() {
		DatabaseContext dbContext = new DatabaseContext(getContext(), "dbdemo");
		// myHelper = new MySQLiteHelper(dbContext, "my.db", null, 1);
		database = new DbOpenHelper(dbContext).getWritableDatabase();
		database.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
		database.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
		database.execSQL("insert into book values(3,'android');");
		database.execSQL("insert into book values(4,'ios');");
		database.execSQL("insert into book values(5,'html5');");

		database.execSQL("insert into user values(1,'jake',1);");

		database.execSQL("insert into user values(2,'jasmine',0);");
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Log.d("测试代码", "测试代码query");
		String table = getTablename(uri);
		if (table == null) {
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		return database.query(table, projection, selection, selectionArgs,
				null, null, sortOrder);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		String table = getTablename(uri);
		if (table == null) {
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		database.insert(table, null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		String table = getTablename(uri);
		if (table == null) {
			throw new IllegalArgumentException("unsupported uri:" + uri);
		}
		int count = database.delete(table, selection, selectionArgs);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		String table = getTablename(uri);
		if (table == null) {
			throw new IllegalArgumentException("unsupported uri:" + uri);

		}
		int row = database.update(table, values, selection, selectionArgs);
		if (row > 0) {
			getContext().getContentResolver().notifyChange(uri, null);

		}
		return row;
	}

	private String getTablename(Uri uri) {
		String tablename = null;
		switch (mUriMatcher.match(uri)) {
		case BOOK_URI_CODE:
			tablename = "book";
			break;
		case USER_URI_CODE:
			tablename = "user";
			break;

		default:
			break;
		}

		return tablename;
	}

}
