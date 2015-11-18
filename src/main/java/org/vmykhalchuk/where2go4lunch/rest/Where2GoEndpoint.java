package org.vmykhalchuk.where2go4lunch.rest;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vmykhalchuk.where2go4lunch.rest.RestaurantEndpoint.LunchMenuDto;
import org.vmykhalchuk.where2go4lunch.service.VotingService;
import org.vmykhalchuk.where2go4lunch.service.VotingService.VotingResults;

@Service
@Path("/where2go")
@Secured("ROLE_USER")
@Transactional(readOnly = true)
@Produces("application/json")
public class Where2GoEndpoint {

	@Autowired
	private VotingService votingService;

	@GET
	public VotingResults getVotingResults() {
		return votingService.getTodayWinningRestaurants();
	}

	@GET
	@Path("/one")
	public LunchMenuDto getOnlyOneVotingResults() throws RestException {
		VotingResults res = votingService.getTodayWinningRestaurants();
		int size = res.getTopSelection().size();
		if (size == 0) {
			throw new RestException("No votes available for today!");
		} else if (size == 1) {
			return res.getTopSelection().get(0);
		} else {
			Random random = new Random(System.currentTimeMillis() / 2);
			return res.getTopSelection().get(random.nextInt(size));
		}
	}
	
}
