package org.voting.gateway.repository;

import org.voting.gateway.domain.Party;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Party entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRepository_old extends JpaRepository<Party, Long> {

}
