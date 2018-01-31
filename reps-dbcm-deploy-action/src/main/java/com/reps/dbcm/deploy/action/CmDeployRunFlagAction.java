package com.reps.dbcm.deploy.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.dbcm.deploy.entity.CmDeployRunFlag;
import com.reps.dbcm.deploy.service.ICmDeployRunFlagService;

@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/deploy/runflag")
public class CmDeployRunFlagAction extends BaseAction {
	
	protected final Logger logger = LoggerFactory.getLogger(CmDeployRunFlagAction.class);
	
	@Autowired
	ICmDeployRunFlagService cmDeployRunFlagService;
	
	@RequestMapping(value = "/list")
	public Object list() {
		try {
			ModelAndView mav = getModelAndView("/deploy/runflag/list");
			List<CmDeployRunFlag> runFlagList = cmDeployRunFlagService.query();
			mav.addObject("list", runFlagList);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete() {
		try {
			cmDeployRunFlagService.delete(null);
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

}
