package com.devd.atmapi.repository;

import com.devd.atmapi.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

}
