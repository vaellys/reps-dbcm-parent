package com.reps.dbcm.deploy.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
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
	
	public ListResult<CmDeploy> query(int start, int pagesize, CmDeploy cmDeploy) {
		DetachedCriteria dc = DetachedCriteria.forClass(CmDeploy.class);
		if (null != cmDeploy) {
			String deployName = cmDeploy.getDeployName();
			if (StringUtil.isNotBlank(deployName)) {
				dc.add(Restrictions.like("deployName", deployName, MatchMode.ANYWHERE));
			}
		}
		return dao.query(dc, start, pagesize, Order.asc("whxh"));
	}
}
