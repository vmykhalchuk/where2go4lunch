package org.vmykhalchuk.where2go4lunch.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vmykhalchuk.where2go4lunch.dao.UserRepository;
import org.vmykhalchuk.where2go4lunch.model.User;

@Service
@Transactional
@Produces("application/json")
@Path("/login-init")
public class InitEndpoint {

	public static class InitResDto {
		public boolean success;
		public String msg;
		public InitResDto() {
		}
		public InitResDto(boolean success) {
			this.success = success;
		}
		public InitResDto(boolean success, String msg) {
			this(success);
			this.msg = msg;
		}
	}

	@Autowired
	private UserRepository userRepo;

	private static final Object LOCK = new Object();

	@GET
	@Path("/login-error")
	public InitResDto errorPage() {
		return new InitResDto(false, "Error happened!");
	}
	
	@GET
	public InitResDto init() {
		synchronized (LOCK) {
			if (userRepo.findOne("admin") == null) {
				List<String> rolesSet = new ArrayList<>();
				rolesSet.add("ROLE_ADMIN");
				rolesSet.add("ROLE_USER");

				User user = new User("admin", "admin", rolesSet);
				userRepo.save(user);

				return new InitResDto();
			} else {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
				InitResDto res = new InitResDto();
				res.success = false;
				res.msg = "Already initialized!";
				return res;
			}
		}
	}
}
