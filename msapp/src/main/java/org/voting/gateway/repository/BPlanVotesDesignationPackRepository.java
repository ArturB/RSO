package org.voting.gateway.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import org.springframework.stereotype.Repository;
import org.voting.gateway.domain.BPlanVotesDesignationPack;
import org.voting.gateway.service.VotesDesignationPackDTO;
import org.voting.gateway.service.VotesDesignationSingleCandidateDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by defacto on 6/8/2018.
 */
@Repository
public class BPlanVotesDesignationPackRepository {
    private final Mapper<BPlanVotesDesignationPack> mapper;
    private final CassandraSession cassandraSession;
    private BPlanSingleCandidateVoteRepository singleVotesRepository;

    public BPlanVotesDesignationPackRepository(CassandraSession cassandraSession, BPlanSingleCandidateVoteRepository repository) {
        this.cassandraSession = cassandraSession;
        this.mapper = cassandraSession.getMappingManager().mapper(BPlanVotesDesignationPack.class);
        this.singleVotesRepository = repository;
    }

    public void add(VotesDesignationPackDTO votesPack, int round, UUID userId) {
        BPlanVotesDesignationPack pack = new BPlanVotesDesignationPack();
        pack.setDate(new Date());
        pack.setElectoral_district_id(votesPack.getElectoral_district_id());
        pack.setErased_marks_cards_used(votesPack.getErasedMarksCardsUsed());
        pack.setNone_marks_cards_used(votesPack.getNone_marks_cards_used());
        pack.setToo_many_marks_cards_used(votesPack.getTooManyMarksCardsUsed());
        pack.setPack_id(UUIDs.timeBased());
        pack.setRound(round);
        pack.setPositionName("TODO553");
        pack.setUser_id(userId);
        if(votesPack.getId() != null ) {
            pack.setPack_id(votesPack.getId());
        }else{
            pack.setPack_id(UUIDs.timeBased());
        }
        mapper.save(pack);

        votesPack.getCandidate_votes().stream().forEach(c -> {
            singleVotesRepository.save(c, pack.getPack_id());
        });
    }

    public void save(VotesDesignationPackDTO votesPack) {
        BPlanVotesDesignationPack pack = mapper.get(votesPack.getId());
        pack.setDate(new Date());
        pack.setElectoral_district_id(votesPack.getElectoral_district_id());
        pack.setErased_marks_cards_used(votesPack.getErasedMarksCardsUsed());
        pack.setNone_marks_cards_used(votesPack.getNone_marks_cards_used());
        pack.setToo_many_marks_cards_used(votesPack.getTooManyMarksCardsUsed());
        pack.setPack_id(votesPack.getId());
        pack.setPositionName("TODO553");
        mapper.save(pack);

        Map<UUID, VotesDesignationSingleCandidateDTO> collect
            = votesPack.getCandidate_votes().stream().collect(Collectors.toMap(c -> c.getCandidate_id(), c -> c));

        singleVotesRepository.findByPackIdNoDto(pack.getPack_id()).stream().forEach(c -> {
            c.setVotes_count(collect.get(c.getCandidate_id()).getNumber_of_votes());
            singleVotesRepository.save(c);
        });
    }

    public List<VotesDesignationPackDTO> findByTurnByUser(Integer round, UUID userId) {
        Statement statement = new SimpleStatement("SELECT * FROM bPlanVotesDesignationPack WHERE user_id = ? AND " +
            "round = ? ALLOW  FILTERING", userId, round);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<BPlanVotesDesignationPack> votes = mapper.map(results);
        return packToDto(userId, votes);
    }

    private List<VotesDesignationPackDTO> packToDto(UUID userId, Result<BPlanVotesDesignationPack> votes) {
        return votes.all().stream().map(c -> {
            VotesDesignationPackDTO outPack = new VotesDesignationPackDTO();
            outPack.setCandidate_votes(singleVotesRepository.findByPackId(c.getPack_id()));
            outPack.setDate(c.getDate());
            outPack.setElectoral_district_id(c.getElectoral_district_id());
            outPack.setErasedMarksCardsUsed(c.getErased_marks_cards_used());
            outPack.setNone_marks_cards_used(c.getNone_marks_cards_used());
            outPack.setTooManyMarksCardsUsed(c.getToo_many_marks_cards_used());
            outPack.setId(c.getPack_id());
            outPack.setUser_id(userId);
            return outPack;
        }).collect(Collectors.toList());
    }

    public List<VotesDesignationPackDTO> findByTurnByDistrictId(UUID districtId, int turnNum) {
        Statement statement = new SimpleStatement("SELECT * FROM bPlanVotesDesignationPack" +
            " WHERE   electoral_district_id = ? AND round = ? ALLOW  FILTERING", districtId, turnNum);

        ResultSet results = cassandraSession.getSession().execute(statement);
        Result<BPlanVotesDesignationPack> votes = mapper.map(results);
        return packToDto(UUIDs.timeBased(), votes);

    }
}
