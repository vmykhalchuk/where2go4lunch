package org.vmykhalchuk.where2go4lunch.security;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vmykhalchuk.where2go4lunch.dao.UserRepository;
import org.vmykhalchuk.where2go4lunch.model.User;

@Service
public class AuthenticationService implements UserDetailsService {

	private static final Logger log = Logger.getLogger(AuthenticationService.class.getName());

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		log.finest("username: " + username);
		User user = repo.findOne(username);
		if (user != null) {
			return user;
		} else {
			String msg = "No user '" + username + "' found!";
			throw new UsernameNotFoundException(msg);
		}
	}
}
