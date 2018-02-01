package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.agent.entity.DbConfiguration;

public class SqlServerCommand implements CommandGenerator {

	private DbConfiguration dbConfiguration;

	public SqlServerCommand(DbConfiguration dbConfiguration) {
		this.dbConfiguration = dbConfiguration;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String generatorCmd() throws RepsException {
		ConfigValidator.validateParam(this.dbConfiguration);
		/*// 数据库地址
		String host = dbConfiguration.getHost();
		// 数据库端口
		String port = dbConfiguration.getPort();
		// 获取命令路径 (数据库安装目录)
		String cmdPath = dbConfiguration.getCmdPath();
		// 获取数据库用户名称
		String username = dbConfiguration.getUsername();
		// 获取数据库密码
		String password = dbConfiguration.getPassword();
		// 获取数据库名称
		String dbName = dbConfiguration.getDbName();
		// 获取待执行脚本路径
		String scriptPath = dbConfiguration.getScriptPath();
		// 第一步，获取登录命令语句
		StringBuilder loginCmdSb = new StringBuilder();
		loginCmdSb.append(StringUtil.trim(cmdPath)).append(" -u").append(username).append(" -p").append(password);*/
		return "";
	}

}
