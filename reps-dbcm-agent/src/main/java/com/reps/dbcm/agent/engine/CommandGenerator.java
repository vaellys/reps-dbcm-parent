package com.reps.dbcm.agent.engine;

import com.reps.core.exception.RepsException;

public interface CommandGenerator {
	
	public <T> T generatorCmd() throws RepsException;
	
}
