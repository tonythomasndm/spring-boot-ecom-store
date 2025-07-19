package com.tonythomasndm.store.repositories;

import com.tonythomasndm.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}