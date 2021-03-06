package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.agent.entity.DbConfiguration;

public class SqlServerCommand extends AbstractCommand {

	public SqlServerCommand(DbConfiguration dbConfiguration) throws RepsException {
		super(dbConfiguration);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String generatorCmd() throws RepsException {
		// 构建sqlcmd命令
		StringBuilder cmdSb = new StringBuilder();
		cmdSb.append(StringUtil.trim(cmdPath)).append(" -S ").append(host).append(",").append(port).append(" -U ").append(username).append(" -P ").append(password).append(" -d ").append(dbName).append(" -f 65001 -l 5 -b -V 15")
				.append(" -i ").append(scriptPath);
		return cmdSb.toString();
	}

}
