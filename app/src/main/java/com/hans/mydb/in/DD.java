package com.hans.mydb.in;

import java.util.List;
import java.util.Set;
import android.content.ContentValues;
import android.database.Cursor;
import com.hans.mydb.in.DBConfig;
import com.hans.mydb.model.CursorModel;
import com.hans.mydb.model.PK;
import com.hans.mydb.model.SeLectInfo;
import com.hans.mydb.model.TableInfo;
import com.hans.mydb.utils.Centre;

public class DD {
	
	/** 获取默认主键列名（未设置主键的表） */
	public static String getCommonPKColumn(){
		return TableInfo.DPKCFN;
	}
	
	/** 获取主键信息 */
	public static  PK getPK(Class<?> clazz) {
		return Centre.getPK(clazz);
	}
	
	/** 初始化 */
	public static boolean init(DBConfig config){
		return Centre.init(config);
	}

	public static void dropTable(Class<?> clazz){
		 Centre.dropTable(clazz);
	}
	/** 执行sql语句 */
	public static void excuteSql(String sql){
		Centre.excuteSql(sql);
	}
	
	/** 执行查询语句 */
	public static Cursor rqwQuery(String sql, String[] selectionArgs){
		return Centre.rqwQuery(sql, selectionArgs);
	}

	public static long replace(Object entity){
		return 	Centre.replace(entity);
	}

	public static void replace(List<?> data){
		Centre.replace(data);
	}
	
	public static void replaceOrPopup(List<?> data,boolean replace){
		Centre.replaceOrPopup(data, replace);
	}
	public static long replaceOrPopup(Object entity,boolean replace){
		return Centre.replaceOrPopup(entity, replace);
	}
	
	/** 保存一个实体 */
	public static long saveSingle(Object entity) {
		return Centre.saveSingle(entity);
	}
	/** 保存实体列表 ，用事务，线程不能打断，不然会锁住数据库*/
	public static void saveaLot(List<?> data) {
		Centre.saveaLot(data);
	}

	/** 查询 */
	public static <T> List<T> get(Class<T> clazz, SeLectInfo sinfo) {
		return Centre.get(clazz, sinfo);
	}

	/** 处理异常 */
	public static void onHandException(Throwable e) {
		Centre.onHandException(e);
	}
	
	/** 删除语句 */
	public static int delete(Class<?> clazz,SeLectInfo sinfo) {
		return Centre.delete(clazz, sinfo);
	}
	/** 根据类创建建表语句 */
	public static String getCreatTableSQL(Class<?> clazz) {
		return Centre.getCreatTableSQL(clazz);
	}
	/** 根据一个对象返回ContentValues */
	public static ContentValues getContentValuesByEntity(Object obj,boolean needPK) {
		return Centre.getContentValuesByEntity(obj, needPK);
	}

	/** 根据cursor生产对象 */
	public static <T> T getEntityByCursor(Cursor cursor, Class<T> clazz) {
		return Centre.getEntityByCursor(cursor, clazz);
	}

	/** 获取cursor的所有数据 */
	public static CursorModel getDbModel(Cursor cursor) {
		return Centre.getDbModel(cursor);
	}

	/** 将DbModel转为实体 */
	public static <T> T dbModelToEntity(CursorModel dbModel, Class<?> clazz) {
		return Centre.dbModelToEntity(dbModel, clazz);
	}

	/** 获取白名单 */
	public static ContentValues getWhiteContentValues(Object obj, Set<String> whiteList) {
		return Centre.getWhiteContentValues(obj, whiteList);
	}

	/** 获取黑名单 */
	public static ContentValues getBlackContentValues(Object obj, Set<String> blackList) {
		return Centre.getBlackContentValues(obj, blackList);
	}
	
	/** 更新操作 */
	public static int  update(Class<?> clazz,ContentValues cv,String whereClause,String[] whereArgs){
		return Centre.update(clazz, cv, whereClause, whereArgs);
	} 
	
	/** 获取表信息 */
	public static TableInfo getTableInfo(Class<?> clazz) {
		return Centre.getTableInfo(clazz);
	}
	/** 只根据是够自增长 来判断是否放主键，基本无用  */
	public static ContentValues getContentValuesByEntity(Object entity) {
		return Centre.getContentValuesByEntity(entity);
	}
}
