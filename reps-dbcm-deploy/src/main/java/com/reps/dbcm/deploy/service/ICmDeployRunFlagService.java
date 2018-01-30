package com.reps.dbcm.deploy.service;

import java.util.Map;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.deploy.entity.CmDeployRunFlag;

public interface ICmDeployRunFlagService {

	/**
	 * 新增项目更新运行标志
	 * @param cmDeployRunFlag
	 * @throws RepsException
	 */
	public void save(CmDeployRunFlag cmDeployRunFlag) throws RepsException;

	/**
	 * 删除项目更新运行标志
	 * @param cmDeployRunFlag
	 * @throws RepsException
	 */
	public void delete(CmDeployRunFlag cmDeployRunFlag) throws RepsException;

	/**
	 * 修改项目更新运行标志
	 * @param cmDeployRunFlag
	 * @throws RepsException
	 */
	public void update(CmDeployRunFlag cmDeployRunFlag) throws RepsException;
	
	/**
	 * 根据ID查询项目更新运行标志
	 * @param id
	 * @return CmDeployRunFlag
	 * @throws RepsException
	 */
	public CmDeployRunFlag get(String id) throws RepsException;
	
	public CmDeployRunFlag get() throws RepsException;
	
	public void updateCheckRunFlag() throws RepsException;
	
	public int getRowCount(String... updateFlags) throws RepsException;
	
	public void updateRunFlag(CmDeployRunFlag cmDeployRunFlag) throws RepsException;

	public boolean updateRunFlagAndExecuteRequest(CmDeployRunFlag runFlag, Map<String, String> requestParamsMap) throws RepsException;

}
