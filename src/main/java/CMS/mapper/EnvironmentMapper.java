package CMS.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import CMS.model.Environment;


/**
 * 
 * @author apande
 *
 */
public class EnvironmentMapper implements RowMapper<Environment> {

	public Environment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Environment environment = new Environment();
		environment.setEnvironmentID(rs.getString("EnvironmentID"));
		environment.setEnvironmentName(rs.getString("EnvironmentName"));
		return environment;
	}
}
