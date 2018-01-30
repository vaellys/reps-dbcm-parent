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
		validateParam(this.dbConfiguration);
		// 数据库地址
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
		loginCmdSb.append(StringUtil.trim(cmdPath)).append(" -u").append(username).append(" -p").append(password);
		if (StringUtil.isNotBlank(host)) {
			loginCmdSb.append(" -h").append(host);
		}
		if (StringUtil.isNotBlank(port)) {
			loginCmdSb.append(" -P").append(port);
		}
		//设置编码
		String loginCmd = loginCmdSb.append(" --default-character-set=utf8").append(" --connect-timeout=4").toString();
		// 第二步，获取切换数据库到目标数据库的命令语句
		String switchCmd = new StringBuilder("use ").append(dbName).toString();
		// 第三步，获取导入的命令语句
		String importCmd = new StringBuilder("source ").append(scriptPath).toString();
		// 需要返回的命令语句数组
		String[] commands = new String[] { loginCmd, switchCmd, importCmd };
		return commands;
	}

	private void validateParam(DbConfiguration databaseInfo) throws RepsException {
		if (null == databaseInfo) {
			throw new RepsException("请设置数据库连接信息");
		}
		if (StringUtil.isEmpty(databaseInfo.getCmdPath())) {
			throw new RepsException("数据库执行命令路径为空");
		}
		if (StringUtil.isEmpty(databaseInfo.getUsername())) {
			throw new RepsException("数据库用户名为空");
		}
		if (StringUtil.isEmpty(databaseInfo.getPassword())) {
			throw new RepsException("数据库密码为空");
		}
		if (StringUtil.isEmpty(databaseInfo.getScriptPath())) {
			throw new RepsException("数据库脚本路径为空");
		}
		if (StringUtil.isEmpty(databaseInfo.getDbName())) {
			throw new RepsException("数据库名称空");
		}
	}

}
