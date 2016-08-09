package CMS.DAO.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import CMS.DAO.ConfigDAO;
import CMS.model.Config;

/**
 * 
 * @author apande
 *
 */
public class ConfigJDBCTemplate implements ConfigDAO {
	private int maxLevels = 3;
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
	 * 
	 */
	@Transactional
	public String createConfiguration(String applicationId, String environmentId, String levelId,
			String configurationName, String configurationValue) {
		try {
			String SQL = "insert into test.Configuration (ApplicationID, EnvironmentID, LevelID, ConfigurationName, ConfigurationValue) "
					+ "values (?, ?, ?, ?, ?)";
			// check if configuration already exists
			List<Config> configs = getConfiguration(applicationId, environmentId, levelId, configurationName);

			if (configs != null && configs.size() > 0)
				return new String(" Try PUT API. Configuration already exists for " + configs);

			//create configuration
			jdbcTemplateObject.update(SQL, applicationId, environmentId, levelId, configurationName,
					configurationValue);

			String result = "";

			// also add for inherited levels till maxlevels
			//in reality this step may be limited to existing levels in DB than maxlevels
			for (int i = Integer.parseInt(levelId) + 1; i <= maxLevels; ++i) {
				jdbcTemplateObject.update(SQL, applicationId, environmentId, i, configurationName, configurationValue);
				result += "Created Config  app id " + applicationId + " environmentId = " + environmentId
						+ " levelId = " + i + " configurationName = " + configurationName + " configurationValue = "
						+ configurationValue + System.getProperty("line.separator");
			}

			result += "Created Config  app id " + applicationId + " environmentId = " + environmentId + " levelId = "
					+ levelId + " configurationName = " + configurationName + " configurationValue = "
					+ configurationValue + System.getProperty("line.separator");

			return result;
		} catch (Exception ex) {
			return "Exception while creating config " + ex.getMessage();
		}

	}

	public String getConfigValue(String applicationId, String env_id, String level_id, String config_name) {
		List<Config> config = getConfiguration(applicationId, env_id, level_id, config_name);
		if (config.size() > 0) {
			return config.get(0).getConfigurationValue();
		} else {
			return new String("No value found for configuration " + config_name);
		}

	}
/**
 * 
 */
	public List<Config> getConfiguration(String applicationId, String env_id, String level_id, String config_name) {
		List<Config> resultConfig = new ArrayList<Config>();
		String SQL = "select * from test.Configuration ";

		if (applicationId != null && applicationId.length() > 0) {
			SQL += "where ApplicationID = \"" + applicationId + "\"";
		}

		if (env_id != null && env_id.length() > 0) {
			SQL += " AND " + "EnvironmentID = \"" + env_id + "\"";
		}

		if (level_id != null && level_id.length() > 0) {
			SQL += " AND " + "LevelID = \"" + level_id + "\"";
		}

		if (config_name != null && config_name.length() > 0) {
			SQL += " AND " + "ConfigurationName = \"" + config_name + "\"";
		}

		List<Map<String, Object>> configurationMap = jdbcTemplateObject.queryForList(SQL);

		if (configurationMap.size() > 0) {
			for (Map<String, Object> tempRow : configurationMap) {
				Config tmpConfig = new Config();
				tmpConfig.setApplicationId("" + tempRow.get("ApplicationID"));
				tmpConfig.setEnvironmentId((String) tempRow.get("EnvironmentID"));
				tmpConfig.setLevelId((String) tempRow.get("LevelID"));
				tmpConfig.setConfigurationName((String) tempRow.get("ConfigurationName"));
				tmpConfig.setConfigurationValue((String) tempRow.get("ConfigurationValue"));
				resultConfig.add(tmpConfig);
			}
		}
		return resultConfig;
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public String updateConfigurationAsTransaction(String applicationId, String environmentId, String levelId,
			String configurationName, String ConfigurationValue) {
		try {
			// ASSUMPTION: for simplicity assume that level 3 always inherits
			// from level 2 and level 2 always inherits from level 1
			// Though in reality this may not be true. In reality one should
			// take level from which other level inherit and
			// accordingly one should implement

			// check if configuration exists
			List<Config> updateConfigurations = new ArrayList<Config>();

			// Get all Hierarchical objects for modifications
			for (int i = Integer.parseInt(levelId); i <= maxLevels; i++) {
				List<Config> configs = getConfiguration(applicationId, environmentId, new String("" + i),
						configurationName);
				if (configs != null) {
					updateConfigurations.addAll(configs);
				}
			}

			if (updateConfigurations.size() == 0) {
				return new String("Desired configuration was not found in DB for for ApplicationId = " + applicationId
						+ " environmentId = " + environmentId + " levelId = " + levelId + " configurationName = "
						+ configurationName);
			}

			// Check that hierarchical data is in consistent state
			if (updateConfigurations.size() != maxLevels - Integer.parseInt(levelId) + 1) {
				return new String("Data is not in consistent state for ApplicationId = " + applicationId
						+ " environmentId = " + environmentId + " levelId = " + levelId + " configurationName = "
						+ configurationName);
			}

			String SQL = "update test.Configuration set ConfigurationValue = ? where ApplicationID = ? and EnvironmentID = ? and "
					+ "LevelID = ? and ConfigurationName = ? ";

			//update all configurations at all levels
			for (Config config : updateConfigurations) {
				jdbcTemplateObject.update(SQL, ConfigurationValue, config.getApplicationId(), config.getEnvironmentId(),
						config.getLevelId(), config.getConfigurationName());
			}

			return new String("All Updated for ApplicationId = " + applicationId + " environmentId = " + environmentId
					+ " levelId = " + levelId + " configurationName = " + configurationName);
		} catch (Exception ex) {
			return new String(" Could not Updated for ApplicationId = " + applicationId + " environmentId = "
					+ environmentId + " levelId = " + levelId + " configurationName = " + configurationName);

		}
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public String deleteConfiguration(String applicationId, String environmentId, String levelId,
			String configurationName) {
		// TODO Auto-generated method stub
		try {

			String SQL = "delete from test.Configuration where ApplicationID = ? and EnvironmentID = ? and "
					+ "LevelID = ? and ConfigurationName = ? ";
			
			// hierarchical delete
			for (int i = Integer.parseInt(levelId); i <= maxLevels; ++i) {
				jdbcTemplateObject.update(SQL, applicationId, environmentId, i, configurationName);
			}

			return new String("Deleted for ApplicationId = " + applicationId + " environmentId = " + environmentId
					+ " levelId = " + levelId + " configurationName =" + configurationName);
		} catch (Exception ex) {
			return new String("Could not delete for ApplicationId = " + applicationId + " environmentId = "
					+ environmentId + " levelId = " + levelId + " configurationName =" + configurationName + " because "
					+ ex.getMessage());
		}

	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public String deleteAllConfigurations(String applicationId, String environmentId, String levelId) {

		try {
			String SQL = "delete from test.Configuration where ApplicationID = ? and EnvironmentID = ? and "
					+ "LevelID = ?";
			jdbcTemplateObject.update(SQL, applicationId, environmentId, levelId);
			String result = "Deleted all configurations for ApplicationId = " + applicationId + " environmentId = "
					+ environmentId + " levelId = " + levelId;
			return result;
		} catch (Exception ex) {
			return new String("Could not delete for ApplicationId = " + applicationId + " environmentId = "
					+ environmentId + " levelId = " + levelId + " Reason ::: " + ex.getMessage());
		}

	}

}
