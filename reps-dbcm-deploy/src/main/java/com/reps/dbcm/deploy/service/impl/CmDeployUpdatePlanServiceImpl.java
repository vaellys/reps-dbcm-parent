package com.reps.dbcm.deploy.service.impl;

import static com.reps.dbcm.deploy.enums.UpdateFlag.NON_EXECUTION;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.dbcm.deploy.dao.CmDeployUpdatePlanDao;
import com.reps.dbcm.deploy.entity.CmDeploy;
import com.reps.dbcm.deploy.entity.CmDeployRunFlag;
import com.reps.dbcm.deploy.entity.CmDeployUpdatePlan;
import com.reps.dbcm.deploy.enums.UpdateFlag;
import com.reps.dbcm.deploy.service.ICmDeployRunFlagService;
import com.reps.dbcm.deploy.service.ICmDeployService;
import com.reps.dbcm.deploy.service.ICmDeployUpdatePlanService;
import com.reps.dbcm.deploy.util.MetaKey;

/**
 * 
 * @ClassName: CmDeployServiceImpl
 * @Description: 项目更新任务业务类实现
 * @author qianguobing
 * @date 2018年1月22日 下午3:48:33
 */
@Service
@Transactional
public class CmDeployUpdatePlanServiceImpl implements ICmDeployUpdatePlanService {

	protected final Logger logger = LoggerFactory.getLogger(CmDeployUpdatePlanServiceImpl.class);

	@Autowired
	CmDeployUpdatePlanDao dao;

	@Autowired
	ICmDeployRunFlagService cmDeployRunFlagService;

	@Autowired
	ICmDeployService cmDeployService;

	@Override
	public void save(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException {
		dao.save(cmDeployUpdatePlan);
	}

	@Override
	public void delete(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException {
		dao.delete(cmDeployUpdatePlan);
	}

	@Override
	public void update(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException {
		if (null == cmDeployUpdatePlan) {
			throw new RepsException("参数异常");
		}
		CmDeployUpdatePlan updatePlan = this.get(cmDeployUpdatePlan.getPlanId());
		if (null == updatePlan) {
			throw new RepsException("该更新任务ID无效");
		}
		String planFrom = cmDeployUpdatePlan.getPlanFrom();
		if (StringUtil.isNotBlank(planFrom)) {
			updatePlan.setPlanFrom(planFrom);
		}
		String deployId = cmDeployUpdatePlan.getDeployId();
		if (StringUtil.isNotBlank(deployId)) {
			updatePlan.setDeployId(deployId);
		}
		Integer whxh = cmDeployUpdatePlan.getWhxh();
		if (null != whxh) {
			updatePlan.setWhxh(whxh);
		}
		Date timePlan = cmDeployUpdatePlan.getTimePlan();
		if (null != timePlan) {
			updatePlan.setTimePlan(timePlan);
		}
		Date updateTime = cmDeployUpdatePlan.getUpdateTime();
		if (null != updateTime) {
			updatePlan.setUpdateTime(updateTime);
		}
		String updateFlag = cmDeployUpdatePlan.getUpdateFlag();
		if (StringUtil.isNotBlank(updateFlag)) {
			updatePlan.setUpdateFlag(updateFlag);
		}
		String updateLog = cmDeployUpdatePlan.getUpdateLog();
		if (StringUtil.isNotBlank(updateLog)) {
			updatePlan.setUpdateLog(updateLog);
		}
		Integer objId = cmDeployUpdatePlan.getObjId();
		if (null != objId) {
			updatePlan.setObjId(objId);
		}
		String objName = cmDeployUpdatePlan.getObjName();
		if (StringUtil.isNotBlank(objName)) {
			updatePlan.setObjName(objName);
		}
		String cmDomainName = cmDeployUpdatePlan.getCmDomainName();
		if (StringUtil.isNotBlank(cmDomainName)) {
			updatePlan.setCmDomainName(cmDomainName);
		}
		String openWith = cmDeployUpdatePlan.getOpenWith();
		if (StringUtil.isNotBlank(openWith)) {
			updatePlan.setOpenWith(openWith);
		}
		String beginScrip = cmDeployUpdatePlan.getBeginScrip();
		if (StringUtil.isNotBlank(beginScrip)) {
			updatePlan.setBeginScrip(beginScrip);
		}
		String endScrip = cmDeployUpdatePlan.getEndScrip();
		if (StringUtil.isNotBlank(endScrip)) {
			updatePlan.setEndScrip(endScrip);
		}
		String script = cmDeployUpdatePlan.getScript();
		if (StringUtil.isNotBlank(script)) {
			updatePlan.setScript(script);
		}
		dao.update(updatePlan);
	}

	@Override
	public CmDeployUpdatePlan get(Integer id) throws RepsException {
		return dao.get(id);
	}

	@Override
	public CmDeployUpdatePlan getMinUpdatePlanByFlag(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException {
		return dao.getMinUpdatePlanByFlag(cmDeployUpdatePlan);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updatePlanExecutor(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException {
		try {
			if (null == cmDeployUpdatePlan) {
				throw new RepsException("参数异常");
			}
			String deployId = cmDeployUpdatePlan.getDeployId();
			if (StringUtil.isBlank(deployId)) {
				throw new RepsException("参数异常:项目部署ID为空");
			}
			// 状态位检查
			cmDeployRunFlagService.updateCheckRunFlag();
			// 将元数据映射成为请求参数
			Map<String, String> metaDataMap = getMetaDataMap(deployId);
			Map<String, String> paramsMap = getRequestParamMap(getMetaDataMap(deployId));
			// 查询未执行的任务
			List<CmDeployUpdatePlan> updatePlanlist = findUpdatePlan(cmDeployUpdatePlan);
			if (null != updatePlanlist && !updatePlanlist.isEmpty()) {
				for (CmDeployUpdatePlan updatePlan : updatePlanlist) {
					Integer planId = updatePlan.getPlanId();
					if (null == planId) {
						logger.error("更新任务ID为空 planId");
						throw new RepsException("更新任务ID为空");
					}
					String openWith = updatePlan.getOpenWith();
					if (StringUtil.isBlank(openWith)) {
						logger.error("该更新任务打开方式为空 planId {}", planId);
						throw new RepsException("该更新任务打开方式为空 ");
					}
					// 设置打开方式
					paramsMap.put("openWith", openWith);
					Date timePlan = updatePlan.getTimePlan();
					if (null == timePlan || timePlan.getTime() <= System.currentTimeMillis()) {
						logger.error("该任务计划执行时间小于或等于当前时间  planId {}", planId);
						throw new RepsException("该任务计划执行时间小于或等于当前时间 ");
					}
					String scriptName = metaDataMap.get("TEMP1SCRIPT");
					if (StringUtil.isBlank(scriptName)) {
						logger.error("脚本文件名字为空 planId {}", planId);
						throw new RepsException("脚本文件名字为空");
					}
					// 设置参数
					CmDeployRunFlag cmDeployRunFlag = new CmDeployRunFlag();
					cmDeployRunFlag.setPlanId(planId);
					// 获取开始脚本
					String beginScrip = updatePlan.getBeginScrip();
					boolean retVal = false;
					if (StringUtil.isNotBlank(beginScrip)) {
						// 执行脚本前设置运行状态
						cmDeployRunFlag.setUpdateFlag(UpdateFlag.STEP1.getCode());
						cmDeployRunFlag.setUpdateLog("---------begin script----------");
						retVal = executeUpdatePlan(metaDataMap, paramsMap, cmDeployRunFlag, new String[] { "TEMP1SCRIPT", beginScrip }, retVal);
					} else {
						logger.info("该更新任务planId {} step1无可执行脚本", planId);
						retVal = true;
					}
					if (retVal) {
						String script = updatePlan.getScript();
						if (StringUtil.isNotBlank(script)) {
							// 执行脚本前设置运行状态
							cmDeployRunFlag.setUpdateFlag(UpdateFlag.STEP2.getCode());
							cmDeployRunFlag.setUpdateLog("---------script----------");
							executeUpdatePlan(metaDataMap, paramsMap, cmDeployRunFlag, new String[] { "TEMP2CLOB", script }, retVal);
						} else {
							logger.info("该更新任务planId {} step2无可执行脚本", planId);
						}
					} else {
						break;
					}
					retVal = false;
					String endScript = updatePlan.getEndScrip();
					if (StringUtil.isNotBlank(endScript)) {
						// 执行脚本前设置运行状态
						cmDeployRunFlag.setUpdateFlag(UpdateFlag.STEP3.getCode());
						cmDeployRunFlag.setUpdateLog("---------end script----------");
						retVal = executeUpdatePlan(metaDataMap, paramsMap, cmDeployRunFlag, new String[] { "TEMP3SCRIPT", endScript }, retVal);
					} else {
						logger.info("该更新任务planId {} step3无可执行脚本", planId);
						retVal = true;
					}
					// 最后更新任务和运行表的状态
					if (retVal) {
						cmDeployRunFlag.setUpdateFlag(UpdateFlag.SUCCESS.getCode());
						updateStatus(cmDeployRunFlag, UpdateFlag.SUCCESS.getCode());
					} else {
						break;
					}
				}
			} else {
				logger.info("没有需要更新的任务  deployId {} ", deployId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行部署更新任务异常");
			throw new RepsException(e);
		}
	}

	private boolean executeUpdatePlan(Map<String, String> metaDataMap, Map<String, String> paramsMap, CmDeployRunFlag cmDeployRunFlag, String[] scriptParams, boolean retVal) {
		try {
			retVal = sendRequest(metaDataMap, paramsMap, cmDeployRunFlag, scriptParams[1], scriptParams[0]);
		} catch (Exception e) {
			updateStatus(cmDeployRunFlag, e.getMessage());
			throw new RepsException(e);
		}
		return retVal;
	}

	private void updateStatus(CmDeployRunFlag cmDeployRunFlag, String msg) {
		cmDeployRunFlag.setUpdateLog(msg);
		cmDeployRunFlagService.updateRunFlag(cmDeployRunFlag);
	}

	private boolean sendRequest(Map<String, String> metaDataMap, Map<String, String> paramsMap, CmDeployRunFlag cmDeployRunFlag, String scriptContent, String scriptNameKey) {
		// 追加脚本参数
		putParams(paramsMap, scriptContent, metaDataMap.get(scriptNameKey));
		// 执行脚本前设置运行状态
		return cmDeployRunFlagService.updateRunFlagAndExecuteRequest(cmDeployRunFlag, paramsMap);
	}

	private void putParams(Map<String, String> paramsMap, String scriptContent, String scriptName) {
		paramsMap.put("script", scriptContent);
		paramsMap.put("scriptName", scriptName);
	}

	private List<CmDeployUpdatePlan> findUpdatePlan(CmDeployUpdatePlan cmDeployUpdatePlan) {
		cmDeployUpdatePlan.setUpdateFlag(NON_EXECUTION.getCode());
		// 查询所有未执行的
		List<CmDeployUpdatePlan> updatePlanlist = find(cmDeployUpdatePlan);
		return updatePlanlist;
	}

	private Map<String, String> getRequestParamMap(Map<String, String> metaDataMap) {
		// 构建请求参数 MAP
		Map<String, String> paramsMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : MetaKey.FIELD_MAPS.entrySet()) {
			paramsMap.put(entry.getValue(), metaDataMap.get(entry.getKey()));
		}
		return paramsMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getMetaDataMap(String deployId) throws RepsException {
		try {
			// 查询部署信息
			CmDeploy cmDeploy = cmDeployService.get(deployId);
			String metaData = cmDeploy.getMetaData();
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> metaDataMap = mapper.readValue(metaData, Map.class);
			return metaDataMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取元数据信息异常", e);
			throw new RepsException(e);
		}
	}

	@Override
	public List<CmDeployUpdatePlan> find(CmDeployUpdatePlan cmDeployUpdatePlan) throws RepsException {
		return dao.find(cmDeployUpdatePlan);
	}

}
