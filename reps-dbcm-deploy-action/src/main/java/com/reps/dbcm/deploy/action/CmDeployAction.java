package com.reps.dbcm.deploy.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.orm.ListResult;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.dbcm.deploy.entity.CmDeploy;
import com.reps.dbcm.deploy.entity.CmDeployUpdatePlan;
import com.reps.dbcm.deploy.entity.OprMessage;
import com.reps.dbcm.deploy.enums.StatusFlag;
import com.reps.dbcm.deploy.service.ICmDeployService;
import com.reps.dbcm.deploy.service.ICmDeployUpdatePlanService;

@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/deploy/cmdepy")
public class CmDeployAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(CmDeployAction.class);

	@Autowired
	ICmDeployUpdatePlanService cmDeployUpdatePlanService;

	@Autowired
	ICmDeployService cmDeployService;

	@RequestMapping(value = "/list")
	public Object list(Pagination pager, CmDeploy cmDeploy) {
		ModelAndView mav = getModelAndView("/deploy/cmdepy/list");
		try {
			ListResult<CmDeploy> deployList = cmDeployService.query(pager.getStartRow(), pager.getPageSize(), cmDeploy);
			mav.addObject("cmDeploy", cmDeploy);
			// 分页数据
			mav.addObject("list", deployList.getList());
			// 分页参数
			pager.setTotalRecord(deployList.getCount().longValue());
			mav.addObject("pager", pager);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/executedeploy")
	@ResponseBody
	public Object executeUpdatePlan(CmDeployUpdatePlan cmDeployUpdatePlan) {
		try {
			OprMessage<String> oprMessage = cmDeployUpdatePlanService.updatePlanExecutor(cmDeployUpdatePlan);
			if (StatusFlag.SUCCESS == oprMessage.getStatus()) {
				return ajax(AjaxStatus.OK, oprMessage.getMessage());
			} else {
				return ajax(AjaxStatus.ERROR, oprMessage.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行部署失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/deploy/cmdepy/edit");
		CmDeploy cmDeploy = cmDeployService.get(id);
		mav.addObject("cmDeploy", cmDeploy);
		return mav;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(CmDeploy cmDeploy) {
		try {
			cmDeployService.update(cmDeploy);
			return ajax(AjaxStatus.OK, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/show")
	public Object show(String id) {
		try {
			ModelAndView mav = new ModelAndView("/deploy/cmdepy/show");
			CmDeploy cmDeploy = cmDeployService.get(id);
			mav.addObject("cmDeploy", cmDeploy);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取详情失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

}
