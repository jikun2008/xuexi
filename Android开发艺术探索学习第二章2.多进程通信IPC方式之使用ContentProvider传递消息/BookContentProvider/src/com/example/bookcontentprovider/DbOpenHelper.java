package com.example.bookcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "book_provider.db";
	private final static int DB_VERSION = 1;

	public final static String BOOK_TABLE_NAME = "book";

	public final static String USER_TABLE_NAME = "user";

	private final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ BOOK_TABLE_NAME + "(_id INTEGER PRIMART KEY," + "name TEXT)";

	private final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ USER_TABLE_NAME + "(_id INTEGER PRIMART KEY," + "name TEXT,"
			+ "sex INT)";

	public DbOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_BOOK_TABLE);
		db.execSQL(CREATE_USER_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
