package com.reps.dbcm.deploy.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MetaKey {

	public static final Map<String, String> FIELD_MAPS = new LinkedHashMap<>();
	
	static {
		FIELD_MAPS.put("DBHOME", "cmdHome");
		FIELD_MAPS.put("DBNAME", "dbName");
		FIELD_MAPS.put("DBUSER", "username");
		FIELD_MAPS.put("DBPASSWORD", "password");
		FIELD_MAPS.put("DBHOST", "host");
		FIELD_MAPS.put("DBPORT", "port");
		FIELD_MAPS.put("TEMPDIR", "tempDir");
		FIELD_MAPS.put("TEMPSCRIPT", "scriptName");
	}
	
}
