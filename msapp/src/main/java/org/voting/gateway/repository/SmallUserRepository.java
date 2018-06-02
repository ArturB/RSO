package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.voting.gateway.domain.MyUser;
import org.voting.gateway.domain.SmallUser;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class SmallUserRepository {

    private Mapper<SmallUser> mapper;
    private final CassandraSession cassandraSession;


    public SmallUserRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(SmallUser.class);
    }

    public SmallUser findOne(UUID id) {
        SmallUser res = mapper.get(id);
        return res;
    }

    public List<SmallUser> findByUsername(String login) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM user WHERE username = " + login);
        Result<SmallUser> users = mapper.map(results);
        return users.all();

    }

    public Page<SmallUser> findAllPaged(Pageable pageRequest)
    {
        Statement statement = new SimpleStatement("SELECT * FROM user");
        statement.setFetchSize(50);
        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<SmallUser> users = mapper.map(results);
        //skip
        for (int i = 0; i < pageRequest.getOffset() &&  !users.isExhausted(); i++) {
            users.one();
        }
        List<SmallUser> users1 = new LinkedList<>();
        for (int i = 0; i < pageRequest.getPageSize() &&  !users.isExhausted() ; i++) {
            users1.add(users.one());
        }


        return new PageImpl<SmallUser>(users1) ;

    }

    public SmallUser save(SmallUser user) {

        mapper.save(user);
        return user;

    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public List<SmallUser> findInMunicipality(UUID municipalityId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM user WHERE commune = " + municipalityId);
        Result<SmallUser> users = mapper.map(results);
        return users.all();
    }

    public List<SmallUser> findInDistrict(UUID districtId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM user WHERE ward = " + districtId);
        Result<SmallUser> users = mapper.map(results);
        return users.all();

    }
}
