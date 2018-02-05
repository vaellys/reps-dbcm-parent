package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.agent.entity.DbConfiguration;

public class OracleCommand extends AbstractCommand {

	public OracleCommand(DbConfiguration dbConfiguration) {
		super(dbConfiguration);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String generatorCmd() throws RepsException {
		// 构建sqlcmd命令
		StringBuilder cmdSb = new StringBuilder();
		cmdSb.append(StringUtil.trim(cmdPath)).append(" ").append(username).append("/").append(password).append("@").append(host).append(":").append(port).append("/").append(dbName).append(" @")
				.append(scriptPath);
		return cmdSb.toString();
	}

}
