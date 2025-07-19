package com.tonythomasndm.store.repositories;

import com.tonythomasndm.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}