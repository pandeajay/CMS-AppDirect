package CMS.DAO;

import java.util.List;
import javax.sql.DataSource;

import CMS.model.Config;


/**
 * 
 * @author apande
 *
 */
public interface ConfigDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the Configuration table.
	 */
	public String createConfiguration(String applicationId, String environmentId, String levelId,
			String configurationName, String configurationValue);

	/**
	 * This is the method to be used to list down a record from the Configuration
	 * table corresponding to a passed parameters .
	 */
	public List<Config> getConfiguration(String applicationId, String environmentId, String levelId,
			String configurationName);

	/**
	 * This is the method to be used to delete a record from the Student table
	 * corresponding to a passed student id.
	 */
	public String deleteConfiguration(String applicationId, String environmentId, String levelId, String configurationName);
	
	/**
	 * This is the method to be used to update a record into the Student table.
	 */

	String updateConfigurationAsTransaction(String applicationId, String environmentId, String levelId,
			String configurationName, String ConfigurationValue);


	String deleteAllConfigurations(String applicationId, String environmentId, String levelId);
}
