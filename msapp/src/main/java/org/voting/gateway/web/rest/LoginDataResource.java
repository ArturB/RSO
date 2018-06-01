package org.voting.gateway.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voting.gateway.domain.MyUser;
import org.voting.gateway.repository.MyUserRepository_old;
import org.voting.gateway.service.LoginDataDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginDataResource {

    private final Logger log = LoggerFactory.getLogger(LoginDataResource.class);
    private final MyUserRepository_old myUserRepository;

    public LoginDataResource(MyUserRepository_old myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @GetMapping("loginData/{login}")
    public ResponseEntity<LoginDataDTO> getMyUser(@PathVariable String login)
    {
        log.debug("REST request to get login Data of user : {}", login);
        LoginDataDTO loginDataDTO = null;

        List<MyUser> users = myUserRepository.findByUsername(login);
        if(users.size() != 0 ){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passHash = encoder.encode(login); // password==login!!
            loginDataDTO = new LoginDataDTO();
            loginDataDTO.setPassHash(passHash);
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loginDataDTO));
    }
}
