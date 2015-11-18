package org.vmykhalchuk.where2go4lunch.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vmykhalchuk.where2go4lunch.model.LunchMenu;

@Repository
public interface LunchMenuRepository extends PagingAndSortingRepository<LunchMenu, Long> {

}
