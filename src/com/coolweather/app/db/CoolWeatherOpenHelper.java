package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	// Province建表语句
	public static final String CREATE_PROVINCE = "create table Province("
			+ "id interger primary key auto increment," + "province_name text,"
			+ "province_code text)";
	// City建表语句
	public static final String CREATE_CITY = "create table City("
			+ "id interger primary key auto increment," + "city_name text,"
			+ "city_code text," + "province_id integer)";
	// County建表语句
	public static final String CREATE_County = "create table County("
			+ "id interger primary key auto increment," + "county_name text,"
			+ "county_code text," + "city_id integer)";

	public CoolWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_PROVINCE); //创建Province表
		db.execSQL(CREATE_CITY);	
		db.execSQL(CREATE_County);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
