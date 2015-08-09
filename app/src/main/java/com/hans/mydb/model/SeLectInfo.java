package com.hans.mydb.model;

import com.hans.mydb.in.DD;

import android.text.TextUtils;


public class SeLectInfo {
	private String selection;
	private String[] selectionArgs;
	private String groupBy;
	private String having;
	private String orderBy;

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
	
	/** 采用默认主键，条件为主键== */
	public static SeLectInfo _CommonPkValueIs(String pkValue){
		if(TextUtils.isEmpty(pkValue)){
			return null;
		}
		SeLectInfo s = new SeLectInfo().selection(DD.getCommonPKColumn()+" = ?").selectionArgs(new String[]{pkValue});
		return s;
	}
	/** 采用默认主键，条件为主键== */
	public static SeLectInfo _PkValueIs(Class<?> clazz,String pkValue){
		if(clazz==null||TextUtils.isEmpty(pkValue)){
			return null;
		}
		PK pk  = DD.getPK(clazz);
		SeLectInfo s = new SeLectInfo().selection(pk.getColumn()+" = ?").selectionArgs(new String[]{pkValue});
		return s;
	}
}
