package com.lanwantec.wechat.mybatis;

public class DynamicDataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static String getDataSourceType() {
//		contextHolder.get()
		return "primary";
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}

}