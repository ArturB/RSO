package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM user WHERE username = ? ALLOW " +
                "FILTERING" ,
            login );
        Result<SmallUser> users = mapper.map(results);
        return users.all();

    }

    public List<SmallUser> findAll()
    {
        Statement statement = new SimpleStatement("SELECT * FROM user");
        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<SmallUser> users = mapper.map(results);
        return users.all();
    }

    public PageWithTotalCount<SmallUser> findAllPaged(Pageable pageRequest)
    {
        Statement statement = new SimpleStatement("SELECT * FROM user");
        statement.setFetchSize(50);
        ResultSet results = cassandraSession.getSession().execute(statement);
        int total = (int) cassandraSession.getSession().execute(new SimpleStatement("SELECT COUNT(*) FROM user")).one()
            .getLong(0);
        Result<SmallUser> users = mapper.map(results);
        //skip
        for (int i = 0; i < pageRequest.getOffset() &&  !users.isExhausted(); i++) {
            users.one();
        }
        List<SmallUser> users1 = new LinkedList<>();
        for (int i = 0; i < pageRequest.getPageSize() &&  !users.isExhausted() ; i++) {
            users1.add(users.one());
        }

        return new PageWithTotalCount<>(total, new PageImpl<>(users1)) ;
    }

    public SmallUser save(SmallUser user) {
        mapper.save(user);
        return user;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public List<SmallUser> findInMunicipality(UUID municipalityId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM user WHERE commune = ? ALLOW " +
            "FILTERING" , municipalityId);
        Result<SmallUser> users = mapper.map(results);
        return users.all();
    }

    public List<SmallUser> findInDistrict(UUID districtId) {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM user WHERE ward = ? ALLOW " +
            "FILTERING" , districtId);
        Result<SmallUser> users = mapper.map(results);
        return users.all();

    }

    public void disable(UUID id) {
        SmallUser user = findOne(id);
        user.setDisabled(true);
        save(user);
    }
}
