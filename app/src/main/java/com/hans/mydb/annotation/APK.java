package com.hans.mydb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @title 主键配置
 * @description 不配置的时候默认找类的id或_id字段作为主键，column不配置的是默认为字段名
 * @author Han
 * @version 1.0
 * @created 2014.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) 
public @interface APK {
}
