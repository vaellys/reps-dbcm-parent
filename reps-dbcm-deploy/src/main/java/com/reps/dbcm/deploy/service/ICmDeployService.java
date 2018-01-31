package com.reps.dbcm.deploy.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.dbcm.deploy.entity.CmDeploy;

public interface ICmDeployService {

	/**
	 * 新增部署定义
	 * @param cmDeploy
	 * @throws RepsException
	 */
	public void save(CmDeploy cmDeploy) throws RepsException;

	/**
	 * 删除部署定义
	 * @param cmDeploy
	 * @throws RepsException
	 */
	public void delete(CmDeploy cmDeploy) throws RepsException;

	/**
	 * 修改部署定义
	 * @param cmDeploy
	 * @throws RepsException
	 */
	public void update(CmDeploy cmDeploy) throws RepsException;
	
	/**
	 * 根据ID查询部署定义
	 * @param id
	 * @return CmDeploy
	 * @throws RepsException
	 */
	public CmDeploy get(String id) throws RepsException;
	
	/**
	 * 查询所有部署项目
	 * @param start
	 * @param pagesize
	 * @param cmDeploy
	 * @return
	 */
	public ListResult<CmDeploy> query(int start, int pagesize, CmDeploy cmDeploy);

}
