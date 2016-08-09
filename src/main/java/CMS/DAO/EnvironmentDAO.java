package CMS.DAO;

import CMS.model.Environment;

/**
 * DAO interface for Environment
 * @author apande
 *
 */
public interface EnvironmentDAO {

	Environment getEnvironment(String environementID);
	String createEnvironment(String environmentName);
	String deleteEnvironment(String env_id);
	Object updateEnvironment(String env_id, String env_name);
}
