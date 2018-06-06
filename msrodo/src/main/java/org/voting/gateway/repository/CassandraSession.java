package org.voting.gateway.repository;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

@Repository
public class CassandraSession {

    private Cluster cluster = null;
    private Session session = null;
    private MappingManager mappingManager = null;

    public CassandraSession() {
        cluster = Cluster.builder()
            .addContactPoint("rodo.brodzki.org")
            .withCredentials("rso", "rso2018L")
            .build();
        session = cluster.connect("rodo");
        mappingManager = new MappingManager(session);
    }

    void close()
    {
        if (cluster != null) cluster.close();
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Session getSession() {
        return session;
    }

    public MappingManager getMappingManager() {
        return mappingManager;
    }
}
