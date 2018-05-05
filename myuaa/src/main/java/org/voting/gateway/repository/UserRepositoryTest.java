package org.voting.gateway.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.voting.gateway.domain.User;
import org.voting.gateway.domain.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voting.gateway.service.dto.LoginDataDTO;

import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Set;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public class UserRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    final String url = "http://localhost:8081/api/loginData/";

    RestTemplate restTemplate = new RestTemplate();

    private User user;

    public UserRepositoryTest()
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passHash = encoder.encode("abc");
        user = new User();
        user.setLogin("abc");
        //user.setPassword(passHash);
        user.setActivated(true);
        Authority authority = new Authority();
        authority.setName("ROLE_ADMIN");
        user.getAuthorities().add(authority);
    }




    public Optional<User> findOneWithAuthoritiesByLogin(String login)
    {
        ResponseEntity<LoginDataDTO> responseEntity = restTemplate.getForEntity(url+login,LoginDataDTO.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
        {
            log.debug("failed to get login data from msapp, status: {}",
                responseEntity.getStatusCodeValue());

            return Optional.empty();
        }
        LoginDataDTO loginData = responseEntity.getBody();

        user.setLogin(login);
        user.setPassword(loginData.getPassHash());




        return Optional.of(user);
    }


}
