package com.reps.dbcm.deploy.service.impl;

import static com.reps.dbcm.deploy.enums.UpdateFlag.SUCCESS;
import static com.reps.dbcm.deploy.enums.UpdateFlag.WARNING;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.deploy.dao.CmDeployRunFlagDao;
import com.reps.dbcm.deploy.entity.CmDeployRunFlag;
import com.reps.dbcm.deploy.entity.CmDeployUpdatePlan;
import com.reps.dbcm.deploy.entity.OprMessage;
import com.reps.dbcm.deploy.enums.StatusFlag;
import com.reps.dbcm.deploy.service.ICmDeployRunFlagService;
import com.reps.dbcm.deploy.service.ICmDeployUpdatePlanService;
import com.reps.dbcm.deploy.util.ConfigurePath;
import com.reps.dbcm.deploy.util.ScriptRequest;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: CmDeployServiceImpl
 * @Description: 项目更新运行标志业务类实现
 * @author qianguobing
 * @date 2018年1月22日 下午3:48:33
 */
@Service
@Transactional
public class CmDeployRunFlagServiceImpl implements ICmDeployRunFlagService {

	protected final Logger logger = LoggerFactory.getLogger(CmDeployRunFlagServiceImpl.class);

	@Autowired
	CmDeployRunFlagDao dao;

	@Autowired
	ICmDeployUpdatePlanService cmDeployUpdatePlanService;

	@Override
	public void save(CmDeployRunFlag cmDeployRunFlag) throws RepsException {
		dao.save(cmDeployRunFlag);
	}

	@Override
	public void delete(CmDeployRunFlag cmDeployRunFlag) throws RepsException {
		dao.delete(cmDeployRunFlag);
	}

	@Override
	public void update(CmDeployRunFlag cmDeployRunFlag) throws RepsException {
		if (null == cmDeployRunFlag) {
			throw new RepsException("参数异常");
		}
		dao.update(cmDeployRunFlag);
	}

	@Override
	public CmDeployRunFlag get(String id) throws RepsException {
		return dao.get(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public synchronized void updateCheckRunFlag() throws RepsException {
		try {
			if (0 == this.getRowCount()) {
				CmDeployRunFlag cmDeployRunFlag = new CmDeployRunFlag();
				cmDeployRunFlag.setUpdateFlag(SUCCESS.getCode());
				save(cmDeployRunFlag);
			}
			if (0 == this.getRowCount(SUCCESS.getCode(), WARNING.getCode())) {
				throw new RepsException("cm_deploy_runflag 状态位不正确， 不允许继续执行后续命令");
			}
		} catch (RepsException e) {
			e.printStackTrace();
			logger.error("检查运行状态位异常");
			throw e;
		}
	}

	@Override
	public CmDeployRunFlag get() throws RepsException {
		List<CmDeployRunFlag> runFlagList = dao.findAll();
		if (null != runFlagList && !runFlagList.isEmpty()) {
			return runFlagList.get(0);
		}
		return null;
	}

	@Override
	public List<CmDeployRunFlag> query() throws RepsException {
		return dao.findAll();
	}

	@Override
	public int getRowCount(String... updateFlags) throws RepsException {
		return dao.getRowCount(updateFlags);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public OprMessage<String> updateRunFlagAndExecuteRequest(CmDeployRunFlag runFlag, Map<String, String> requestParamsMap) throws RepsException {
		updateRunFlag(runFlag);
		return executeRequest(requestParamsMap, runFlag);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized void updateRunFlag(CmDeployRunFlag runFlag) throws RepsException {
		String updateFlag = runFlag.getUpdateFlag();
		String updateLog = runFlag.getUpdateLog();
		Integer planId = runFlag.getPlanId();
		// 更新运行状态
		CmDeployRunFlag cmDeployRunFlag = new CmDeployRunFlag();
		cmDeployRunFlag.setUpdateTime(new Date());
		cmDeployRunFlag.setUpdateCont(runFlag.getUpdateCont());
		cmDeployRunFlag.setUpdateFlag(updateFlag);
		cmDeployRunFlag.setUpdateLog(updateLog);
		cmDeployRunFlag.setPlanId(runFlag.getPlanId());
		this.update(cmDeployRunFlag);

		// 更新计划任务
		CmDeployUpdatePlan bean = new CmDeployUpdatePlan();
		bean.setUpdateTime(new Date());
		bean.setUpdateFlag(updateFlag);
		bean.setUpdateLog(updateLog);
		bean.setPlanId(planId);
		cmDeployUpdatePlanService.update(bean);
	}

	public OprMessage<String> executeRequest(Map<String, String> paramsMap, CmDeployRunFlag runFlag) throws RepsException {
		String updateFlag = runFlag.getUpdateFlag();
		Integer planId = runFlag.getPlanId();
		JSONObject responseResult = ScriptRequest.doPosts(ConfigurePath.AGENT_SERVER_URL, ConfigurePath.AGENT_SCRIPT_EXECUTOR_URI, paramsMap);
		CmDeployRunFlag cmDeployRunFlag = new CmDeployRunFlag();
		cmDeployRunFlag.setPlanId(planId);
		cmDeployRunFlag.setUpdateFlag(updateFlag);
		if (200 != responseResult.getInt("status")) {
			String errorMsg = responseResult.getString("result");
			cmDeployRunFlag.setUpdateLog(errorMsg);
			this.updateRunFlag(cmDeployRunFlag);
			return new OprMessage<String>(errorMsg, StatusFlag.FAIL);
		} else {
			cmDeployRunFlag.setUpdateLog(runFlag.getUpdateLog());
			this.updateRunFlag(cmDeployRunFlag);
			return new OprMessage<String>("", StatusFlag.SUCCESS);
		}
	}

	@Override
	public boolean checkRunFlagExist(Integer planId, String... updateFlags) throws RepsException {
		return dao.getRowCount(planId, updateFlags) > 0 ? true : false;
	}

}
