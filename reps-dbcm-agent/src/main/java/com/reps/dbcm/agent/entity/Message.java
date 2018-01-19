package com.reps.dbcm.agent.entity;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 6116817452919137990L;
	
	private String host;
	
	private String port;

	private String username;
	
	private String password;
	
	private String cmdHome;
	
	private String dbName;
	
	/** 脚本内容 */
	private String script;
	
	/** 脚本存放目录 */
	private String tempDir;
	
	/** 脚本文件名称 */
	private String scriptName;
	
	/** 数据库类型 和数据库执行方式 依赖于具体实现 */
	private String openWith;

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

	public String getCmdHome() {
		return cmdHome;
	}

	public void setCmdHome(String cmdHome) {
		this.cmdHome = cmdHome;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getOpenWith() {
		return openWith;
	}

	public void setOpenWith(String openWith) {
		this.openWith = openWith;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
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
	
}
