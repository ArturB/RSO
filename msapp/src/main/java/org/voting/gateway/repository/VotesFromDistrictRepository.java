package org.voting.gateway.repository;

import org.voting.gateway.domain.VotesFromDistrict;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VotesFromDistrict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotesFromDistrictRepository extends JpaRepository<VotesFromDistrict, Long> {

}
