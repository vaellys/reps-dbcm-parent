package com.reps.dbcm.deploy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.dbcm.deploy.entity.CmDeploy;

@Repository
public class CmDeployDao {
	
	@Autowired
	IGenericDao<CmDeploy> dao;
	
	public void save(CmDeploy cmDeploy) {
		dao.save(cmDeploy);
	}
	
	public void delete(CmDeploy cmDeploy) {
		dao.delete(cmDeploy);
	}
	
	public void update(CmDeploy cmDeploy) {
		dao.update(cmDeploy);
	}
	
	public CmDeploy get(String id) {
		return dao.get(CmDeploy.class, id);
	}
}
