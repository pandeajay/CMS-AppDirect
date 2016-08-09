package CMS.DAO.impl;

import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import CMS.DAO.ApplicationDAO;
import CMS.mapper.ApplicationMapper;
import CMS.model.Application;

/**
 * DAO implementation for Application
 * @author apande
 *
 */
public class ApplicationJDBCTemplate implements ApplicationDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJDBCTemplate() {
		return this.jdbcTemplateObject;
	}

	/**
	 * Get Application form ApplicationID
	 */
	@Override
	public Application getApplication(String applicationId) {
		String SQL = "select * from test.Application where ApplicationID = ?";
		try {
			Application app = jdbcTemplateObject.queryForObject(SQL, new Object[] { applicationId },
					new ApplicationMapper());
			return app;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * Creates Application
	 */
	@Override
	@Transactional
	public String createApplication(String applicationName) {
		try {
			String SQL = "insert into test.Application (ApplicationID, ApplicationName) " + "values (?, ?)";

			// check if application already exists
			String uuid = UUID.randomUUID().toString();

			// consider first 20 char
			String applicationId = uuid.substring(0, 20);
			Application app = getApplication(applicationId);

			if (app != null)
				return new String(" Try PUT API. Application already exists for " + app);

			jdbcTemplateObject.update(SQL, applicationId, applicationName);

			String result = "Created application with applicationid = " + applicationId + " applicatioName = "
					+ applicationName;
			return result;
		} catch (Exception ex) {
			return "Exception while creating application " + ex.getMessage();
		}

	}

	/**
	 * Deletes Application
	 */
	@Override
	@Transactional
	public String deleteApplication(String appId) {
		String SQL = "delete from test.Application where ApplicationID = ?";
		try {
			jdbcTemplateObject.update(SQL, appId);

			// Delete configuration relevant to environment too
			SQL = "delete from test.Configuration where ApplicationID = ?";
			jdbcTemplateObject.update(SQL, appId);
			return new String(" Deleted application with envId = " + appId);
		} catch (Exception ex) {
			return new String("Error in deleting application with envId = " + appId);
		}
	}

	/**
	 * updates application information
	 */
	@Override
	@Transactional
	public String updateApplication(String app_id, String app_name) {
		String SQL = "update test.Application set ApplicationName = ? where ApplicationID = ?";
		try {
			jdbcTemplateObject.update(SQL, app_name, app_id);
			return new String(" Updated application with envId = " + app_id);
		} catch (Exception ex) {
			return new String("Error in updating application with appId = " + app_id);
		}
	}

}
