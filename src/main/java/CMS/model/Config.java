package CMS.model;


/**
 * Configuration details class
 * In reality configuration association with application and environment should be stored differently 
 * So following class is just for prototype
 * 
 * @author apande
 *
 */
public class Config {
	
	private String applicationId;
	private String environmentId;
	private String levelId;
	private String configurationName;
	private String configurationValue;

	@Override
	public String toString() {
		return "Config [applicationId=" + applicationId + ", environmentId=" + environmentId + ", levelId=" + levelId
				+ ", configurationName=" + configurationName + ", configurationValue=" + configurationValue + "]";
	}

	/**
	 * 
	 * @return
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * 
	 * @param applicationId
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * 
	 * @return
	 */
	public String getEnvironmentId() {
		return environmentId;
	}

	/**
	 * 
	 * @param environmentId
	 */
	public void setEnvironmentId(String environmentId) {
		this.environmentId = environmentId;
	}

	/**
	 * 
	 * @return
	 */
	public String getConfigurationName() {
		return configurationName;
	}

	/**
	 * 
	 * @param configurationName
	 */
	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	/**
	 * 
	 * @return
	 */
	public String getConfigurationValue() {
		return configurationValue;
	}

	/**
	 * 
	 * @param configurationValue
	 */
	public void setConfigurationValue(String configurationValue) {
		this.configurationValue = configurationValue;
	}

	/**
	 * 
	 * @return
	 */
	public String getLevelId() {
		return levelId;
	}

	/**
	 * 
	 * @param levelId
	 */
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

}
