package org.vmykhalchuk.where2go4lunch.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Produces("application/json")
@Path("/403")
public class Http403Endpoint {

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
	
	@GET
	public InitResDto http403() {
		return new InitResDto(false, "Access Denied");
	}
}
