package org.voting.gateway.repository;

import org.voting.gateway.domain.User;
import org.voting.gateway.domain.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Set;

/**
 * Spring Data JPA repository for the User entity.
 */
public class UserRepositoryTest {

    private User user;

    public UserRepositoryTest()
    {
        user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        user.setActivated(true);
        Authority authority = new Authority();
        authority.setName("Admin");
        user.getAuthorities().add(authority);
    }

    public Optional<User> findOneByActivationKey(String activationKey) {
        return Optional.of(user);
    }

    public List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime) {
        List<User> result = new LinkedList<User>();
        result.add(user);
        return result;
    }

    public Optional<User> findOneByResetKey(String resetKey) {
        return Optional.of(user);
    }

    public Optional<User> findOneByEmailIgnoreCase(String email) {
        return Optional.of(user);
    }

    public Optional<User> findOneByLogin(String login) {
        return Optional.of(user);
    }

    @EntityGraph(attributePaths = "authorities")
    public Optional<User> findOneWithAuthoritiesById(Long id) {
        return Optional.of(user);
    }

    @EntityGraph(attributePaths = "authorities")
    public Optional<User> findOneWithAuthoritiesByLogin(String login) {
        return Optional.of(user);
    }

    @EntityGraph(attributePaths = "authorities")
    public Optional<User> findOneWithAuthoritiesByEmail(String email) {
        return Optional.of(user);
    }

    /*
    Page<User> findAllByLoginNot(Pageable pageable, String login)
    {
        return Page.empty();
    }
    */
}
