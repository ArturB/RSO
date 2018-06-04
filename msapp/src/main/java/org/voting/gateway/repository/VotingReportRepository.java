package org.voting.gateway.repository;

import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.VotingData;
import org.voting.gateway.domain.VotingReport;

import com.datastax.driver.mapping.Mapper;

@Repository
public class VotingReportRepository {
	
    private Mapper<VotingReport> mapper;
    private final CassandraSession cassandraSession;
	
    public VotingReportRepository(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
        mapper = cassandraSession.getMappingManager().mapper(VotingReport.class);
    }
}
