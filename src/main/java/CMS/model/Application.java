package CMS.model;

/**
 * 
 * @author apande
 *
 */
public class Application {
	
	private String applicationId;
	private String applictionName;
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplictionName() {
		return applictionName;
	}
	public void setApplictionName(String applictionName) {
		this.applictionName = applictionName;
	}
	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", applictionName=" + applictionName + "]";
	}


	

}
