package org.voting.gateway.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import org.voting.gateway.domain.SmallUser;
import org.voting.gateway.service.LoginDataDTO;
import org.voting.gateway.service.RodoUserDTO;


import com.datastax.driver.mapping.Mapper;

import io.github.jhipster.web.util.ResponseUtil;

@Repository
public class RodoUserRepository {


    private final Logger log = LoggerFactory.getLogger(RodoUserRepository.class);

    @Autowired
    @Lazy
    @Qualifier("loadBalancedRestTemplate")
    RestTemplate restTemplate;

    public RodoUserDTO findOne(UUID id) {
    	ResponseEntity<RodoUserDTO> re = restTemplate.getForEntity("http://msrodo/api/rodoUser/" + id, RodoUserDTO.class);
    	if (re.getStatusCode() != HttpStatus.OK)
    	{
    		log.debug("Nie znaleziono rodo user");
    		return null;
    	}
    	return re.getBody();
    }

    public RodoUserDTO save(RodoUserDTO user) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        //headers.set("X-TP-DeviceID", Global.deviceID);
        HttpEntity<RodoUserDTO> requestEntity = new HttpEntity<RodoUserDTO>(user, headers);
        ResponseEntity<RodoUserDTO> response = restTemplate.exchange("http://msrodo/api/saveRodoUser", HttpMethod.PUT, requestEntity, RodoUserDTO.class);

    	if (response.getStatusCode() != HttpStatus.OK)
    	{
    		log.debug("Nie udalo sie zapisac rodo user");
    		return null;
    	}
    	return response.getBody();
    }

    public void delete(UUID id) {
    	restTemplate.delete("http://msrodo/api/deleteRodoUser/" + id);
    	// TODO pokazywanie bledu jesli usuwanie sie nie uda
    }
}
