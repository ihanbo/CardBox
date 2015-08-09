package com.hans.mydb.model;

/**
 * IO异常
 */
public class DbException extends Exception {

    private static final long serialVersionUID = 7729676731472012868L;
    
    private String content = "N/A";	//网络请求返回的内容
    public static final String REASON_DB = "database exception";
    public static final String REASON_NET_OR_SERVER = "net request exception";

    public DbException() {
        super();
    }
    public DbException(String detailMessage) {
    	super(detailMessage);
    }
    
    public DbException(String detailMessage,String content) {
        super(detailMessage);
        if(content!=null){
        	this.content = content;
        }
    }
    public DbException(String detailMessage, Throwable throwable,String content) {
        super(detailMessage, throwable);
        if(content!=null){
        	this.content = content;
        }
    }
    public DbException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DbException(Throwable throwable) {
        super(throwable);
    }
    
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
