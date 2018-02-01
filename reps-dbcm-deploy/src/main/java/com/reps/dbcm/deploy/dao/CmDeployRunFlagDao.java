package com.reps.dbcm.deploy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.dbcm.deploy.entity.CmDeployRunFlag;

@Repository
public class CmDeployRunFlagDao {

	@Autowired
	IGenericDao<CmDeployRunFlag> dao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(final CmDeployRunFlag cmDeployRunFlag) {

		final String sql = "insert into cm_deploy_runflag(updatetime, updatecont, updateflag, updatelog, planid, dbcncount) values(?,?,?,?,?,?)";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				conn.setAutoCommit(true);
				PreparedStatement ps = conn.prepareStatement(sql);
				Date updateTime = cmDeployRunFlag.getUpdateTime();
				ps.setTimestamp(1, null != updateTime ? new Timestamp(updateTime.getTime()) : null);
				ps.setString(2, cmDeployRunFlag.getUpdateCont());
				ps.setString(3, cmDeployRunFlag.getUpdateFlag());
				ps.setString(4, cmDeployRunFlag.getUpdateLog());
				ps.setObject(5, cmDeployRunFlag.getPlanId());
				ps.setObject(6, cmDeployRunFlag.getDbcnCount());
				return ps;
			}
		});
	}

	public void delete(CmDeployRunFlag cmDeployRunFlag) {
		dao.execute("delete from CmDeployRunFlag");
	}

	public void update(final CmDeployRunFlag cmDeployRunFlag) {
		final String sql = "update cm_deploy_runflag set updatetime=?, updatecont=?, updateflag=?, updatelog=?, planid=?, dbcncount=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				Date updateTime = cmDeployRunFlag.getUpdateTime();
				ps.setTimestamp(1, null != updateTime ? new Timestamp(updateTime.getTime()) : null);
				ps.setString(2, cmDeployRunFlag.getUpdateCont());
				ps.setString(3, cmDeployRunFlag.getUpdateFlag());
				ps.setString(4, cmDeployRunFlag.getUpdateLog());
				ps.setObject(5, cmDeployRunFlag.getPlanId());
				ps.setObject(6, cmDeployRunFlag.getDbcnCount());
				return ps;
			}
		});
	}

	public CmDeployRunFlag get(String id) {
		return dao.get(CmDeployRunFlag.class, id);
	}
	
	public int getRowCount(Integer planId, String... updateFlags) {
		DetachedCriteria dc = DetachedCriteria.forClass(CmDeployRunFlag.class);
		if(null != planId) {
			dc.add(Restrictions.eq("planId", planId));
		}
		if(null != updateFlags && updateFlags.length > 0) {
			dc.add(Restrictions.in("updateFlag", updateFlags));
		}
		return dao.getRowCount(dc).intValue();
	}

	public List<CmDeployRunFlag> findAll() {
		return dao.findAll(CmDeployRunFlag.class);
	}

	public int getRowCount(String... updateFlags) {
		DetachedCriteria dc = DetachedCriteria.forClass(CmDeployRunFlag.class);
		if (null != updateFlags && updateFlags.length > 0) {
			dc.add(Restrictions.in("updateFlag", updateFlags));
		}
		return dao.getRowCount(dc).intValue();
	}

}
