package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.Municipality;
import org.voting.gateway.domain.Turn;
import org.voting.gateway.service.MunicipalityDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MunicipalityRepository {

    private Mapper<Municipality> mapper;
    private final CassandraSession cassandraSession;

    private final TurnRepository turnRepository;


    public MunicipalityRepository(CassandraSession cassandraSession, TurnRepository turnRepository) {
        this.cassandraSession = cassandraSession;
        this.turnRepository = turnRepository;
        mapper = cassandraSession.getMappingManager().mapper(Municipality.class);
    }


    public List<Municipality> findAll() {
        ResultSet results = cassandraSession.getSession().execute("SELECT * FROM commune");
        Result<Municipality> candidates = mapper.map(results);
        return candidates.all();
    }

    public Municipality findOne(UUID id) {
        Municipality res = mapper.get(id);
        return res;
    }

    public List<MunicipalityDTO> findAllDTO() {
        List<Municipality> municipalities = findAll();
        List<Turn> turns = turnRepository.findAll();

        List<MunicipalityDTO> dto = municipalities.stream()
            .map(s ->{

                List<Turn> turnsForMun = turns.stream()
                    .filter(t ->{ return t.getCommune() == s.getMunicipality_id();}).collect(Collectors.toList());
                boolean isLastTurn = turnsForMun.stream()
                    .anyMatch(a -> {return a.isLastTurn();});

                if(turnsForMun.size() > 2 ) throw new RuntimeException("Too many turns for municipality:" + s.getName());

                boolean first_round_votes_accepted = turnsForMun.stream()
                    .filter(t -> !t.isLastTurn())
                    .anyMatch(t-> t.isTurnFinished());

                boolean second_round_votes_accepted = turnsForMun.stream()
                    .filter(t -> t.isLastTurn())
                    .anyMatch(t-> t.isTurnFinished());

                MunicipalityDTO m = new MunicipalityDTO(s);
                m.setHas_second_round(isLastTurn);
                m.setFirst_round_votes_accepted(first_round_votes_accepted);
                m.setSecond_round_votes_accepted(second_round_votes_accepted);

                return m;
            }).collect(Collectors.toList());

        return dto;
    }

    public MunicipalityDTO findOneDTO(UUID id) {
        Municipality municipality = findOne(id);


        if(municipality == null) return null;
        List<Turn> turnsForMun = turnRepository.findInMunicipality(municipality.getMunicipality_id());



        boolean isLastTurn = turnsForMun.stream()
            .anyMatch(a -> {return a.isLastTurn();});

        if(turnsForMun.size() > 2 ) throw new RuntimeException("Too many turns for municipality:" + municipality.getName());

        boolean first_round_votes_accepted = turnsForMun.stream()
            .filter(t -> !t.isLastTurn())
            .anyMatch(t-> t.isTurnFinished());

        boolean second_round_votes_accepted = turnsForMun.stream()
            .filter(t -> t.isLastTurn())
            .anyMatch(t-> t.isTurnFinished());

        MunicipalityDTO m = new MunicipalityDTO(municipality);
        m.setHas_second_round(isLastTurn);
        m.setFirst_round_votes_accepted(first_round_votes_accepted);
        m.setSecond_round_votes_accepted(second_round_votes_accepted);

        return m;



    }
}
