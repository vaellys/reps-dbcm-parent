package com.reps.dbcm.deploy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.reps.core.orm.IdEntity;

/**
 * 
 * @ClassName: CmDeployRunFlag
 * @Description: 项目更新运行标志
 * @author qianguobing
 * @date 2018年1月22日 下午3:39:29
 */
@Entity
@Table(name = "cm_deploy_runflag")
public class CmDeployRunFlag implements Serializable {

	private static final long serialVersionUID = 8474062020280140243L;
	
	/** 任务ID */
	@Column(name = "planid", nullable = false, insertable = false, updatable = false)
	private Integer planId;
	
	/** 更新时间 */
	@Column(name = "updatetime")
	private Date updateTime;
	
	/**　正在执行的内容 */
	@Column(name = "updatecont", length = 3000)
	private String updateCont;
	
	/** 运行结果标识{成功、错误、警告、未执行、执行中} */
	@Id
	@Column(name = "updateflag", length = 50, nullable = false)
	private String updateFlag;
	
	/** 执行情况 */
	@Column(name = "updatelog", length = 3000)
	private String updateLog;
	
	@OneToOne(cascade={}, fetch=FetchType.EAGER)
	@JoinColumn(name="planid", insertable = false, updatable = false)
	private CmDeployUpdatePlan cmDeployUpdatePlan;
	
	/** 数据库连接数，用于服务程序控制连接数  */
	@Column(name = "dbcncount")
	private Integer dbcnCount;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateCont() {
		return updateCont;
	}

	public void setUpdateCont(String updateCont) {
		this.updateCont = updateCont;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public CmDeployUpdatePlan getCmDeployUpdatePlan() {
		return cmDeployUpdatePlan;
	}

	public void setCmDeployUpdatePlan(CmDeployUpdatePlan cmDeployUpdatePlan) {
		this.cmDeployUpdatePlan = cmDeployUpdatePlan;
	}

	public Integer getDbcnCount() {
		return dbcnCount;
	}

	public void setDbcnCount(Integer dbcnCount) {
		this.dbcnCount = dbcnCount;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	
}
