package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.agent.entity.DbConfiguration;

public abstract class AbstractCommand implements CommandGenerator {
	
	// 数据库地址
	protected String host = "";
	
	// 数据库端口
	protected String port = "";
	
	// 获取命令路径 (数据库安装目录完整路径)
	protected String cmdPath = "";
	
	// 获取数据库用户名称
	protected String username = "";
	
	// 获取数据库密码
	protected String password = "";
	
	// 获取数据库名称
	protected String dbName = "";
	
	// 获取待执行脚本路径
	protected String scriptPath = "";
	
	public AbstractCommand(DbConfiguration dbConfiguration) throws RepsException{
		ConfigValidator.validateParam(dbConfiguration);
		init(dbConfiguration);
	}
	
	private void init(DbConfiguration dbConfiguration) throws RepsException{
		this.host = dbConfiguration.getHost();
		this.port = dbConfiguration.getPort();
		this.cmdPath = dbConfiguration.getCmdPath();
		this.username = dbConfiguration.getUsername();
		this.password = dbConfiguration.getPassword();
		this.dbName = dbConfiguration.getDbName();
		this.scriptPath = dbConfiguration.getScriptPath();
	}

}
