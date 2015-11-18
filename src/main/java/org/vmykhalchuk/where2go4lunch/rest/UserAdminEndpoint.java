package org.vmykhalchuk.where2go4lunch.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vmykhalchuk.where2go4lunch.dao.UserRepository;
import org.vmykhalchuk.where2go4lunch.model.User;

@Service
@Path("/users")
@Secured("ROLE_ADMIN")
@Transactional
@Produces("application/json")
public class UserAdminEndpoint {

	@Autowired
	private UserRepository userRepo;

	private User getWithoutPassword(User user) {
		user = user.clone();
		user.setPassword(null);
		return user;
	}

	@POST
	@Consumes("application/json")
	public User addUser(User userData) {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		User user = new User(userData.getUsername(), userData.getPassword(), roles);
		user.setFirstname(userData.getFirstname());
		user.setLastname(userData.getLastname());
		user = userRepo.save(user);
		return getWithoutPassword(user);
	}

	@GET
	@Path("/{username}")
	public User getUser(@PathParam("username") String username) {
		User user = userRepo.findOne(username);
		return user == null ? null : getWithoutPassword(user);
	}

	@DELETE
	@Path("/{username}")
	public Boolean deleteUser(@PathParam("username") String username) throws RestBadRequestException {
		try {
			userRepo.delete(username);
			return true;
		} catch (EmptyResultDataAccessException e) {
			throw new RestBadRequestException("No user with username: " + username + " found!");
		}
	}

	@PUT
	@Consumes("application/json")
	@Path("/{username}")
	@Secured("ROLE_USER")
	public User updateUser(@PathParam("username") String username, User userData) {
		User user = userRepo.findOne(username);
		if (userData.getFirstname() != null) {
			user.setFirstname(userData.getFirstname());
		}
		if (userData.getLastname() != null) {
			user.setLastname(userData.getLastname());
		}
		return user;
	}

	public static class PasswordDto {
		public String oldPassword;
		public String newPassword;
	}

	@POST
	@Consumes("application/json")
	@Path("/{username}/password")
	@Secured("ROLE_USER")
	public void changePassword(@PathParam("username") String username, PasswordDto passwordDto)
			throws RestBadRequestException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal instanceof User ? (User) principal : null;
		if (user == null) {
			throw new RestBadRequestException("username is invalid!");
		}
		if (!user.getUsername().equals(username)) {
			throw new RestBadRequestException("You can change only own password!");
		}
		user = userRepo.findOne(username);

		if (!user.getPassword().equals(passwordDto.oldPassword)) {
			throw new RestBadRequestException("Old Password is invalid!");
		}
		user.setPassword(passwordDto.newPassword);
	}
	
}
