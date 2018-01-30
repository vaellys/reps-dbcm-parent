package com.reps.dbcm.deploy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: CmDeploy
 * @Description: 项目部署定义
 * @author qianguobing
 * @date 2018年1月22日 下午2:08:07
 */
@Entity
@Table(name = "cm_deploy")
public class CmDeploy implements Serializable {

	private static final long serialVersionUID = -201679423461364795L;

	/** 部署ID */
	@Id
	@Column(name = "deployid", nullable = false, length = 50)
	private String deployId;

	/** 项目代码 */
	@Column(name = "prjcode", length = 50)
	private String prjCode;

	/** 部署名称 */
	@Column(name = "deployname", length = 500)
	private String deployName;

	/** 元数据 */
	@Column(name = "metadata", length = 3000)
	private String metaData;

	/** 已发布任务维护序号 */
	@Column(name = "whxh")
	private Integer whxh;

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public String getPrjCode() {
		return prjCode;
	}

	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}

	public String getDeployName() {
		return deployName;
	}

	public void setDeployName(String deployName) {
		this.deployName = deployName;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public Integer getWhxh() {
		return whxh;
	}

	public void setWhxh(Integer whxh) {
		this.whxh = whxh;
	}

}
