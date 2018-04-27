package org.voting.gateway.repository;

import org.voting.gateway.domain.Municipality;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Municipality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

}
