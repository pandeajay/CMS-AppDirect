package CMS.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import CMS.DAO.impl.ApplicationJDBCTemplate;
import CMS.DAO.impl.ConfigJDBCTemplate;
import CMS.DAO.impl.EnvironmentJDBCTemplate;
import CMS.model.Application;
import CMS.model.Config;
import CMS.model.Environment;
import CMS.template.JDBCTemplate;

import java.util.Map.Entry;

/**
 * Utility class for CMS application
 * @author apande
 *
 */
public class Utilities {

	public static ConfigJDBCTemplate getConfigJDBCTemplate() {
		return JDBCTemplate.getConfigJDBCTemplate();
	}

	public static ApplicationJDBCTemplate getApplicationJDBCTemplate() {
		return JDBCTemplate.getApplicationJDBCTemplate();
	}

	public static EnvironmentJDBCTemplate getEnvironmentJDBCTemplate() {
		return JDBCTemplate.getEnvironmentJDBCTemplate();
	}

	public static String createConfig(String payload, String app_id, String env_id, String level_id) {
		String result = "";
		try {
			String separator = "&";
			ConfigJDBCTemplate configJDBCTemplate = Utilities.getConfigJDBCTemplate();
			List<String> myList = new ArrayList<String>();
			myList = Arrays.asList(payload.split(separator));
			Map<String, String> configMap = new HashMap<String, String>();

			for (String element : myList) {
				List<String> configList = new ArrayList<String>();
				configList = Arrays.asList(element.split("="));
				configMap.put(configList.get(0), configList.get(1));
			}
			Set<Entry<String, String>> entrySet = configMap.entrySet();

			for (Entry<String, String> entry : entrySet) {
				result = result + System.lineSeparator() + configJDBCTemplate.createConfiguration(app_id, env_id,
						level_id, entry.getKey(), entry.getValue());
			}

		} catch (Exception ex) {
			return new String("Error in creating configuration : " + ex.getMessage());
		}
		return result;
	}

	public static String getConfig(String app_id, String env_id, String level_id, String config_name) {
		String result = "";
		try {
			ConfigJDBCTemplate configJDBCTemplate = Utilities.getConfigJDBCTemplate();

			if (config_name != null && config_name.length() > 0)
				return configJDBCTemplate.getConfigValue(app_id, env_id, level_id, config_name);

			List<Config> configs = configJDBCTemplate.getConfiguration(app_id, env_id, level_id, "");
			for (Config config : configs) {
				String str = " Application id = " + config.getApplicationId() + " Env_id = " + config.getEnvironmentId()
						+ " Level id = " + config.getLevelId() + " Config name = " + config.getConfigurationName()
						+ " Configuration value = " + config.getConfigurationValue();
				result = result + System.lineSeparator() + str;
			}
		} catch (Exception ex) {
			return new String("Error in getConfig  for appd_id = " + app_id + "   : " + ex.getMessage());
		}
		return result;
	}

	private static String updateSpecificConfiguration(String payload, String app_id, String env_id, String level_id,
			String config_id) {
		String result = "";
		try {
			ConfigJDBCTemplate configJDBCTemplate = Utilities.getConfigJDBCTemplate();

			result = result + System.lineSeparator()
					+ configJDBCTemplate.updateConfigurationAsTransaction(app_id, env_id, level_id, config_id, payload);
		} catch (Exception ex) {
			return new String("Error in updating configuration with config_id : " + config_id + " reasosn :: " + ex.getMessage());
		}
		return result;

	}

	private static String updateAllConfigurations(String payload, String app_id, String env_id, String level_id) {
		String result = "";
		try {
			String separator = "&";
			ConfigJDBCTemplate configJDBCTemplate = Utilities.getConfigJDBCTemplate();
			List<String> myList = new ArrayList<String>();
			myList = Arrays.asList(payload.split(separator));
			Map<String, String> configMap = new HashMap<String, String>();

			for (String element : myList) {
				List<String> configList = new ArrayList<String>();
				configList = Arrays.asList(element.split("="));
				configMap.put(configList.get(0), configList.get(1));
			}
			Set<Entry<String, String>> entrySet = configMap.entrySet();

			for (Entry<String, String> entry : entrySet) {
				result = result + System.lineSeparator() + configJDBCTemplate.updateConfigurationAsTransaction(app_id,
						env_id, level_id, entry.getKey(), entry.getValue());
			}

		} catch (Exception ex) {
			return new String("Error in putConfig configuration : " + ex.getMessage());
		}
		return result;

	}

	public static String putConfig(String payload, String app_id, String env_id, String level_id, String config_id) {
		String result = "";
		try {
			if (config_id != null && config_id.length() > 0) {
				result = updateSpecificConfiguration(payload, app_id, env_id, level_id, config_id);
			} else {
				result = updateAllConfigurations(payload, app_id, env_id, level_id);
			}

		} catch (Exception ex) {
			return new String("Error in putConfig configuration : " + ex.getMessage());
		}
		return result;
	}

	public static String deleteConfig(String payload, String app_id, String env_id, String level_id, String config_name) {
		String result = "";
		try {
			ConfigJDBCTemplate configJDBCTemplate = Utilities.getConfigJDBCTemplate();
			List<String> myList = new ArrayList<String>();
			
			if(payload != null && payload.length() > 0){
				myList = Arrays.asList(payload.split(System.getProperty("line.separator")));
			}

			if (config_name != null && config_name.length() > 0) {
				return result + System.lineSeparator()
						+ configJDBCTemplate.deleteConfiguration(app_id, env_id, level_id, config_name);
			} else if (myList.size() > 0) {
				for (String configName : myList) {
					result = result + System.lineSeparator()
							+ configJDBCTemplate.deleteConfiguration(app_id, env_id, level_id, configName);
				}

			}else{
				//Delete all configurations
				return configJDBCTemplate.deleteAllConfigurations(app_id, env_id, level_id);
			}

		} catch (Exception ex) {
			return new String("Error in putConfig configuration : " + ex.getMessage());
		}
		return result;
	}

	public static String getApplication(String appID) {
		Application app = Utilities.getApplicationJDBCTemplate().getApplication(appID);
		return app != null ? app.toString() : new String("Application not found for appid " + appID);
	}

	public static String createApplication(String appName) {
		return Utilities.getApplicationJDBCTemplate().createApplication(appName).toString();
	}

	public static String updateApplication(String app_id, String app_name) {
		return Utilities.getApplicationJDBCTemplate().updateApplication(app_id, app_name).toString();
	}
	
	public static String deleteApplication(String app_id) {
		return Utilities.getApplicationJDBCTemplate().deleteApplication(app_id).toString();
	}

	public static String getEnvironment(String envID) {
		Environment env = Utilities.getEnvironmentJDBCTemplate().getEnvironment(envID);
		return env != null ? env.toString() : new String("Environment not found for envID " + envID);
	}

	public static String createEnvironment(String envName) {
		return Utilities.getEnvironmentJDBCTemplate().createEnvironment(envName).toString();
	}

	public static String deleteEnvironment(String env_id) {
		return Utilities.getEnvironmentJDBCTemplate().deleteEnvironment(env_id).toString();
	}

	public static String updateEnvironment(String env_id, String env_name) {
		return Utilities.getEnvironmentJDBCTemplate().updateEnvironment(env_id, env_name).toString();
	}


	


}
