package org.vmykhalchuk.where2go4lunch.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vmykhalchuk.where2go4lunch.dao.LunchMenuRepository;
import org.vmykhalchuk.where2go4lunch.dao.RestaurantRepository;
import org.vmykhalchuk.where2go4lunch.dao.UserRepository;
import org.vmykhalchuk.where2go4lunch.model.LunchMenu;
import org.vmykhalchuk.where2go4lunch.model.Restaurant;
import org.vmykhalchuk.where2go4lunch.model.User;
import org.vmykhalchuk.where2go4lunch.service.VoteTimeTooLateException;
import org.vmykhalchuk.where2go4lunch.service.VotingService;

@Service
@Path("/restaurant")
@Secured("ROLE_ADMIN")
@Transactional
@Produces("application/json")
public class RestaurantEndpoint {

	public static class RestaurantDto {
		public Long id;
		public String name;
		public List<LunchMenuDto> menu;

		public static RestaurantDto fromRestaurant(Restaurant rest, boolean initLunchMenu) {
			RestaurantDto dto = new RestaurantDto();
			dto.id = rest.getId();
			dto.name = rest.getName();
			if (initLunchMenu) {
				dto.menu = new ArrayList<>();
				for (LunchMenu lm : rest.getLunchMenuesList()) {
					dto.menu.add(LunchMenuDto.fromLunchMenu(lm));
				}
			}
			return dto;
		}
	}

	public static class LunchMenuDto {
		public Long id;
		public String price;
		public List<String> dishes;

		public static LunchMenuDto fromLunchMenu(LunchMenu lm) {
			LunchMenuDto dto = new LunchMenuDto();
			dto.id = lm.getId();
			dto.price = lm.getPrice();
			dto.dishes = lm.getDishes();
			return dto;
		}
	}

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private LunchMenuRepository lunchMenuRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private VotingService votingService;

	@GET
	@Secured("ROLE_USER")
	public List<RestaurantDto> findAll() {
		List<RestaurantDto> resList = new ArrayList<>();
		for (Restaurant r : restaurantRepo.findAll()) {
			resList.add(RestaurantDto.fromRestaurant(r, false));
		}
		return resList;
	}

	@GET
	@Path("/{restaurantId}")
	@Secured("ROLE_USER")
	public RestaurantDto findById(@PathParam("restaurantId") long restaurantId) {
		Restaurant restaurant = restaurantRepo.findOne(restaurantId);
		if (restaurant != null) {
			return RestaurantDto.fromRestaurant(restaurant, true);
		} else {
			return null;
		}
	}

	@POST
	@Consumes("application/json")
	public RestaurantDto add(Restaurant restaurant) {
		return RestaurantDto.fromRestaurant(restaurantRepo.save(restaurant), true);
	}

	@DELETE
	@Path("/{restaurantId}")
	public Boolean deleteRestaurant(@PathParam("restaurantId") long restaurantId) throws RestBadRequestException {
		try {
			// FIXME extend repository to cascade delete Menu
			// FIXME It should check if we have no voting for this restaurant for today!
			restaurantRepo.delete(restaurantId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			throw new RestBadRequestException("No restaurant: " + restaurantId + " found!");
		}
	}

	@POST
	@Consumes("application/json")
	@Path("/{restaurantId}/menu")
	public LunchMenuDto addLunchMenu(@PathParam("restaurantId") long restaurantId, LunchMenu lunchMenu) {
		Restaurant rest = restaurantRepo.findOne(restaurantId);
		lunchMenu.setRestaurant(rest);
		return LunchMenuDto.fromLunchMenu(lunchMenuRepo.save(lunchMenu));
	}

	@DELETE
	@Path("/{restaurantId}/menu/{lunchMenuId}")
	public Boolean deleteLunchMenu(@PathParam("restaurantId") long restaurantId,
			@PathParam("lunchMenuId") long lunchMenuId) throws RestBadRequestException {
		try {
			lunchMenuRepo.delete(lunchMenuId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			throw new RestBadRequestException("No restaurant: " + restaurantId + " found!");
		}
	}

	@GET
	@Path("/{restaurantId}/menu/{lunchMenuId}/giveVote")
	@Secured("ROLE_USER")
	public Boolean makeAVote(@PathParam("restaurantId") long restaurantId, @PathParam("lunchMenuId") long lunchMenuId)
			throws RestBadRequestException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal instanceof User ? (User) principal : null;
		user = userRepo.findOne(user.getUsername());
		try {
			votingService.makeVote(user, lunchMenuId);
		} catch (VoteTimeTooLateException e) {
			throw new RestBadRequestException(e.getMessage());
		}
		return true;
	}
}
