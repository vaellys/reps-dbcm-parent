package com.reps.dbcm.agent.entity;

import java.io.Serializable;

public class DbConfiguration implements Serializable{
	
	private static final long serialVersionUID = -8487198819112563658L;
	
	/** 数据库服务所在地址 */
	private String host;
	
	/** 数据库端口 */
	private String port;

	/** 用户名 */
	private String username;
	
	/** 密码 */
	private String password;
	
	/** 数据库名称 */
	private String dbName;
	
	/** 获取待执行脚本文件路径 */
	private String scriptPath;
	
	/** 获取命令路径 (数据库安装目录) */
	private String cmdPath;
	
	public DbConfiguration() {
		super();
	}

	public DbConfiguration(String username, String password, String scriptPath, String cmdPath, String dbName) {
		super();
		this.username = username;
		this.password = password;
		this.scriptPath = scriptPath;
		this.cmdPath = cmdPath;
		this.dbName = dbName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getCmdPath() {
		return cmdPath;
	}

	public void setCmdPath(String cmdPath) {
		this.cmdPath = cmdPath;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "DbConfiguration [host=" + host + ", port=" + port + ", username=" + username + ", password=" + password + ", dbName=" + dbName + ", scriptPath=" + scriptPath + ", cmdPath=" + cmdPath
				+ "]";
	}

}
