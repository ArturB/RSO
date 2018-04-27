package org.voting.gateway.web.rest;

import org.voting.gateway.MsappApp;

import org.voting.gateway.config.SecurityBeanOverrideConfiguration;

import org.voting.gateway.domain.ElectoralDistrict;
import org.voting.gateway.repository.ElectoralDistrictRepository;
import org.voting.gateway.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.voting.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ElectoralDistrictResource REST controller.
 *
 * @see ElectoralDistrictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MsappApp.class, SecurityBeanOverrideConfiguration.class})
public class ElectoralDistrictResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NR_CAN_VOTE = 1;
    private static final Integer UPDATED_NR_CAN_VOTE = 2;

    private static final Integer DEFAULT_NR_CARDS_USED = 1;
    private static final Integer UPDATED_NR_CARDS_USED = 2;

    private static final Boolean DEFAULT_VOTING_FINISHED = false;
    private static final Boolean UPDATED_VOTING_FINISHED = true;

    @Autowired
    private ElectoralDistrictRepository electoralDistrictRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElectoralDistrictMockMvc;

    private ElectoralDistrict electoralDistrict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElectoralDistrictResource electoralDistrictResource = new ElectoralDistrictResource(electoralDistrictRepository);
        this.restElectoralDistrictMockMvc = MockMvcBuilders.standaloneSetup(electoralDistrictResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElectoralDistrict createEntity(EntityManager em) {
        ElectoralDistrict electoralDistrict = new ElectoralDistrict()
            .name(DEFAULT_NAME)
            .nrCanVote(DEFAULT_NR_CAN_VOTE)
            .nrCardsUsed(DEFAULT_NR_CARDS_USED)
            .votingFinished(DEFAULT_VOTING_FINISHED);
        return electoralDistrict;
    }

    @Before
    public void initTest() {
        electoralDistrict = createEntity(em);
    }

    @Test
    @Transactional
    public void createElectoralDistrict() throws Exception {
        int databaseSizeBeforeCreate = electoralDistrictRepository.findAll().size();

        // Create the ElectoralDistrict
        restElectoralDistrictMockMvc.perform(post("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isCreated());

        // Validate the ElectoralDistrict in the database
        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeCreate + 1);
        ElectoralDistrict testElectoralDistrict = electoralDistrictList.get(electoralDistrictList.size() - 1);
        assertThat(testElectoralDistrict.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testElectoralDistrict.getNrCanVote()).isEqualTo(DEFAULT_NR_CAN_VOTE);
        assertThat(testElectoralDistrict.getNrCardsUsed()).isEqualTo(DEFAULT_NR_CARDS_USED);
        assertThat(testElectoralDistrict.isVotingFinished()).isEqualTo(DEFAULT_VOTING_FINISHED);
    }

    @Test
    @Transactional
    public void createElectoralDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = electoralDistrictRepository.findAll().size();

        // Create the ElectoralDistrict with an existing ID
        electoralDistrict.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElectoralDistrictMockMvc.perform(post("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isBadRequest());

        // Validate the ElectoralDistrict in the database
        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = electoralDistrictRepository.findAll().size();
        // set the field null
        electoralDistrict.setName(null);

        // Create the ElectoralDistrict, which fails.

        restElectoralDistrictMockMvc.perform(post("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isBadRequest());

        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNrCanVoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = electoralDistrictRepository.findAll().size();
        // set the field null
        electoralDistrict.setNrCanVote(null);

        // Create the ElectoralDistrict, which fails.

        restElectoralDistrictMockMvc.perform(post("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isBadRequest());

        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNrCardsUsedIsRequired() throws Exception {
        int databaseSizeBeforeTest = electoralDistrictRepository.findAll().size();
        // set the field null
        electoralDistrict.setNrCardsUsed(null);

        // Create the ElectoralDistrict, which fails.

        restElectoralDistrictMockMvc.perform(post("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isBadRequest());

        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVotingFinishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = electoralDistrictRepository.findAll().size();
        // set the field null
        electoralDistrict.setVotingFinished(null);

        // Create the ElectoralDistrict, which fails.

        restElectoralDistrictMockMvc.perform(post("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isBadRequest());

        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllElectoralDistricts() throws Exception {
        // Initialize the database
        electoralDistrictRepository.saveAndFlush(electoralDistrict);

        // Get all the electoralDistrictList
        restElectoralDistrictMockMvc.perform(get("/api/electoral-districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(electoralDistrict.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nrCanVote").value(hasItem(DEFAULT_NR_CAN_VOTE)))
            .andExpect(jsonPath("$.[*].nrCardsUsed").value(hasItem(DEFAULT_NR_CARDS_USED)))
            .andExpect(jsonPath("$.[*].votingFinished").value(hasItem(DEFAULT_VOTING_FINISHED.booleanValue())));
    }

    @Test
    @Transactional
    public void getElectoralDistrict() throws Exception {
        // Initialize the database
        electoralDistrictRepository.saveAndFlush(electoralDistrict);

        // Get the electoralDistrict
        restElectoralDistrictMockMvc.perform(get("/api/electoral-districts/{id}", electoralDistrict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(electoralDistrict.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nrCanVote").value(DEFAULT_NR_CAN_VOTE))
            .andExpect(jsonPath("$.nrCardsUsed").value(DEFAULT_NR_CARDS_USED))
            .andExpect(jsonPath("$.votingFinished").value(DEFAULT_VOTING_FINISHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElectoralDistrict() throws Exception {
        // Get the electoralDistrict
        restElectoralDistrictMockMvc.perform(get("/api/electoral-districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElectoralDistrict() throws Exception {
        // Initialize the database
        electoralDistrictRepository.saveAndFlush(electoralDistrict);
        int databaseSizeBeforeUpdate = electoralDistrictRepository.findAll().size();

        // Update the electoralDistrict
        ElectoralDistrict updatedElectoralDistrict = electoralDistrictRepository.findOne(electoralDistrict.getId());
        // Disconnect from session so that the updates on updatedElectoralDistrict are not directly saved in db
        em.detach(updatedElectoralDistrict);
        updatedElectoralDistrict
            .name(UPDATED_NAME)
            .nrCanVote(UPDATED_NR_CAN_VOTE)
            .nrCardsUsed(UPDATED_NR_CARDS_USED)
            .votingFinished(UPDATED_VOTING_FINISHED);

        restElectoralDistrictMockMvc.perform(put("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElectoralDistrict)))
            .andExpect(status().isOk());

        // Validate the ElectoralDistrict in the database
        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeUpdate);
        ElectoralDistrict testElectoralDistrict = electoralDistrictList.get(electoralDistrictList.size() - 1);
        assertThat(testElectoralDistrict.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testElectoralDistrict.getNrCanVote()).isEqualTo(UPDATED_NR_CAN_VOTE);
        assertThat(testElectoralDistrict.getNrCardsUsed()).isEqualTo(UPDATED_NR_CARDS_USED);
        assertThat(testElectoralDistrict.isVotingFinished()).isEqualTo(UPDATED_VOTING_FINISHED);
    }

    @Test
    @Transactional
    public void updateNonExistingElectoralDistrict() throws Exception {
        int databaseSizeBeforeUpdate = electoralDistrictRepository.findAll().size();

        // Create the ElectoralDistrict

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restElectoralDistrictMockMvc.perform(put("/api/electoral-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(electoralDistrict)))
            .andExpect(status().isCreated());

        // Validate the ElectoralDistrict in the database
        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteElectoralDistrict() throws Exception {
        // Initialize the database
        electoralDistrictRepository.saveAndFlush(electoralDistrict);
        int databaseSizeBeforeDelete = electoralDistrictRepository.findAll().size();

        // Get the electoralDistrict
        restElectoralDistrictMockMvc.perform(delete("/api/electoral-districts/{id}", electoralDistrict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ElectoralDistrict> electoralDistrictList = electoralDistrictRepository.findAll();
        assertThat(electoralDistrictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElectoralDistrict.class);
        ElectoralDistrict electoralDistrict1 = new ElectoralDistrict();
        electoralDistrict1.setId(1L);
        ElectoralDistrict electoralDistrict2 = new ElectoralDistrict();
        electoralDistrict2.setId(electoralDistrict1.getId());
        assertThat(electoralDistrict1).isEqualTo(electoralDistrict2);
        electoralDistrict2.setId(2L);
        assertThat(electoralDistrict1).isNotEqualTo(electoralDistrict2);
        electoralDistrict1.setId(null);
        assertThat(electoralDistrict1).isNotEqualTo(electoralDistrict2);
    }
}
