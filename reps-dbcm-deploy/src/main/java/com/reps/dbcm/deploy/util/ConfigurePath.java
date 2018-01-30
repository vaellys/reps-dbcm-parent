package com.reps.dbcm.deploy.util;

import com.reps.core.RepsContext;

public class ConfigurePath {

	/** 代理端部署路径 */
	public static final String AGENT_SERVER_URL = RepsContext.getConst("dbcm", "agentServerUrl");
	
	/** 代理端脚本执行器 api */
	public static final String AGENT_SCRIPT_EXECUTOR_URI = "/oapi/message/receive";
}
