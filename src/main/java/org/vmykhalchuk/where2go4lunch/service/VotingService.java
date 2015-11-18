package org.vmykhalchuk.where2go4lunch.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vmykhalchuk.where2go4lunch.Application;
import org.vmykhalchuk.where2go4lunch.dao.LunchMenuRepository;
import org.vmykhalchuk.where2go4lunch.dao.UserRepository;
import org.vmykhalchuk.where2go4lunch.dao.VoteRepository;
import org.vmykhalchuk.where2go4lunch.model.LunchMenu;
import org.vmykhalchuk.where2go4lunch.model.User;
import org.vmykhalchuk.where2go4lunch.model.Vote;
import org.vmykhalchuk.where2go4lunch.rest.RestaurantEndpoint.LunchMenuDto;

@Component
public class VotingService {

	public static class VoteDto {
		public Long id;
		public String username;
		public LunchMenuDto lunchMenu;
		public long tmstmp;
		/**
		 * YYYYMMDD
		 */
		public int date;

		public static VoteDto fromVote(Vote vote) {
			VoteDto dto = new VoteDto();
			dto.id = vote.getId();
			dto.username = vote.getUser() == null ? null : vote.getUser().getUsername();
			dto.lunchMenu = LunchMenuDto.fromLunchMenu(vote.getLunchMenu());
			dto.tmstmp = vote.getTmstmp();
			dto.date = vote.getDate();
			return dto;
		}
	}

	public static int getDateByTmstmp(long tmstmp) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTimeInMillis(tmstmp);
		int year = gc.get(Calendar.YEAR);
		int month = gc.get(Calendar.MONTH);
		int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		return year * 10000 + month * 100 + dayOfMonth;
	}

	public static boolean isTmstmpLessThan(long tmstmp, int hour, int minute) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTimeInMillis(tmstmp);
		int _hour = gc.get(Calendar.HOUR_OF_DAY);
		if (_hour > hour) {
			return false;
		} else if (_hour < hour) {
			return true;
		}
		return gc.get(Calendar.MINUTE) <= minute;
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LunchMenuRepository lunchMenuRepository;

	@Autowired
	private VoteRepository voteRepository;

	private final static Object LOCK = new Object();

	public void makeVote(User user, long lunchMenuId) throws VoteTimeTooLateException {
		LunchMenu lunchMenu = lunchMenuRepository.findOne(lunchMenuId);
		if (lunchMenu == null) {
			throw new IllegalArgumentException("lunchMenuId is invalid!");
		}

		long tmstmp = System.currentTimeMillis();
		int date = getDateByTmstmp(tmstmp);

		synchronized (LOCK) /* this, plus constraint on DB layer (Vote#user+Vote#date unique) */{
			Vote vote = voteRepository.findOneByUserAndDate(user, date);
			if (vote == null) {
				vote = new Vote();
				vote.setUser(user);
				vote.setDate(date);
				voteRepository.save(vote);
			} else {
				if (!isTmstmpLessThan(tmstmp, Application.LAST_VOTING_HOUR, Application.LAST_VOTING_MINUTE)) {
					String msg = String.format("Time now is behind %02d:%02d. You cannot change vote!",
							Application.LAST_VOTING_HOUR, Application.LAST_VOTING_MINUTE);
					throw new VoteTimeTooLateException(msg);
				}
			}

			vote.setTmstmp(tmstmp);
			vote.setLunchMenu(lunchMenu);
		}
	}

	public static class VotingResults {
		private List<LunchMenuDto> topSelection;
		private int topSelectionVotes;

		public VotingResults() {
			super();
		}

		public VotingResults(List<LunchMenuDto> topSelection, int topSelectionVotes) {
			super();
			this.topSelection = topSelection;
			this.topSelectionVotes = topSelectionVotes;
		}

		public List<LunchMenuDto> getTopSelection() {
			return topSelection;
		}

		public void setTopSelection(List<LunchMenuDto> topSelection) {
			this.topSelection = topSelection;
		}

		public int getTopSelectionVotes() {
			return topSelectionVotes;
		}

		public void setTopSelectionVotes(int topSelectionVotes) {
			this.topSelectionVotes = topSelectionVotes;
		}

	}

	public VotingResults getTodayWinningRestaurants() {
		long tmstmp = System.currentTimeMillis();
		int date = getDateByTmstmp(tmstmp);

		Map<LunchMenu, Integer> results = new HashMap<>();
		for (Vote vote : voteRepository.findByDate(date)) {
			Integer count = results.get(vote.getLunchMenu());
			count = count == null ? 1 : count + 1;
			results.put(vote.getLunchMenu(), count);
		}

		int maxVotes = 0;
		for (Entry<LunchMenu, Integer> entry : results.entrySet()) {
			maxVotes = Math.max(entry.getValue(), maxVotes);
		}

		List<LunchMenuDto> topSelection = new ArrayList<>();
		for (Entry<LunchMenu, Integer> entry : results.entrySet()) {
			if (entry.getValue() == maxVotes) {
				topSelection.add(LunchMenuDto.fromLunchMenu(entry.getKey()));
			}
		}
		return new VotingResults(topSelection, maxVotes);
	}

}
