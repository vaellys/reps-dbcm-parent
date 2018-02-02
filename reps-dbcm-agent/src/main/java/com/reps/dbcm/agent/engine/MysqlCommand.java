package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.agent.entity.DbConfiguration;

public class MysqlCommand implements CommandGenerator {

	private DbConfiguration dbConfiguration;
	
	public MysqlCommand(DbConfiguration dbConfiguration) {
		this.dbConfiguration = dbConfiguration;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] generatorCmd() throws RepsException {
		ConfigValidator.validateParam(this.dbConfiguration);
		// 数据库地址
		String host = dbConfiguration.getHost();
		// 数据库端口
		String port = dbConfiguration.getPort();
		// 获取命令路径 (数据库安装目录完整路径)
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
		loginCmdSb.append(StringUtil.trim(cmdPath)).append(" -u").append(username).append(" -p").append(password);
		if (StringUtil.isNotBlank(host)) {
			loginCmdSb.append(" -h").append(host);
		}
		if (StringUtil.isNotBlank(port)) {
			loginCmdSb.append(" -P").append(port);
		}
		// 设置编码
		String loginCmd = loginCmdSb.append(" --default-character-set=utf8").append(" --connect-timeout=4").append(" --delimiter=$$").toString();
		// 第二步，获取切换数据库到目标数据库的命令语句
		String switchCmd = new StringBuilder("use ").append(dbName).toString();
		// 第三步，获取导入的命令语句
		String importCmd = new StringBuilder("source ").append(scriptPath).toString();
		// 需要返回的命令语句数组
		String[] commands = new String[] { loginCmd, switchCmd, importCmd };
		return commands;
	}
	
}
