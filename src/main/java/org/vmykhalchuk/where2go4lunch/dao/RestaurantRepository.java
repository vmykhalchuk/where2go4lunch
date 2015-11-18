package org.vmykhalchuk.where2go4lunch.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vmykhalchuk.where2go4lunch.model.Restaurant;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

}
