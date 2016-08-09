package CMS.model;

/**
 * Environment information class
 * @author apande
 *
 */
public class Environment {
	private String environmentID;
	private String environmentName;
	
	@Override
	public String toString() {
		return "Environment [environmentID=" + environmentID + ", environmentName=" + environmentName + "]";
	}
	public String getEnvironmentID() {
		return environmentID;
	}
	public void setEnvironmentID(String environmentID) {
		this.environmentID = environmentID;
	}
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
	
	

	

}
