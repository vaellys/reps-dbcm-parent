package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;

public interface CommandExecutor {

	public <T> T execute() throws RepsException;

}
