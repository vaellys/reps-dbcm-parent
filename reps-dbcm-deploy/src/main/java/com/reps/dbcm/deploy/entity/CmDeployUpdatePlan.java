package com.reps.dbcm.deploy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @ClassName: CmDeployUpdatePlan
 * @Description: 项目更新任务
 * @author qianguobing
 * @date 2018年1月22日 下午2:17:29
 */
@Entity
@Table(name = "cm_deploy_updateplan")
public class CmDeployUpdatePlan implements Serializable  {
	
	private static final long serialVersionUID = 3567503587664351552L;
	
	/** 任务ID */
	@Id
	@Column(name = "planid", nullable = false, insertable = false, updatable = false)
	private Integer planId;
	
	/** 任务来源{对象变更、新增对象、删除对象、手工增加} */
	@Column(name = "planfrom", length = 50, nullable = false)
	private String planFrom;
	
	/** 部署ID */
	@Column(name = "deployid", nullable = false, length = 50, insertable = false, updatable = false)
	private String deployId;
	
	@JsonIgnore
	@ManyToOne(cascade = {})
	@JoinColumn(name = "deployid", nullable = true)
	private CmDeploy cmDeploy;
	
	/** 维护序号，引用对象变更历史或项目对象变更历史的WHXH.如果任务来源为手工增加则=-1 */
	@Column(name = "whxh")
	private Integer whxh;
	
	/** 计划开始时间,如果为空表示尽快ASAP */
	@Column(name = "timeplan")
	private Date timePlan;
	
	/** 更新完成时间 */
	@Column(name = "updatetime")
	private Date updateTime;
	
	/** 运行结果标识{成功、错误、警告、未执行、STEP1 STEP2 STEP3} */
	@Column(name = "updateflag", length = 50)
	private String updateFlag;
	
	/** 执行情况 */
	@Column(name = "updatelog", length = 3000, nullable = false)
	private String updateLog;
	
	/** 对象标识 */
	@Column(name = "objid", nullable = false)
	private Integer objId;
	
	/** 对象名称 */
	@Column(name = "objname", length = 100)
	private String objName;
	
	/** 配置域名称 */
	@Column(name = "cm_domain_name", length = 50, nullable = false)
	private String cmDomainName;
	
	/** 执行脚本的数据库用户 */
	@Column(name = "dbuser", length = 50)
	private String dbUser;
	
	/** 打开方式  */
	@Column(name = "openwith", length = 3000)
	private String openWith;
	
	/** 执行前需要的命令  */
	@Column(name = "beginscrip", length = 3000)
	private String beginScrip;
	
	/** 执行后需要的命令  */
	@Column(name = "endscrip", length = 3000)
	private String endScrip;
	
	/** 脚本内容  */
	@Column(name = "script")
	private String script;
	
	/** 二进制内容 */
	@Column(name = "bincont")
	private Byte[] binCont;

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanFrom() {
		return planFrom;
	}

	public void setPlanFrom(String planFrom) {
		this.planFrom = planFrom;
	}

	public CmDeploy getCmDeploy() {
		return cmDeploy;
	}

	public void setCmDeploy(CmDeploy cmDeploy) {
		this.cmDeploy = cmDeploy;
	}

	public Integer getWhxh() {
		return whxh;
	}

	public void setWhxh(Integer whxh) {
		this.whxh = whxh;
	}

	public Date getTimePlan() {
		return timePlan;
	}

	public void setTimePlan(Date timePlan) {
		this.timePlan = timePlan;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getCmDomainName() {
		return cmDomainName;
	}

	public void setCmDomainName(String cmDomainName) {
		this.cmDomainName = cmDomainName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getOpenWith() {
		return openWith;
	}

	public void setOpenWith(String openWith) {
		this.openWith = openWith;
	}

	public String getBeginScrip() {
		return beginScrip;
	}

	public void setBeginScrip(String beginScrip) {
		this.beginScrip = beginScrip;
	}

	public String getEndScrip() {
		return endScrip;
	}

	public void setEndScrip(String endScrip) {
		this.endScrip = endScrip;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Byte[] getBinCont() {
		return binCont;
	}

	public void setBinCont(Byte[] binCont) {
		this.binCont = binCont;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}
	
}
