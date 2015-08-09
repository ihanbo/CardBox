package com.hans.mydb.utils;

public class SelectUtils {

	public static String getWhereEquals(String column,String value) {
		return column+" = "+value;
	}
	
	public static String getWhereScope(String column,boolean big,String bigValue,boolean small,String smallValue) {
		if(!big&&!small){
			return null;
		}
		StringBuilder sb = new StringBuilder(column);
		if(big){
			sb.append(" > ");
			sb.append(bigValue);
		}
		if(big&&small){
			sb.append(" and ");
			sb.append(column);
		}
		if(small){
			sb.append(" < ");
			sb.append(smallValue);
		}
		return sb.toString();
	}

}
