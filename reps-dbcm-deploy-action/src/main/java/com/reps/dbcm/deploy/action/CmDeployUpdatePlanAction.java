package com.reps.dbcm.deploy.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reps.core.RepsConstant;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.dbcm.deploy.entity.CmDeployUpdatePlan;
import com.reps.dbcm.deploy.service.ICmDeployRunFlagService;
import com.reps.dbcm.deploy.service.ICmDeployUpdatePlanService;

/**
 * 
 * @ClassName: CmDeployUpdatePlanAction
 * @Description: 任务更新模块
 * @author qianguobing
 * @date 2018年1月22日 下午4:01:36
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/deploy/updateplan")
public class CmDeployUpdatePlanAction extends BaseAction {
	
	protected final Logger logger = LoggerFactory.getLogger(CmDeployUpdatePlanAction.class);
	
	@Autowired
	ICmDeployUpdatePlanService cmDeployUpdatePlanService;
	
	@Autowired
	ICmDeployRunFlagService cmDeployRunFlagService;
	
	@RequestMapping(value = "/executeupdateplan")
	public Object executeUpdatePlan(CmDeployUpdatePlan cmDeployUpdatePlan) {
		try {
			cmDeployUpdatePlanService.updatePlanExecutor(cmDeployUpdatePlan);
			return ajax(AjaxStatus.OK, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行更新任务失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

}
