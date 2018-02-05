package com.reps.dbcm.agent.engine;

import org.apache.commons.io.FileUtils;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.agent.entity.DbConfiguration;

public class ConfigValidator {

	private ConfigValidator() {
	}

	protected static void validateParam(DbConfiguration databaseInfo) throws RepsException {
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

	/**
	 * 创建数据库执行完整命令
	 * 
	 * @param cmdHome
	 *            命令安装目录
	 * @param parentDir
	 *            命令上级目录
	 * @param cmd
	 *            执行命令名称
	 * @return String
	 * @throws RepsException
	 */
	public static String buildFullCmdPath(String cmdHome, String parentDir, String cmd) throws RepsException {
		try {
			if (StringUtil.isBlank(cmdHome)) {
				throw new RepsException("数据库安装目录未指定");
			}
			return FileUtils.getFile(cmdHome, parentDir, cmd).getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepsException("生成数据库执行命令失败", e);
		}
	}
	
	/**
	 * 获取数据库执行命令完整路径
	 * @param cmdPath
	 * @return
	 * @throws RepsException
	 */
	public static String formatCmdPath(String cmdPath) throws RepsException {
		try {
			if (StringUtil.isBlank(cmdPath)) {
				throw new RepsException("数据库执行命令未指定");
			}
			return FileUtils.getFile(cmdPath).getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepsException("生成数据库执行命令失败", e);
		}
	}
	
}
