package CMS.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import CMS.model.User;
import CMS.utilities.Utilities;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class OpenIdServiceImpl
		implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	@Value("${spring.user.name}")
	private String username;
	@Value("${spring.user.password}")
	private String password;

	

	/**
	 * Depending on the business logic, the user could be loaded here from
	 * database, update, etc... For the test, just compose one user and return.
	 * Because here H2 is used, the users are lost between restarts,
	 * unpredictable results in this case. For that the default user is loaded.
	 * 
	 * @param token
	 * @return 
	 * @return 
	 * @return
	 * @throws UsernameNotFoundException
	 */

	@Override
	public  String loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
		Optional<String> userOptional = Optional.ofNullable(Utilities.getApplication(token.getIdentityUrl()));
		return userOptional.get();
	}


}
