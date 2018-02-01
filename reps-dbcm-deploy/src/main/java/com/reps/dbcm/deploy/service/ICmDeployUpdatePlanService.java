package com.reps.dbcm.deploy.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.deploy.entity.CmDeployUpdatePlan;
import com.reps.dbcm.deploy.entity.OprMessage;

public interface ICmDeployUpdatePlanService {

	/**
	 * 新增项目更新任务
	 * @param cmDeployUpdatePlan
	 * @throws RepsException
	 */
	public void save(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException;

	/**
	 * 删除项目更新任务
	 * @param cmDeployUpdatePlan
	 * @throws RepsException
	 */
	public void delete(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException;

	/**
	 * 修改项目更新任务
	 * @param cmDeployUpdatePlan
	 * @throws RepsException
	 */
	public void update(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException;
	
	/**
	 * 根据ID查询项目更新任务
	 * @param id
	 * @return CmDeployUpdatePlan
	 * @throws RepsException
	 */
	public CmDeployUpdatePlan get(Integer id) throws RepsException;
	
	/**
	 * 获取最小的部署任务
	 * @param cmDeployUpdatePlan
	 * @return CmDeployUpdatePlan
	 * @throws RepsException
	 */
	public CmDeployUpdatePlan getMinUpdatePlanByFlag(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException;
	
	/**
	 * 查询所有未执行更新任务
	 * @param cmDeployUpdatePlan
	 * @return List<CmDeployUpdatePlan>
	 * @throws RepsException
	 */
	public List<CmDeployUpdatePlan> find(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException;
	
	/**
	 * 脚本执行器
	 * 执行 开始脚本 更新脚本 结束脚本
	 * @param cmDeployUpdatePlan
	 * @return OprMessage<String>
	 * @throws RepsException
	 */
	public OprMessage<String> updatePlanExecutor(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException;

}
