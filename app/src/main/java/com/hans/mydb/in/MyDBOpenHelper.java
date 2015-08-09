package com.hans.mydb.in;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.hans.mydb.utils.Centre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {
	
	
	private static MyDBOpenHelper instance;
	/** 防止在 onCreate中调用方法出问题*/
	private SQLiteDatabase db;
	private static DBConfig dbConfig;

	public static DBConfig getDbConfig() {
		return dbConfig;
	}

	public static void setDbConfig(DBConfig dbConfig) {
		MyDBOpenHelper.dbConfig = dbConfig;
	}

	public static synchronized MyDBOpenHelper getInstance() {
		if (instance == null) {
			if(!DBConfig.isOk(dbConfig)){
				throw new RuntimeException("请先调用init方法初始化");
			}
			instance = new MyDBOpenHelper(dbConfig.context);
		}
		return instance;
	}

	private MyDBOpenHelper(Context context) {
		super(context, dbConfig.dbName, null, dbConfig.version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		if(dbConfig.tables==null||dbConfig.tables.length == 0){
			return;
		}
		for (String tableName : dbConfig.tables) {
			try {
				Class<?> t = Class.forName(tableName);
				Method monCreate = t.getDeclaredMethod("onCreate", SQLiteDatabase.class);
				monCreate.invoke(null, db);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.db = db;
		if(dbConfig.tables==null||dbConfig.tables.length==0){
			return;
		}
		for (String tableName : dbConfig.tables) {
			try {
				Class<?> t = Class.forName(tableName);
				Method monUpgrade = t.getDeclaredMethod("onUpgrade", SQLiteDatabase.class,int.class,int.class);
				monUpgrade.invoke(null, db,oldVersion,newVersion);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/** 功能:执行sql语句 */
	public static void excuteSQL(String sql) {
		if (sql == null) {
			return;
		}
		getDB().execSQL(sql);
	}

	public SQLiteDatabase getDatabase() {
		if (this.db == null) {
			this.db = getWritableDatabase();
		}
		return this.db;
	}

	/** 获取数据库 */
	public static SQLiteDatabase getDB() {
		return getInstance().getDatabase();
	}

	/** 功能:判断表是否存在 */
	public static  boolean isTableExist(String tableName) {
		if (tableName == null) {
			return false;
		}
		String sql = "select count(*) from sqlite_master where type='table' and name=?";
		Cursor cursor = null;
		try {
			cursor = getDB().rawQuery(sql, new String[] { tableName });
			if (cursor.moveToFirst()) {
				int i = cursor.getInt(0);
				if (i == 1) {
					return true;
				} else {
					return false;
				}
			}
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception e) {
			if (cursor != null) {
				cursor.close();
			}
			handleSQLException(e);
		}
		return false;
	}

	/** 功能: 删除表 */
	public static void dropTable(String tableName) {
		getDB().execSQL("DROP TABLE IF EXISTS " + tableName);
	}

	/** 功能:创建唯一性索引 */
	public static boolean createUniqueIndex(String uniqueName, String tableName,
			String[] columns) {
		if (uniqueName == null || tableName == null || columns == null
				|| columns.length <= 0) {
			return false;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			sb.append(columns[i]);
			sb.append(",");
		}
		String columnString = sb.substring(0, sb.length() - 1);
		boolean success = true;
		try {
			getDB().execSQL(
					"CREATE UNIQUE INDEX " + uniqueName + " ON " + tableName
							+ " (" + columnString + ")");
		} catch (Exception e) {
			handleSQLException(e);
			success = false;
		}
		return success;
	}

	/** 功能:查询某表数据条数 */
	public int getCount(String tableName, String where) {
		SQLiteDatabase db = getDB();
		int result = 0;
		Cursor cursor = null;
		try {
			String sql = "select count(*) from " + tableName;
			if (where != null) {
				sql = sql + " " + where;
			}
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			result = cursor.getInt(0);
			cursor.close();
		} catch (Exception e) {
			if (cursor != null) {
				cursor.close();
			}
			handleSQLException(e);
		}
		return result;
	}

	/** 插入单条记录 */
	public static long insertSingle(String tableName, ContentValues cv,
			boolean replace) {
		if (tableName == null || cv == null) {
			return -1;
		}
		SQLiteDatabase writableDatabase = getDB();
		try {
			long lid = -1;
			if (replace) {
				lid = writableDatabase.replaceOrThrow(tableName, null, cv);
			} else {
				lid = writableDatabase.insertOrThrow(tableName, null, cv);
			}
			return lid;
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return -1;
	}

	/** 插入多条,线程不能打断，否则容易锁住数据库 */
	public static void insertAlot(String tableName, List<ContentValues> data,
			boolean replace) {
		if (data == null || data.isEmpty()) {
			return;
		}
		SQLiteDatabase writableDatabase = getDB();
		writableDatabase.beginTransaction();
		for (ContentValues cv : data) {
			try {
				if (replace) {
					writableDatabase.replaceOrThrow(tableName, null, cv);
				} else {
					writableDatabase.insertOrThrow(tableName, null, cv);
				}
			} catch (SQLException e) {
				handleSQLException(e);
			}

		}
		writableDatabase.setTransactionSuccessful();
		writableDatabase.endTransaction();
	}

	/** 查询数据，记得关闭Cursor */
	public static Cursor queryData(String tableName, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		SQLiteDatabase writableDatabase = getDB();
		Cursor cursor = writableDatabase.query(tableName, columns, selection,
				selectionArgs, groupBy, having, orderBy);
		return cursor;
	}

	/** 更新数据 */
	public static int update(String tableName, ContentValues cv,
			String whereClause, String[] whereArgs) {
		if (tableName == null || cv == null) {
			return -1;
		}
		SQLiteDatabase writableDatabase = getDB();
		int lines = writableDatabase.update(tableName, cv, whereClause,
				whereArgs);
		return lines;
	}

	/** 删除数据,返回影响的行数 */
	public static int delete(String tableName, String whereClause,
			String[] whereArgs) {
		if (tableName == null) {
			return -1;
		}
		SQLiteDatabase writableDatabase = getDB();
		int rowNums = writableDatabase
				.delete(tableName, whereClause, whereArgs);
		return rowNums;
	}

	public static Cursor query(String sql, String[] selectionArgs) {
		SQLiteDatabase writableDatabase = getDB();
		Cursor cursor = writableDatabase.rawQuery(sql, selectionArgs);
		return cursor;

	}

	/** 数据库异常处理 */
	public static void handleSQLException(Throwable e) {
		Centre.onHandException(e);
	}

}
