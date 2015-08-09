package com.hans.mydb.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import android.content.ContentValues;
import android.database.Cursor;
import com.hans.mydb.in.DBConfig;
import com.hans.mydb.in.MyDBOpenHelper;
import com.hans.mydb.model.CursorModel;
import com.hans.mydb.model.PK;
import com.hans.mydb.model.Property;
import com.hans.mydb.model.SeLectInfo;
import com.hans.mydb.model.TableInfo;

public class Centre {
	
	/** 获取默认主键列名（未设置主键的表） */
	public static String getCommonPKColumn(){
		return TableInfo.DPKCFN;
	}
	
	/** 获取主键信息 */
	public static  PK getPK(Class<?> clazz) {
		TableInfo tb = getTableInfo(clazz);
		PK pk = tb.getId();
		return pk;
	}
	
	/** 初始化 */
	public static boolean init(DBConfig config){
		if(DBConfig.isOk(config)){
			MyDBOpenHelper.setDbConfig(config);
			return true;
		}else{
			return false;
		}
	}

	/** 执行sql语句 */
	public static void dropTable(Class<?> clazz){
		MyDBOpenHelper.dropTable(getTableInfo(clazz).getTableName());
	}
	
	/** 执行sql语句 */
	public static void excuteSql(String sql){
		MyDBOpenHelper.excuteSQL(sql);
	}
	
	/** 执行查询语句 */
	public static Cursor rqwQuery(String sql, String[] selectionArgs){
		return MyDBOpenHelper.query(sql, selectionArgs);
	}
	
	public static long replaceOrPopup(Object entity,boolean replace){
		if (entity == null) {
			return -1;
		}
		TableInfo info = getTableInfo(entity.getClass());
		ContentValues  cv = Centre.getContentValuesByEntity(entity,true);
		long i = MyDBOpenHelper.insertSingle(info.getTableName(), cv, replace);
		return i;
	}
	
	public static void replaceOrPopup(List<?> data,boolean replace){
		if (data == null||data.isEmpty()) {
			return ;
		}
		Object entity = data.get(0);
		TableInfo info = getTableInfo(entity.getClass());
		List<ContentValues> cs = new ArrayList<ContentValues>();
		int size = data.size();
		for (int i = 0; i < size; i++) {
			Object obj = data.get(i);
			ContentValues cv = getContentValuesByEntity(obj,true);
			cs.add(cv);
		}
		MyDBOpenHelper.insertAlot(info.getTableName(), cs, replace);
	}
	
	public static long insertOrReplace(Object entity,boolean replace){
		if (entity == null) {
			return -1;
		}
		TableInfo info = getTableInfo(entity.getClass());
		ContentValues cv = getContentValuesByEntity(entity);
		long i = MyDBOpenHelper.insertSingle(info.getTableName(), cv, replace);
		return i;
	}
	
	/** 保存一个实体 */
	public static long saveSingle(Object entity) {
		if (entity == null) {
			return -1;
		}
		TableInfo info = getTableInfo(entity.getClass());
		boolean isAutoIncrese = info.isAutoIncrement();
		ContentValues cv = Centre.getContentValuesByEntity(entity,!isAutoIncrese);
		long i = MyDBOpenHelper.insertSingle(info.getTableName(), cv, false);
		return i;
	}
	/** 保存实体列表 ，用事务，线程不能打断，不然会锁住数据库*/
	public static void saveaLot(List<?> data) {
		if (data == null||data.isEmpty()) {
			return ;
		}
		Object entity = data.get(0);
		TableInfo info = getTableInfo(entity.getClass());
		boolean isAutoIncrese =  info.isAutoIncrement();
		List<ContentValues> ls = new ArrayList<ContentValues>();
		for (Object object : data) {
			ContentValues cv = Centre.getContentValuesByEntity(object,!isAutoIncrese);
			ls.add(cv);
		}
		MyDBOpenHelper.insertAlot(info.getTableName(), ls, false);
	}

	/** 查询 */
	public static <T> List<T> get(Class<T> clazz, SeLectInfo sinfo) {
		return get(clazz, sinfo, null);
	}
	
	/** 查询 */
	public static <T> List<T> get(Class<T> clazz, SeLectInfo sinfo,String[] columns) {
		if (clazz == null) {
			return null;
		}
		if(sinfo==null){
			sinfo = new SeLectInfo();
		}
		TableInfo info = null;
		try {
			info = TableInfo.get(clazz);
		} catch (Exception e) {
			Centre.onHandException(e);
			return null;
		}
		String[] cs = null;
		if(columns!=null&&columns.length>0){
			cs=columns;
		}else{
			cs = info.getAllColumns();
		}
		List<T> ls = new ArrayList<T>();
		Cursor c = MyDBOpenHelper.queryData(info.getTableName(), cs,
				sinfo.getSelection(), sinfo.getSelectionArgs(), sinfo.getGroupBy(), sinfo.getHaving(), sinfo.getOrderBy());
		while (c.moveToNext()) {
			T t = getEntityByCursor(c, clazz);
			if(t!=null){
				ls.add(t);
			}
		}
		return ls;
	}

	/** 处理异常 */
	public static void onHandException(Throwable e) {

	}
	
	/** 删除语句 */
	public static int delete(Class<?> clazz,SeLectInfo sinfo) {
		if (clazz == null) {
			return -1;
		}
		if(sinfo==null){
			sinfo = new SeLectInfo();
		}
		TableInfo info = null;
		try {
			info = TableInfo.get(clazz);
		} catch (Exception e) {
			Centre.onHandException(e);
			return -1;
		}
		int lines = MyDBOpenHelper.delete(info.getTableName(), sinfo.getSelection(), sinfo.getSelectionArgs());
		return lines;
	}
	/** 根据类创建建表语句 */
	public static String getCreatTableSQL(Class<?> clazz) {
		TableInfo table = TableInfo.get(clazz);
		PK id = table.getId();

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("CREATE TABLE IF NOT EXISTS ");
		strSQL.append(table.getTableName());
		strSQL.append(" ( ");
		
		if (table.isAutoIncrement()) {
			strSQL.append(id.getColumn()).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
		} else {
			strSQL.append(id.getColumn()).append(" TEXT PRIMARY KEY,");
		}

		Collection<Property> propertys = table.propertyMap.values();
		for (Property property : propertys) {
			strSQL.append(property.getColumn());
			Class<?> dataType = property.getDataType();
			if (dataType == int.class || dataType == Integer.class || dataType == long.class
					|| dataType == Long.class) {
				strSQL.append(" INTEGER");
			} else if (dataType == float.class || dataType == Float.class
					|| dataType == double.class || dataType == Double.class) {
				strSQL.append(" REAL");
			} 
//			else if (dataType == boolean.class || dataType == Boolean.class) {
//				strSQL.append(" NUMERIC");
//			} 
			else {
				strSQL.append(" TEXT");
			}
			strSQL.append(",");
		}

		strSQL.deleteCharAt(strSQL.length() - 1);
		strSQL.append(" )");
		return strSQL.toString();
	}
	/** 根据一个对象返回ContentValues */
	public static ContentValues getContentValuesByEntity(Object obj,boolean needPK) {
		if (obj == null) {
			return null;
		}
		TableInfo table = null;
		try {
			table = TableInfo.get(obj.getClass());
		} catch (Exception e) {
			onHandException(e);
			return null;
		}
		ContentValues cv = new ContentValues();
		if(needPK){
			PK id = table.getId();
			boolean isPkFieldExists = id.isHasField();
			if(!isPkFieldExists){
				throw new RuntimeException("获取contentValues需要PK主键的数据，但该主键无对应Field,table:"+table.getTableName()+" 主键："+id.getColumn());
			}
			Object idValue = id.getValue(obj);
			if(idValue==null){
				//主键就不用defaultvalue了吧？
				throw new RuntimeException("ContentValues需要主键的数据但主键值为null,表名:"+table.getTableName()+" 主键："+id.getField().getName()+"  对象："+obj.toString());
			}
			cv.put(id.getColumn(), idValue.toString());
		}
		
		// 添加属性
		Collection<Property> propertys = table.propertyMap.values();
		for (Property property : propertys) {
			String pcolumn = property.getColumn();
			Object value = property.getValue(obj);
			if (value != null) {
				cv.put(pcolumn, value.toString());
			} else {
				cv.put(pcolumn, property.getDefaultValue());
				// if(property.getDefaultValue()!=null &&
				// property.getDefaultValue().trim().length()!=0)
				// cv.put(pcolumn, property.getDefaultValue());
			}
		}
		return cv;
	}

	/** 根据cursor生产对象 */
	public static <T> T getEntityByCursor(Cursor cursor, Class<T> clazz) {
		if (cursor == null || clazz == null) {
			return null;
		}
		TableInfo table = TableInfo.get(clazz);
		int columnCount = cursor.getColumnCount();
		if (columnCount > 0) {
			T entity = null;
			try {
				entity = clazz.newInstance();
			} catch (Exception e1) {
				throw new RuntimeException(clazz + " newInstance Error ," + e1.getMessage());
			}
			for (int i = 0; i < columnCount; i++) {
				try {
					String column = cursor.getColumnName(i);

					Property property = table.propertyMap.get(column);
					if (property != null) {
						property.setValue(entity, cursor.getString(i));
					} else {
						if (table.getId().getColumn().equals(column)
								&& table.getId().isHasField()) {
							table.getId().setValue(entity, cursor.getString(i));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return entity;
		}
		return null;
	}

	/** 获取cursor的所有数据 */
	public static CursorModel getDbModel(Cursor cursor) {
		if (cursor != null && cursor.getColumnCount() > 0) {
			CursorModel model = new CursorModel();
			int columnCount = cursor.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				model.set(cursor.getColumnName(i), cursor.getString(i));
			}
			return model;
		}
		return null;
	}

	/** 将DbModel转为实体 */
	public static <T> T dbModelToEntity(CursorModel dbModel, Class<?> clazz) {
		if (dbModel != null && dbModel.getDataMap() != null) {
			HashMap<String, Object> dataMap = dbModel.getDataMap();
			try {
				@SuppressWarnings("unchecked")
				T entity = (T) clazz.newInstance();
				for (Entry<String, Object> entry : dataMap.entrySet()) {
					String column = entry.getKey();
					TableInfo table = TableInfo.get(clazz);
					Property property = table.propertyMap.get(column);
					if (property != null) {
						property.setValue(entity, entry.getValue() == null ? null : entry
								.getValue().toString());
					} else {
						if (table.getId().getColumn().equals(column)
								&& table.getId().getField() != null) {
							table.getId().setValue(
									entity,
									entry.getValue() == null ? null : entry.getValue()
											.toString());
						}
					}

				}
				return entity;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/** 获取白名单 */
	public static ContentValues getWhiteContentValues(Object obj, Set<String> whiteList) {
		ContentValues cv = getContentValuesByEntity(obj, true);
		ContentValues cv2 = new ContentValues();
		for (String key : whiteList) {  
			cv2.put(key, cv.getAsString(key));
		}  
		return cv2;
	}

	/** 获取黑名单 */
	public static ContentValues getBlackContentValues(Object obj, Set<String> blackList) {
		ContentValues cv = getContentValuesByEntity(obj, true);
		for (String key : blackList) {  
			cv.remove(key);
		}  
		return cv;
	}
	
	/** 更新操作 */
	public static int  update(Class<?> clazz,ContentValues cv,String whereClause,String[] whereArgs){
		TableInfo info = getTableInfo(clazz);
		return MyDBOpenHelper.update(info.getTableName(), cv, whereClause, whereArgs);
	} 
	
	/** 获取表信息 */
	public static TableInfo getTableInfo(Class<?> clazz) {
		TableInfo ct = TableInfo.get(clazz);
		return ct;
	}


	/** 根据是否自增长，添加主键 */
	public static ContentValues getContentValuesByEntity(Object entity) {
		TableInfo info = getTableInfo(entity.getClass());
		boolean isAutoIncrese = info.isAutoIncrement();
		ContentValues cv = Centre.getContentValuesByEntity(entity,!isAutoIncrese);
		return cv;
	}
}
