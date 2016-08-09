package CMS.DAO.impl;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


import CMS.DAO.EnvironmentDAO;
import CMS.mapper.EnvironmentMapper;
import CMS.model.Environment;

/**
 * 
 * @author apande
 *
 */
public class EnvironmentJDBCTemplate implements EnvironmentDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJDBCTemplate() {
		return this.jdbcTemplateObject;
	}

	@Override
	public Environment getEnvironment(String environementID) {
		String SQL = "select * from test.Environment where EnvironmentID = ?";
		try {
			Environment env = jdbcTemplateObject.queryForObject(SQL, new Object[] { environementID },
					new EnvironmentMapper());
			return env;
		} catch (Exception ex) {
			return null;
		}

	}

	@Override
	@Transactional
	public String createEnvironment(String environmentName) {
		try {
			String SQL = "insert into test.Environment (EnvironmentID, EnvironmentName) " + "values (?, ?)";

			// check if environment already exists
			String uuid = UUID.randomUUID().toString();

			// consider first 20 char
			String environmentID = uuid.substring(0, 20);
			Environment env = getEnvironment(environmentID);
			
			if (env != null)
				return new String(" Try PUT API. Environment already exists for " + env);

			jdbcTemplateObject.update(SQL, environmentID, environmentName);
			String result = "Created environment with environmentID = " + environmentID + " environmentName = "
					+ environmentName;
			return result;
		} catch (Exception ex) {
			return "Exception while creating environment " + ex.getMessage();
		}

	}

	@Override
	@Transactional
	public String deleteEnvironment(String env_id) {
		String SQL = "delete from test.Environment where EnvironmentID = ?";
		try {
			jdbcTemplateObject.update(SQL, env_id);
			
			// Delete configuration relevant to environment too
			SQL = "delete from test.Configuration where EnvironmentID = ?";
			jdbcTemplateObject.update(SQL, env_id);
			
			return new String(" Deleted environemnt with envId = " + env_id);
		} catch (Exception ex) {
			return new String("Error in deleting environemnt with envId = " + env_id);
		}
	}

	@Override
	@Transactional
	public String updateEnvironment(String env_id, String env_name) {
		String SQL = "update test.Environment set EnvironmentName = ? where EnvironmentID = ?";
		try {
			jdbcTemplateObject.update(SQL, env_name, env_id);
			return new String(" Updated environment with envId = " + env_id);
		} catch (Exception ex) {
			return new String("Error in updating environment with envId = " + env_id);
		}
	}

}
