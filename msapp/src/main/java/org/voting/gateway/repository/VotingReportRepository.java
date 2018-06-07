package org.voting.gateway.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Candidate;
import org.voting.gateway.domain.VotingData;
import org.voting.gateway.domain.VotingReport;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

@Repository
public class VotingReportRepository {

    private Mapper<VotingReport> mapper;
    private final CassandraSession cassandraSession;

    public VotingReportRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(VotingReport.class);
    }

    public List<VotingReport> findReportsByDistrictRound(UUID districtId, UUID roundId)
    {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM voting_report WHERE ward_id = ? "
        		+ "AND turn_id = ? ALLOW FILTERING", districtId, roundId);
    	return mapper.map(results).all();
    }

    public void save(VotingReport report) {

        mapper.save(report);

    }
}
