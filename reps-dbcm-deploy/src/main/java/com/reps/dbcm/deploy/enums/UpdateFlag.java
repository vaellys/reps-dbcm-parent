package com.reps.dbcm.deploy.enums;

public enum UpdateFlag {

	SUCCESS("成功", "success"), ERROR("错误", "error"), WARNING("警告", "warning"), 
	
	NON_EXECUTION("未执行", "nonexecution"), STEP1("STEP1", "STEP1"), STEP2("STEP2", "STEP2"), 
	
	STEP3("STEP3", "STEP3");

	private String code;

	private String desc;

	UpdateFlag(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
