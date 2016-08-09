package CMS.DAO;

import CMS.model.Application;

/**
 * Represent Application DATA Access interface
 * @author apande
 *
 */
public interface ApplicationDAO {
	Application getApplication(String applicationId);
	String createApplication(String applicationName);
	String deleteApplication(String appId);
	String updateApplication(String app_id, String app_name);
}
