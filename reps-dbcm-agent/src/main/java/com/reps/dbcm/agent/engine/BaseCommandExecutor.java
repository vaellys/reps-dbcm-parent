package com.reps.dbcm.agent.engine;

import static com.reps.dbcm.agent.enums.StatusFlag.FAIL;
import static com.reps.dbcm.agent.enums.StatusFlag.SUCCESS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.agent.entity.OprMessage;

public class BaseCommandExecutor implements CommandExecutor{
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseCommandExecutor.class);
	
	protected CommandGenerator commandGenerator;
	
	/**
	 * 构造具体的命令执行器，通过命令生成器
	 * 
	 * @param commandGenerator
	 */
	public BaseCommandExecutor(CommandGenerator commandGenerator) {
		this.commandGenerator = commandGenerator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OprMessage<String> execute() throws RepsException {
		Runtime runtime = null;// 取得当前运行期对象
		BufferedReader input = null;
		BufferedReader error = null;
		String generatorCmd = commandGenerator.generatorCmd();
		try {
			runtime = Runtime.getRuntime();
			logger.info("execStr==" + generatorCmd);
			Process process = runtime.exec(generatorCmd);
			// 使用缓存输入流获取屏幕输出。
			input = new BufferedReader(new InputStreamReader(process
					.getInputStream(), Charset.forName("GBK")));
			StringBuilder msg = new StringBuilder();
			printStream(input, msg);
			input.close();
			error = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName("GBK")));
			String ln = null;
			for (int i = 0; (ln = error.readLine()) != null; i++) {
				logger.error(ln);
				msg.append(ln);
				if (i > 300) {
					error.close(); // 如果发现错误输出流中有信息，就关闭流，同时结束进程。
					process.destroy();
				}
			}
			return getProcessResult(process, msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行命令时发生异常！ " + generatorCmd, e);
			throw new RepsException(e);
		} finally {
			closeStream(error);
		}
	}

	protected OprMessage<String> getProcessResult(Process process, StringBuilder msg) throws InterruptedException {
		return 0 == process.waitFor() ? new OprMessage<String>("脚本执行成功", SUCCESS) : new OprMessage<String>(msg.toString(), FAIL);
	}

	private void printStream(BufferedReader bufferedReader, StringBuilder msg) throws IOException {
		String line;
		// 读取屏幕输出
		while ((line = bufferedReader.readLine()) != null) {
			msg.append(line);
			logger.info(line);
		}
	}
	
	protected void closeStream(BufferedReader bufferedReader) {
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
				throw new RepsException(e);
			}
		}
	}

}
