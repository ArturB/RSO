package org.voting.gateway.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.voting.gateway.domain.Authority;
import org.voting.gateway.domain.User;

import java.util.Optional;


/**
 * Created by defacto on 5/4/2018.
 */
@Repository
public class UserRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    final String url = "http://msapp/api/parties";

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    RestTemplate restTemplate;

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

    public void findOneWithAuthoritiesByLogin(String login)
    {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        log.debug("Called FindOneWithAuthoritiesByLogin. Result: "+responseEntity.getStatusCode()+" "+responseEntity.getBody());
    }
}
