package org.vmykhalchuk.where2go4lunch.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.springframework.security.access.AccessDeniedException;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<RestException> {
	private static final Logger log = Logger.getLogger(ExceptionMapper.class.getName());

	@Override
	public Response toResponse(RestException exception) {
		log.info("Captured exception: " + exception.getMessage());
		exception.printStackTrace();

		Status status = null;
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("errorClass", exception.getClass());
		resMap.put("errorMessage", exception.getMessage());
		//resMap.put("error", exception);
		/*if (exception instanceof AccessDeniedException) {
			return Response.status(Status.FORBIDDEN).entity(resMap).build();
		} else*/ if (exception instanceof RestException) {
			status = ((RestException) exception).getStatusCode();
		}
		
		/*if (exception instanceof IRestException) {
			resMap.put("userErrorMessage", ((IRestException) exception).getUserMessage());
			status = ((IRestException) exception).getResponseStatus();
		} else {
			resMap.put("userErrorMessage", IRestException.DEFAULT_SERVER_MESSAGE);
			status = Status.INTERNAL_SERVER_ERROR;
		}*/
		if (status == null) {
			log.severe("Status cannot be null, check exception handling!");
			status = Status.INTERNAL_SERVER_ERROR;
		}
		return Response.status(status).entity(resMap).build();
	}

}
