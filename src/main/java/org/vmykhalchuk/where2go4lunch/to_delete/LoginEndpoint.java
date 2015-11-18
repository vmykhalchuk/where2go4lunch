package org.vmykhalchuk.where2go4lunch.to_delete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vmykhalchuk.where2go4lunch.dao.UserRepository;

//@Service
//@Path("/auth")
//@Transactional
public class LoginEndpoint {

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = true)
	public void login(LoginRequestDto loginRequest, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null && !session.isNew()) {
			session.invalidate();
		}
		request.getSession(true);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
		String errorMessage = null;
		try {
			SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(token));
		} catch (AuthenticationException e) {
			// TODO make more meaningful explanation here (pass info that email is not verified, etc)
			errorMessage = e.getMessage();
			e.printStackTrace();
		}
		/*AuthenticationInfoDto res = checkAuthentication();
		res.setErrorMessage(errorMessage);
		return res;*/
	}

	@Path("/logout")
	@GET
	@Transactional(readOnly = true)
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
