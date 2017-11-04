package com.cgxt.base.baseJDBC;

/**
 * sql操作基本接口（保证static final和单例）
 *
 */
public interface SqlHandle {
	String SQL_INSERT = "insert";
	String SQL_UPDATE = "update";
	String SQL_DELETE = "delete";
}
