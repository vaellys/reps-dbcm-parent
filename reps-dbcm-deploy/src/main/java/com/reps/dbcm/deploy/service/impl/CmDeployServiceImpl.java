package com.reps.dbcm.deploy.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.deploy.dao.CmDeployDao;
import com.reps.dbcm.deploy.entity.CmDeploy;
import com.reps.dbcm.deploy.service.ICmDeployService;

/**
 * 
 * @ClassName: CmDeployServiceImpl
 * @Description: 项目部署定义业务类实现
 * @author qianguobing
 * @date 2018年1月22日 下午3:48:33
 */
@Service
@Transactional
public class CmDeployServiceImpl implements ICmDeployService {
	
	protected final Logger logger = LoggerFactory.getLogger(CmDeployServiceImpl.class);
	
	@Autowired
	CmDeployDao dao;

	@Override
	public void save(CmDeploy cmDeploy) throws RepsException {
		dao.save(cmDeploy);
	}

	@Override
	public void delete(CmDeploy cmDeploy) throws RepsException {
		dao.delete(cmDeploy);
	}

	@Override
	public void update(CmDeploy cmDeploy) throws RepsException {
		if(null == cmDeploy) {
			throw new RepsException("参数异常");
		}
		CmDeploy deploy = this.get(cmDeploy.getDeployId());
		if(null == deploy) {
			throw new RepsException("参数异常:部署项目ID无效");
		}
		String deployName = cmDeploy.getDeployName();
		if(StringUtil.isNotBlank(deployName)) {
			deploy.setDeployName(deployName);
		}
		String metaData = cmDeploy.getMetaData();
		if(StringUtil.isNotBlank(metaData)) {
			deploy.setMetaData(metaData);
		}
		String prjCode = cmDeploy.getPrjCode();
		if(StringUtil.isNotBlank(prjCode)) {
			deploy.setPrjCode(prjCode);
		}
		Integer whxh = cmDeploy.getWhxh();
		if(null != whxh) {
			deploy.setWhxh(whxh);
		}
		dao.update(deploy);
	}

	@Override
	public CmDeploy get(String id) throws RepsException {
		return dao.get(id);
	}

	@Override
	public ListResult<CmDeploy> query(int start, int pagesize, CmDeploy cmDeploy) {
		return dao.query(start, pagesize, cmDeploy);
	}
	
}
