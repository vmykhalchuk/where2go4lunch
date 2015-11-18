package org.vmykhalchuk.where2go4lunch.dao;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vmykhalchuk.where2go4lunch.model.User;
import org.vmykhalchuk.where2go4lunch.model.Vote;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {
	Collection<Vote> findByUserAndTmstmpGreaterThan(User user, long tmstmp);
	Vote findOneByUserAndDate(User user, int date);
	
	Collection<Vote> findByDate(int date);
}
