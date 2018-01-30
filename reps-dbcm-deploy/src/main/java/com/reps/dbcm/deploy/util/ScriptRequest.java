package com.reps.dbcm.deploy.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reps.core.exception.RepsException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ScriptRequest {

	public static final Logger logger = LoggerFactory.getLogger(ScriptRequest.class);

	private ScriptRequest() {
	}

	public static JSONObject doPosts(String serverPath, String requestUri, Map<String, String> paramsMap) throws RepsException {
		try {
			String response = HttpClientFactory.getInstance().sendHttpPost(serverPath + requestUri, paramsMap);
			if (null == response) {
				logger.error("请求异常,paramsMap {}", JSONSerializer.toJSON(paramsMap).toString());
				throw new RepsException("请求异常");
			}
			JSONObject jsonObject = (JSONObject) JSONObject.fromObject(response);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e);
		}
	}

}
