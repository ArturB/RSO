package org.voting.gateway.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.repository.SmallUserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.voting.gateway.domain.MyUser;
import org.voting.gateway.service.LoginDataDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginDataResource {

    private final Logger log = LoggerFactory.getLogger(LoginDataResource.class);
    private final SmallUserRepository smallUserRepository;

    public LoginDataResource(SmallUserRepository smallUserRepository) {
        this.smallUserRepository = smallUserRepository;
    }

/*    @Autowired
    @Lazy
    @Qualifier("loadBalancedRestTemplate")
    RestTemplate restTemplate;*/

    @GetMapping("loginData/{login}")
    public ResponseEntity<LoginDataDTO> getMyUser(@PathVariable String login, @RequestHeader HttpHeaders headers)
    {
        log.debug("REST request to get login Data of user : {}", login);
        LoginDataDTO loginDataDTO = null;
        /*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passHash = encoder.encode(login);
        log.debug( "hash:" + passHash);*/

        List<SmallUser> users = smallUserRepository.findByUsername(login);
        if(!users.isEmpty()){

            loginDataDTO = new LoginDataDTO();

            loginDataDTO.setPassHash(users.get(0).getPassword());
            loginDataDTO.setDisabled(users.get(0).isDisabled());
            loginDataDTO.setGroup(users.get(0).getRole());
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loginDataDTO));
        /*return restTemplate.exchange("http://msrodo/api/loginData/"+login, HttpMethod.GET, new HttpEntity<>(headers),
                LoginDataDTO.class);*/
    }
}
