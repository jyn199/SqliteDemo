package com.jyn.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DemoSqliteHelper extends SQLiteOpenHelper{
	private static final String TAG = "DemoSqliteHelper"; 
	
	public DemoSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table demo(id int primary key,name varchar(20))";
		Log.i(TAG, "create Database------------->");
		db.execSQL(sql);
		
		final int FIRST_DATABASE_VERSION = 1;
		
		System.out.println(db.getVersion());
		if(db.getVersion() != FIRST_DATABASE_VERSION){
			onUpgrade(db, FIRST_DATABASE_VERSION, db.getVersion());
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "update Database------------->");
		for(int i=oldVersion; i < newVersion; i++){
			switch(i){
				case 1:
					upgradeToVersion2(db);
					break;
				case 2:
					upgradeToVersion3(db);
					break;
				default:
					break;
			}
		}
	}

	private void upgradeToVersion3(SQLiteDatabase db) {
		String sql = "create table demo2(id int primary key, name varchar(20))";
		db.execSQL(sql);
	}

	private void upgradeToVersion2(SQLiteDatabase db) {
		String sql = "create table demo1(id int primary key, name varchar(20))";
		db.execSQL(sql);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "downgrade Database------------->");
		for(int i=oldVersion; i > newVersion; i--){
			switch(i){
				case 2:
					downgradeToVersion1(db);
					break;
				case 3:
					downgradeToVersion2(db);
					break;
				default:
					break;
			}
		}
	}

	private void downgradeToVersion2(SQLiteDatabase db) {
		String sql = "drop table demo2";
		db.execSQL(sql);
	}

	private void downgradeToVersion1(SQLiteDatabase db) {
		String sql = "drop table demo1";
		db.execSQL(sql);
	}
	
}
