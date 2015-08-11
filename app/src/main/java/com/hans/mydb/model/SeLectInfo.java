package com.hans.mydb.model;

import com.hans.mydb.in.DD;

import android.text.TextUtils;


public class SeLectInfo {

	private String selection;
	private String[] selectionArgs;
	private String groupBy;
	private String having;
	private String orderBy;


	public static SeLectInfo create(){
		return new SeLectInfo();
	}

	public SeLectInfo selection(String selection) {
		this.selection = selection;
		return this;
	}

	public SeLectInfo selectionArgs(String[] selectionArgs) {
		this.selectionArgs = selectionArgs;
		return this;
	}

	public SeLectInfo groupBy(String groupBy) {
		this.groupBy = groupBy;
		return this;
	}

	public SeLectInfo having(String having) {
		this.having = having;
		return this;
	}

	public SeLectInfo orderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	
	
	public String getSelection() {
		return selection;
	}

	public String[] getSelectionArgs() {
		return selectionArgs;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public String getHaving() {
		return having;
	}

	public String getOrderBy() {
		return orderBy;
	}
	
	/** 采用默认主键，主键== */
	public static SeLectInfo _CommonPkValueIs(String pkValue){
		if(TextUtils.isEmpty(pkValue)){
			return null;
		}
		SeLectInfo s = new SeLectInfo().selection(DD.getCommonPKColumn()+" = ?").selectionArgs(new String[]{pkValue});
		return s;
	}
	/** 主键== */
	public static SeLectInfo _PkValueIs(Class<?> clazz,String pkValue){
		if(clazz==null||TextUtils.isEmpty(pkValue)){
			return null;
		}
		PK pk  = DD.getPK(clazz);
		SeLectInfo s = new SeLectInfo().selection(pk.getColumn()+" = ?").selectionArgs(new String[]{pkValue});
		return s;
	}

	public static SeLectInfo columnIs(String[] column,String[] values){
		if(column==null||values==null){
			return null;
		}
		if(column.length!=values.length){
			throw new RuntimeException("columnvalues和长度不等啊");
		}
		if(column.length==1){
			return SeLectInfo.create().selection(column[0]+" = ? ").selectionArgs(values);
		}
		StringBuilder selection = new StringBuilder();
		int size = column.length;
		for(int i = 0;i<size;i++){
			selection.append(column[i]+" = ? ");
			if(i!=(size-1)){
				selection.append(" and ");
			}
		}
		return SeLectInfo.create().selection(selection.toString()).selectionArgs(values);

	}
}
