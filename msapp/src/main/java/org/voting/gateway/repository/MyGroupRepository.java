package org.voting.gateway.repository;

import org.voting.gateway.domain.MyGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MyGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyGroupRepository extends JpaRepository<MyGroup, Long> {

}
