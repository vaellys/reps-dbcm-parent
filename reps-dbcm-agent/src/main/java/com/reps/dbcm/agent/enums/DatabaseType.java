package com.reps.dbcm.agent.enums;

public enum DatabaseType {

	MYSQL("MYSQL", "mysql"), SQLSERVER("SQLSERVER", "sqlcmd"), ORACLE("ORACLE", "sqlplus");

	private String type;
	
	private String command;

	private DatabaseType(String type, String command) {
		this.type = type;
		this.command = command;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
