package org.vmykhalchuk.where2go4lunch.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vmykhalchuk.where2go4lunch.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
}
