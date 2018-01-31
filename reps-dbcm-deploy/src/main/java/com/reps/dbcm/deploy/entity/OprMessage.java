package com.reps.dbcm.deploy.entity;

import com.reps.dbcm.deploy.enums.StatusFlag;

public class OprMessage<T> {
	
	private T message;

	private StatusFlag status;
	
	public OprMessage(T message, StatusFlag status) {
		super();
		this.message = message;
		this.status = status;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

	public StatusFlag getStatus() {
		return status;
	}

	public void setStatus(StatusFlag status) {
		this.status = status;
	}
	
}
