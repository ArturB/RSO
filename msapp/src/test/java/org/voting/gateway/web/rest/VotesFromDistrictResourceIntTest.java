package org.voting.gateway.web.rest;

import org.voting.gateway.MsappApp;

import org.voting.gateway.config.SecurityBeanOverrideConfiguration;

import org.voting.gateway.domain.VotesFromDistrict;
import org.voting.gateway.repository.VotesFromDistrictRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.voting.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VotesFromDistrictResource REST controller.
 *
 * @see VotesFromDistrictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MsappApp.class, SecurityBeanOverrideConfiguration.class})
public class VotesFromDistrictResourceIntTest {

    private static final Integer DEFAULT_NR_OF_VOTES = 0;
    private static final Integer UPDATED_NR_OF_VOTES = 1;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private VotesFromDistrictRepository votesFromDistrictRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVotesFromDistrictMockMvc;

    private VotesFromDistrict votesFromDistrict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VotesFromDistrictResource votesFromDistrictResource = new VotesFromDistrictResource(votesFromDistrictRepository);
        this.restVotesFromDistrictMockMvc = MockMvcBuilders.standaloneSetup(votesFromDistrictResource)
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
    public static VotesFromDistrict createEntity(EntityManager em) {
        VotesFromDistrict votesFromDistrict = new VotesFromDistrict()
            .nrOfVotes(DEFAULT_NR_OF_VOTES)
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE);
        return votesFromDistrict;
    }

    @Before
    public void initTest() {
        votesFromDistrict = createEntity(em);
    }

    @Test
    @Transactional
    public void createVotesFromDistrict() throws Exception {
        int databaseSizeBeforeCreate = votesFromDistrictRepository.findAll().size();

        // Create the VotesFromDistrict
        restVotesFromDistrictMockMvc.perform(post("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votesFromDistrict)))
            .andExpect(status().isCreated());

        // Validate the VotesFromDistrict in the database
        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeCreate + 1);
        VotesFromDistrict testVotesFromDistrict = votesFromDistrictList.get(votesFromDistrictList.size() - 1);
        assertThat(testVotesFromDistrict.getNrOfVotes()).isEqualTo(DEFAULT_NR_OF_VOTES);
        assertThat(testVotesFromDistrict.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVotesFromDistrict.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createVotesFromDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votesFromDistrictRepository.findAll().size();

        // Create the VotesFromDistrict with an existing ID
        votesFromDistrict.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotesFromDistrictMockMvc.perform(post("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votesFromDistrict)))
            .andExpect(status().isBadRequest());

        // Validate the VotesFromDistrict in the database
        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNrOfVotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = votesFromDistrictRepository.findAll().size();
        // set the field null
        votesFromDistrict.setNrOfVotes(null);

        // Create the VotesFromDistrict, which fails.

        restVotesFromDistrictMockMvc.perform(post("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votesFromDistrict)))
            .andExpect(status().isBadRequest());

        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = votesFromDistrictRepository.findAll().size();
        // set the field null
        votesFromDistrict.setDate(null);

        // Create the VotesFromDistrict, which fails.

        restVotesFromDistrictMockMvc.perform(post("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votesFromDistrict)))
            .andExpect(status().isBadRequest());

        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = votesFromDistrictRepository.findAll().size();
        // set the field null
        votesFromDistrict.setType(null);

        // Create the VotesFromDistrict, which fails.

        restVotesFromDistrictMockMvc.perform(post("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votesFromDistrict)))
            .andExpect(status().isBadRequest());

        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVotesFromDistricts() throws Exception {
        // Initialize the database
        votesFromDistrictRepository.saveAndFlush(votesFromDistrict);

        // Get all the votesFromDistrictList
        restVotesFromDistrictMockMvc.perform(get("/api/votes-from-districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(votesFromDistrict.getId().intValue())))
            .andExpect(jsonPath("$.[*].nrOfVotes").value(hasItem(DEFAULT_NR_OF_VOTES)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getVotesFromDistrict() throws Exception {
        // Initialize the database
        votesFromDistrictRepository.saveAndFlush(votesFromDistrict);

        // Get the votesFromDistrict
        restVotesFromDistrictMockMvc.perform(get("/api/votes-from-districts/{id}", votesFromDistrict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(votesFromDistrict.getId().intValue()))
            .andExpect(jsonPath("$.nrOfVotes").value(DEFAULT_NR_OF_VOTES))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVotesFromDistrict() throws Exception {
        // Get the votesFromDistrict
        restVotesFromDistrictMockMvc.perform(get("/api/votes-from-districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVotesFromDistrict() throws Exception {
        // Initialize the database
        votesFromDistrictRepository.saveAndFlush(votesFromDistrict);
        int databaseSizeBeforeUpdate = votesFromDistrictRepository.findAll().size();

        // Update the votesFromDistrict
        VotesFromDistrict updatedVotesFromDistrict = votesFromDistrictRepository.findOne(votesFromDistrict.getId());
        // Disconnect from session so that the updates on updatedVotesFromDistrict are not directly saved in db
        em.detach(updatedVotesFromDistrict);
        updatedVotesFromDistrict
            .nrOfVotes(UPDATED_NR_OF_VOTES)
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE);

        restVotesFromDistrictMockMvc.perform(put("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVotesFromDistrict)))
            .andExpect(status().isOk());

        // Validate the VotesFromDistrict in the database
        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeUpdate);
        VotesFromDistrict testVotesFromDistrict = votesFromDistrictList.get(votesFromDistrictList.size() - 1);
        assertThat(testVotesFromDistrict.getNrOfVotes()).isEqualTo(UPDATED_NR_OF_VOTES);
        assertThat(testVotesFromDistrict.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVotesFromDistrict.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVotesFromDistrict() throws Exception {
        int databaseSizeBeforeUpdate = votesFromDistrictRepository.findAll().size();

        // Create the VotesFromDistrict

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVotesFromDistrictMockMvc.perform(put("/api/votes-from-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(votesFromDistrict)))
            .andExpect(status().isCreated());

        // Validate the VotesFromDistrict in the database
        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVotesFromDistrict() throws Exception {
        // Initialize the database
        votesFromDistrictRepository.saveAndFlush(votesFromDistrict);
        int databaseSizeBeforeDelete = votesFromDistrictRepository.findAll().size();

        // Get the votesFromDistrict
        restVotesFromDistrictMockMvc.perform(delete("/api/votes-from-districts/{id}", votesFromDistrict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VotesFromDistrict> votesFromDistrictList = votesFromDistrictRepository.findAll();
        assertThat(votesFromDistrictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VotesFromDistrict.class);
        VotesFromDistrict votesFromDistrict1 = new VotesFromDistrict();
        votesFromDistrict1.setId(1L);
        VotesFromDistrict votesFromDistrict2 = new VotesFromDistrict();
        votesFromDistrict2.setId(votesFromDistrict1.getId());
        assertThat(votesFromDistrict1).isEqualTo(votesFromDistrict2);
        votesFromDistrict2.setId(2L);
        assertThat(votesFromDistrict1).isNotEqualTo(votesFromDistrict2);
        votesFromDistrict1.setId(null);
        assertThat(votesFromDistrict1).isNotEqualTo(votesFromDistrict2);
    }
}
