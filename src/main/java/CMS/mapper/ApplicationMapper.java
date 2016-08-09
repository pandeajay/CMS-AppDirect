package CMS.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import CMS.model.Application;


/**
 * DB Row Mapping class for Application 
 * @author apande
 *
 */
public class ApplicationMapper implements RowMapper<Application> {

	public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
		Application appliction = new Application();
		appliction.setApplicationId(rs.getString("ApplicationID"));
		appliction.setApplictionName(rs.getString("ApplicationName"));
		return appliction;
	}

}
