package com.reps.dbcm.deploy.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.deploy.entity.CmDeployUpdatePlan;

@Repository
public class CmDeployUpdatePlanDao {
	
	@Autowired
	IGenericDao<CmDeployUpdatePlan> dao;
	
	public void save(CmDeployUpdatePlan cmDeployUpdatePlan) {
		dao.save(cmDeployUpdatePlan);
	}
	
	public void delete(CmDeployUpdatePlan cmDeployUpdatePlan) {
		dao.delete(cmDeployUpdatePlan);
	}
	
	public void update(CmDeployUpdatePlan cmDeployUpdatePlan) {
		dao.update(cmDeployUpdatePlan);
	}
	
	public CmDeployUpdatePlan get(Integer id) {
		return dao.get(CmDeployUpdatePlan.class, id);
	}
	
	public CmDeployUpdatePlan getMinUpdatePlanByFlag(CmDeployUpdatePlan cmDeployUpdatePlan) {
		DetachedCriteria dc = DetachedCriteria.forClass(CmDeployUpdatePlan.class);
		if(null != cmDeployUpdatePlan) {
			dc.setProjection(Projections.projectionList()
					.add(Projections.min("planId").as("planId"))
					.add(Projections.property("timePlan"))
					.add(Projections.property("beginScrip"))
					.add(Projections.property("openWith"))
					.add(Projections.property("deployId")));
			String updateFlag = cmDeployUpdatePlan.getUpdateFlag();
			if(StringUtil.isNotBlank(updateFlag)) {
				dc.add(Restrictions.eq("updateFlag", updateFlag));
			}
			String deployId = cmDeployUpdatePlan.getDeployId();
			if(StringUtil.isNotBlank(deployId)) {
				dc.add(Restrictions.eq("deployId", deployId));
			}
		}
		return (CmDeployUpdatePlan) dc.getExecutableCriteria(dao.getSession()).setResultTransformer(Transformers.aliasToBean(CmDeployUpdatePlan.class)).uniqueResult();
	}
	
	public List<CmDeployUpdatePlan> find(CmDeployUpdatePlan cmDeployUpdatePlan) {
		DetachedCriteria dc = DetachedCriteria.forClass(CmDeployUpdatePlan.class);
		if (null != cmDeployUpdatePlan) {
			String updateFlag = cmDeployUpdatePlan.getUpdateFlag();
			if(StringUtil.isNotBlank(updateFlag)) {
				dc.add(Restrictions.eq("updateFlag", updateFlag));
			}
			String deployId = cmDeployUpdatePlan.getDeployId();
			if(StringUtil.isNotBlank(deployId)) {
				dc.add(Restrictions.eq("deployId", deployId));
			}
		}
		dc.addOrder(Order.asc("planId"));
		return dao.findByCriteria(dc);
	}
	
}
