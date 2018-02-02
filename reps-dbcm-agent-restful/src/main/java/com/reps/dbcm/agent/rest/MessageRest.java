package com.reps.dbcm.agent.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.dbcm.agent.entity.Message;
import com.reps.dbcm.agent.entity.OprMessage;
import com.reps.dbcm.agent.enums.StatusFlag;
import com.reps.dbcm.agent.service.IMessageService;

@RestController
@RequestMapping(value = "/oapi/message")
public class MessageRest extends RestBaseController {
	
	private final Log logger = LogFactory.getLog(MessageRest.class);
	
	@Autowired
	private IMessageService messageService;
	
	@RequestMapping(value = "/receive")
	public RestResponse<String> receive(Message message) {
		try {
			OprMessage<String> messageHandler = messageService.messageHandler(message);
			if(StatusFlag.SUCCESS == messageHandler.getStatus()) {
				return wrap(RestResponseStatus.OK, "脚本执行成功");
			} else {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "脚本执行失败", messageHandler.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("脚本执行异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "", e.getMessage());
		}
	}

}
