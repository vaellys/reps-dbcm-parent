package com.reps.dbcm.deploy.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MetaKey {

	public static final Map<String, String> FIELD_MAPS = new LinkedHashMap<>();
	
	/**
	 * 无数据脚本KEY
	 */
	public static final String TEMP1_SCRIPT = "TEMP1SCRIPT";
	public static final String TEMP2_CLOB = "TEMP2CLOB";
	public static final String TEMP3_SCRIPT = "TEMP3SCRIPT";
	
	public static final String SCRIPT = "script";
	public static final String SCRIPT_NAME = "scriptName";
	
	static {
		FIELD_MAPS.put("DBNAME", "dbName");
		FIELD_MAPS.put("DBUSER", "username");
		FIELD_MAPS.put("DBPASSWORD", "password");
		FIELD_MAPS.put("DBHOST", "host");
		FIELD_MAPS.put("DBPORT", "port");
		FIELD_MAPS.put("TEMPDIR", "tempDir");
		FIELD_MAPS.put("DBUTILITYDIR", "cmdHome");
	}
}
