package com.reps.dbcm.agent.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.dbcm.agent.entity.OprMessage;

public class DbCommandExecutor extends BaseCommandExecutor {

	public DbCommandExecutor(CommandGenerator commandGenerator) {
		super(commandGenerator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OprMessage<String> execute() throws RepsException {
		Runtime runtime = null;// 取得当前运行期对象
		BufferedReader error = null;
		String[] generatorCmds = commandGenerator.generatorCmd();
		List<String> cmds = Arrays.asList(generatorCmds);
		try {
			runtime = Runtime.getRuntime();
			// 因为在命令窗口进行mysql数据库的导入一般分三步走，所以所执行的命令将以字符串数组的形式出现
			logger.info("execStr==" + cmds);
			Process process = runtime.exec(generatorCmds[0]);
			// 执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口
			// 进程执行后面的代码
			OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
			error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			// 命令1和命令2要放在一起执行
			writer.write(generatorCmds[1] + "\r\n" + generatorCmds[2]);
			writer.flush();
			writer.close();
			String ln = null;
			StringBuilder msg = new StringBuilder();
			for (int i = 0; (ln = error.readLine()) != null; i++) {
				logger.error(ln);
				msg.append(ln);
				if (i > 300) {
					error.close(); // 如果发现错误输出流中有信息，就关闭流，同时结束进程。
					process.destroy();
				}
			}
			return  getProcessResult(process, msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行命令时发生异常！ " + cmds, e);
			throw new RepsException(e);
		} finally {
			this.closeStream(error);
		}
	}
}
