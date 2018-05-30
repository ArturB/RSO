package org.voting.gateway.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.LinkedList;
import java.util.List;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Candidate;

@Repository
public class CandidateDatastax {

	Cluster cluster = null;
	ResultSet rs = null;
	Row row = null;
	Session session = null;

	public CandidateDatastax()
	{
		try {
		    cluster = Cluster.builder()                                                    	// (1)
		            .addContactPoint("cass1.brodzki.org")
		            .withCredentials("rso", "rso2018L")
		            .build();
		    session = cluster.connect("rso");                                       		// (2)

		    rs = session.execute("select release_version from system.local");    			// (3)
		    row = rs.one();
		    System.out.println(row.getString("release_version"));                          	// (4)
		} finally {
		    //                                        // (5)
		}
	}

	public List<Candidate> findAll()
	{
		System.out.println("Blablabla");

//	    rs = session.execute("select release_version from system.local");    			// (3)
//	    row = rs.one();
//	    System.out.println(row.getString("release_version"));                          	// (4)

        MappingManager manager = new MappingManager(session);

        Mapper<Candidate> mapper = manager.mapper(Candidate.class);
        Candidate temp = mapper.get(1);
        LinkedList<Candidate> ll = new LinkedList<Candidate>();
        ll.add(temp);

        return ll;
	}
	void close()
    {
        if (cluster != null) cluster.close();
    }

}
