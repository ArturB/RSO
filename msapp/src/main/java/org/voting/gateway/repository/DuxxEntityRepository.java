package org.voting.gateway.repository;

import org.voting.gateway.domain.DuxxEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DuxxEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DuxxEntityRepository extends JpaRepository<DuxxEntity, Long> {

}
