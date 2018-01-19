package com.reps.dbcm.agent.service;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.agent.entity.Message;

public interface IMessageService {
	
	public <T> T messageHandler(Message message) throws RepsException;
	
}
