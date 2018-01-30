package com.reps.dbcm.deploy.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.reps.core.SpringContext;

/**
 * 
 * @ClassName: ConnectionManager
 * @Description: 获取Connection连接
 * @author qianguobing
 * @date 2018年1月29日 上午9:40:52
 */
public class ConnectionManager {   
	
	public static JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContext.getBean("jdbcTemplate");
	
	public static Connection getConnection() throws SQLException { 
		return jdbcTemplate.getDataSource().getConnection();
	}

}
