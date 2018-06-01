package org.voting.gateway.repository;

import org.voting.gateway.domain.Candidate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Candidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository_old extends JpaRepository<Candidate, Long> {

}
