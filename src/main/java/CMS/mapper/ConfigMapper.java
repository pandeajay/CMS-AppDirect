package CMS.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import CMS.model.Config;


/**
 * DB row mapper for Configuration
 * @author apande
 *
 */
public class ConfigMapper implements RowMapper<Config> {
   public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
      Config configuration = new Config();
      configuration.setApplicationId(rs.getString("ApplicationID"));
      configuration.setEnvironmentId(rs.getString("EnvironmentID"));
      configuration.setLevelId(rs.getString("LevelID"));
      configuration.setConfigurationName(rs.getString("ConfigurationName"));
      configuration.setConfigurationValue(rs.getString("ConfigurationValue"));
      return configuration;
   }
}
