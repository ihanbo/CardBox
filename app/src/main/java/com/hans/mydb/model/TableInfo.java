package com.hans.mydb.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import com.hans.mydb.annotation.APK;
import com.hans.mydb.annotation.H_NotShoot;
import com.hans.mydb.annotation.H_TABLE;

public class TableInfo {

	public static final String DPKCFN = "_id";

	/** 类名 */
	private String className;
	/** 表名 */
	private String tableName;
	/** 主键 */
	private PK id;
	/** 关系表 Key为列名 */
	public final HashMap<String, Property> propertyMap = new HashMap<String, Property>();
	
	/** 是否自增长 */
	private boolean isAutoIncrement = true;

	/** 获取TableInfo 通过类 */
	public static TableInfo get(Class<?> clazz) {
		if (clazz == null || clazz.getAnnotation(H_TABLE.class) == null) {
			throw new RuntimeException(
					"TableInfo get error,clazz is null or not set [HTABLE] ");
		}
		TableInfo tableInfo = tableInfoMap.get(clazz.getName());
		if (tableInfo == null) {
			tableInfo = new TableInfo();

			tableInfo.setTableName(ClassUtils.getTableName(clazz));
			tableInfo.setClassName(clazz.getName());
			// 主键
			Field pkField = FieldUtils.getPrimaryKeyField(clazz);

			PK id = new PK();

			if (pkField != null) {
				id.setSet(FieldUtils.getFieldSetMethod(clazz, pkField));
				id.setGet(FieldUtils.getFieldGetMethod(clazz, pkField));
				id.setDataType(pkField.getType());
				id.setField(pkField);
				id.setColumn(pkField.getName());
				
				APK an = pkField.getAnnotation(APK.class);
				if (an != null) {
					tableInfo.isAutoIncrement = false;
				}
			} else {
				id.setColumn(DPKCFN);
			}
		
			tableInfo.setId(id);

			// 设置其他属性列表
			List<Property> pList = ClassUtils.getPropertyList(clazz);
			if (pList != null) {
				for (Property p : pList) {
					if (p != null)
						tableInfo.propertyMap.put(p.getColumn(), p);
				}
			}
			tableInfoMap.put(clazz.getName(), tableInfo);
		}
		return tableInfo;
	}

	/** 获取TableInfo 通过类名（class.getName()） */
	public static TableInfo get(String className) throws DbException {
		try {
			return get(Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	/** 判断是否是自增长  */
	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}
	
	
	/** 获取所有列名 */
	public String[] getAllColumns() {
		List<String> data = new ArrayList<String>();
		data.add(id.getColumn());
		Iterator<Entry<String, Property>> iter = propertyMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Property> entry = iter.next();
			String key = entry.getKey();
			data.add(key);
		}
		String[] ss = new String[data.size()];
		return data.toArray(ss);

	}

	/** static集合 key为类名 */
	private static final HashMap<String, TableInfo> tableInfoMap = new HashMap<String, TableInfo>();
	
	

	/**
	 * @author hanbo1 
	 * 变量工具类
	 */
	private static class FieldUtils {
		
		/**  */
		public static String getColumnByField(Field field){
			return field.getName();
		}
		
		/** 是否是基本类型 */
		public static boolean isBaseDateType(Field field) {
			Class<?> clazz = field.getType();
			return clazz.equals(String.class) || clazz.equals(Integer.class)
					|| clazz.equals(Byte.class) || clazz.equals(Long.class)
					|| clazz.equals(Double.class) || clazz.equals(Float.class)
					|| clazz.equals(Character.class) || clazz.equals(Short.class)
					|| clazz.equals(Boolean.class) || clazz.equals(java.util.Date.class)
					|| clazz.equals(java.sql.Date.class) || clazz.isPrimitive();
		}
		
		/** 判断是否是静态变量 */
		private static boolean isStatic(Field f){
			if(f==null){
				return true;
			}
			int i = f.getModifiers();
			boolean b = Modifier.isStatic(i);
			return b;
		}

		/** 根据Field名获取Field 56 */
		@SuppressWarnings("unused")
		private static Field getFieldByName(Class<?> clazz, String fieldName) {
			Field field = null;
			if (fieldName != null) {
				try {
					field = clazz.getDeclaredField(fieldName);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
			return field;
		}

		/** 获取Field的get方法56 */
		private static Method getFieldGetMethod(Class<?> clazz, Field f) {
			String fn = f.getName();
			Method m = null;
			if (f.getType() == boolean.class) {
				m = getBooleanFieldGetMethod(clazz, fn);
			}
			if (m == null) {
				m = getFieldGetMethod(clazz, fn);
			}
			return m;
		}

		/** 获取boolean Field的get方法56 */
		private static Method getBooleanFieldGetMethod(Class<?> clazz, String fieldName) {
			String mn = "is" + fieldName.substring(0, 1).toUpperCase(Locale.getDefault())
					+ fieldName.substring(1);
			if (isISStart(fieldName)) {
				mn = fieldName;
			}
			try {
				return clazz.getDeclaredMethod(mn);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				return null;
			}
		}

		/** 获取Field的get方法56 */
		private static Method getFieldGetMethod(Class<?> clazz, String fieldName) {
			String mn = "get" + fieldName.substring(0, 1).toUpperCase(Locale.getDefault())
					+ fieldName.substring(1);
			try {
				return clazz.getDeclaredMethod(mn);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				return null;
			}
		}

		/** 获取某Field的set方法56 */
		private static Method getFieldSetMethod(Class<?> clazz, Field f) {
			String fn = f.getName();
			String mn = "set" + fn.substring(0, 1).toUpperCase(Locale.getDefault())
					+ fn.substring(1);
			try {
				return clazz.getDeclaredMethod(mn, f.getType());
			} catch (NoSuchMethodException e) {
				if (f.getType() == boolean.class) {
					return getBooleanFieldSetMethod(clazz, f);
				}
			}
			return null;
		}

		/** 获取boolean Field的set方法56 */
		private static Method getBooleanFieldSetMethod(Class<?> clazz, Field f) {
			String fn = f.getName();
			String mn = "set" + fn.substring(0, 1).toUpperCase(Locale.getDefault())
					+ fn.substring(1);
			if (isISStart(f.getName())) {
				mn = "set" + fn.substring(2, 3).toUpperCase(Locale.getDefault())
						+ fn.substring(3);
			}
			try {
				return clazz.getDeclaredMethod(mn, f.getType());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				return null;
			}
		}

		/** 判断变量名是否以is开头56 */
		private static boolean isISStart(String fieldName) {
			if (fieldName == null || fieldName.trim().length() == 0)
				return false;
			// is开头，并且is之后第一个字母是大写 比如 isAdmin
			return fieldName.startsWith("is") && !Character.isLowerCase(fieldName.charAt(2));
		}

		/** 根据类，获取主键Field,没有注解的话返回null 58 */
		private static Field getPrimaryKeyField(Class<?> clazz) {
			Field primaryKeyField = null;
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null) {
				for (Field field : fields) { // 获取ID注解
					if (field.getAnnotation(APK.class) != null) {
						primaryKeyField = field;
						break;
					}
				}
				if (primaryKeyField == null) { // 如果没有_id的字段
					for (Field field : fields) {
						if (DPKCFN.equals(field.getName())) {
							primaryKeyField = field;
							break;
						}
					}
				}
			} else {
				throw new RuntimeException("这个类 [" + clazz + "] 没有找到变量");
			}
			return primaryKeyField;
		}

		/** 获取某Field在数据库表里的默认值 */
		private static String getPropertyDefaultValue(Field field) {
			return null;
		}

		// /** 检测 字段是否已经被标注为 非数据库字段 */
		// private static boolean isUnderShoot(Field f) {
		// return f.getAnnotation(UnderShoot.class) != null;
		// }
		//
		// /** 判断变量是否是基础类型56 */
		// private static boolean isBaseDateType(Field field) {
		// Class<?> clazz = field.getType();
		// return clazz.equals(String.class) || clazz.equals(Integer.class)
		// || clazz.equals(Byte.class) || clazz.equals(Long.class)
		// || clazz.equals(Double.class) || clazz.equals(Float.class)
		// || clazz.equals(Character.class) || clazz.equals(Short.class)
		// || clazz.equals(Boolean.class) || clazz.equals(Date.class)
		// || clazz.equals(java.util.Date.class) ||
		// clazz.equals(java.sql.Date.class)
		// || clazz.isPrimitive();
		// }

	}

	private static class ClassUtils {

		/** 根据实体类 获得 实体类对应的表名 */
		private static String getTableName(Class<?> clazz) {
			H_TABLE table = clazz.getAnnotation(H_TABLE.class);
			if (table == null || table.name().trim().length() == 0) {
				// 当没有注解的时候默认用类的名称作为表名,并把点（.）替换为下划线(_)
				return clazz.getName().replace('.', '_');
			}
			return table.name();
		}

		/** 获取除主键外的其他变量的关系表56 */
		private static List<Property> getPropertyList(Class<?> clazz) {
			List<Property> plist = new ArrayList<Property>();
			Field[] fs = clazz.getDeclaredFields();
			for (Field f : fs) {
				if (f.getAnnotation(H_NotShoot.class) != null) {
					continue;
				}
				if(FieldUtils.isStatic(f)){
					continue;
				}
				if (!FieldUtils.isBaseDateType(f)) {
					continue;
				}
				if (f.getAnnotation(APK.class) != null || DPKCFN.equals(f.getName())) {
					// 过滤主键
					continue;
				}

				Property property = new Property();
				property.setColumn(FieldUtils.getColumnByField(f));
				property.setDataType(f.getType());
				property.setDefaultValue(FieldUtils.getPropertyDefaultValue(f));
				property.setSet(FieldUtils.getFieldSetMethod(clazz, f));
				property.setGet(FieldUtils.getFieldGetMethod(clazz, f));
				property.setField(f);
				plist.add(property);
			}
			return plist;
		}
	}
}
