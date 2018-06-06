package org.voting.gateway.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.RodoUser;
import org.voting.gateway.domain.SmallUser;

import com.datastax.driver.mapping.Mapper;

@Repository
public class RodoUserRepository {

	private Mapper<RodoUser> mapper;
	private final CassandraSession cassandraSession;
	
	// TODO zmiany by laczylo sie z mikrousluga rodo
	
    public RodoUserRepository(CassandraSession cassandraSession) {
        //this.cassandraSession = cassandraSession;
        //mapper = cassandraSession.getMappingManager().mapper(RodoUser.class);
    	
    }
    
    public RodoUser findOne(UUID id) {
    	//RodoUser res = mapper.get(id);
    	//return res;
    }
    
    public RodoUser save(RodoUser user) {
    	//mapper.save(user);
    	//return user;
    }
    
    public void delete(UUID id) {
    	//mapper.delete(id);
    }
}
