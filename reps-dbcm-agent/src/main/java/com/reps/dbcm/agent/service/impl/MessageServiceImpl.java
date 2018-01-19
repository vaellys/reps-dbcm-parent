package com.reps.dbcm.agent.service.impl;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.agent.engine.CommandExecutor;
import com.reps.dbcm.agent.engine.DbCommandExecutor;
import com.reps.dbcm.agent.engine.MysqlCommand;
import com.reps.dbcm.agent.entity.DbConfiguration;
import com.reps.dbcm.agent.entity.Message;
import com.reps.dbcm.agent.enums.DatabaseType;
import com.reps.dbcm.agent.service.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {

	private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	/** 命令所在上级目录 */
	private static final String BIN = "bin";

	/**
	 * 在指定临时目录中，生成脚本文件
	 * 
	 * @param message
	 *            接收消息对象
	 * @return File 脚本文件
	 * @throws RepsException
	 */
	private File scriptFileHandler(Message message) throws RepsException {
		File scriptFile = null;
		try {
			// 生成文件
			scriptFile = FileUtils.getFile(message.getTempDir(), message.getScriptName());
			FileUtils.touch(scriptFile);
			FileUtils.writeStringToFile(scriptFile, message.getScript(), Charset.forName("UTF-8"), false);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成脚本文件失败", e);
			throw new RepsException("生成脚本文件失败", e);
		}
		return scriptFile;
	}

	/**
	 * 创建数据库执行完整命令
	 * 
	 * @param cmdHome
	 *            命令安装目录
	 * @param cmd
	 *            执行命令名称
	 * @return String
	 * @throws RepsException
	 */
	public String buildCmdPath(String cmdHome, String cmd) throws RepsException {
		try {
			if (StringUtil.isBlank(cmdHome)) {
				throw new RepsException("数据库安装目录未指定");
			}
			return FileUtils.getFile(cmdHome, BIN, cmd).getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成数据库执行命令失败", e);
			throw new RepsException("生成数据库执行命令失败", e);
		}
	}

	@Override
	public <T> T messageHandler(Message message) throws RepsException {
		try {
			if (null == message) {
				throw new RepsException("参数异常");
			}
			String openWith = message.getOpenWith();
			if (StringUtil.isBlank(openWith)) {
				throw new RepsException("数据库打开方式未指定");
			}
			String tempDir = message.getTempDir();
			if (StringUtil.isBlank(tempDir)) {
				throw new RepsException("生成脚本文件目录为空");
			}
			String scriptName = message.getScriptName();
			if (StringUtil.isBlank(scriptName)) {
				throw new RepsException("脚本文件名字为空");
			}
			String script = message.getScript();
			if (StringUtil.isBlank(script)) {
				throw new RepsException("脚本文件内容为空");
			}
			File scriptFile = scriptFileHandler(message);
			if (DatabaseType.MYSQL.getType().equals(openWith)) {
				DbConfiguration databaseInfo = buildDbConfig(message, scriptFile, DatabaseType.MYSQL.getCommand());
				CommandExecutor commandExecutor = new DbCommandExecutor(new MysqlCommand(databaseInfo));
				return commandExecutor.execute();
			} else {
				throw new RepsException("暂不支持该类型的数据库命令");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行脚本文件失败", e);
			throw new RepsException(e);
		}
	}

	/**
	 * 
	 * @param message
	 * @param scriptFile
	 * @param dbCmd
	 *            数据执行命令方式
	 * @return
	 * @throws Exception
	 */
	private DbConfiguration buildDbConfig(Message message, File scriptFile, String dbCmd) throws RepsException {
		try {
			DbConfiguration databaseInfo = new DbConfiguration();
			BeanUtils.copyProperties(databaseInfo, message);
			databaseInfo.setScriptPath(scriptFile.getCanonicalPath());
			databaseInfo.setCmdPath(buildCmdPath(message.getCmdHome(), dbCmd));
			return databaseInfo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("构建数据库配置对象异常", e);
			throw new RepsException(e);
		}
	}

}
