package com.hans.mydb.in;


import android.app.Application;
public class DBConfig {
	public Application context;
	public  String[] tables;
	public int version;
	public String dbName = "hans_db";
	public DBConfig() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DBConfig(Application context, String[] tables, int version,
			String dbName) {
		super();
		this.context = context;
		this.tables = tables;
		this.version = version;
		this.dbName = dbName;
	}



	public static boolean isOk(DBConfig dc){
		if(dc==null){
			return false;
		}
		if(dc.context==null){
			return false;
		}
		return true;
	}

	
}
