package org.voting.gateway.repository;

import org.voting.gateway.domain.ElectoralDistrict;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ElectoralDistrict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElectoralDistrictRepository extends JpaRepository<ElectoralDistrict, Long> {

}
