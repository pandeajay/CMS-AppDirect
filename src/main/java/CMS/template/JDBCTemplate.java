package CMS.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import CMS.DAO.impl.ApplicationJDBCTemplate;
import CMS.DAO.impl.ConfigJDBCTemplate;
import CMS.DAO.impl.EnvironmentJDBCTemplate;

/**
 * 
 * @author apande
 *
 */
public class JDBCTemplate {
	
	public static ConfigJDBCTemplate getConfigJDBCTemplate(){
		ApplicationContext context = 
	             new FileSystemXmlApplicationContext("C:\\demo2\\demo2\\beans.xml");
		ConfigJDBCTemplate configJDBCTemplate = 
	      (ConfigJDBCTemplate)context.getBean("configJDBCTemplate");	      
	      return configJDBCTemplate;
	}
	
	public static ApplicationJDBCTemplate getApplicationJDBCTemplate(){
		ApplicationContext context = 
	             new FileSystemXmlApplicationContext("C:\\demo2\\demo2\\beans.xml");
		ApplicationJDBCTemplate applicationJDBCTemplate = 
	      (ApplicationJDBCTemplate)context.getBean("applicationJDBCTemplate");	      
	      return applicationJDBCTemplate;
	}
	
	public static EnvironmentJDBCTemplate getEnvironmentJDBCTemplate(){
		ApplicationContext context = 
	             new FileSystemXmlApplicationContext("C:\\demo2\\demo2\\beans.xml");
		EnvironmentJDBCTemplate environmentJDBCTemplate = 
	      (EnvironmentJDBCTemplate)context.getBean("environmentJDBCTemplate");	      
	      return environmentJDBCTemplate;
	}
}
