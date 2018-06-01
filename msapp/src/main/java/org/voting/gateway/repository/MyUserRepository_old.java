package org.voting.gateway.repository;

import org.voting.gateway.domain.MyUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyUserRepository_old extends JpaRepository<MyUser, Long> {
    List<MyUser> findByUsername(String username);
}
