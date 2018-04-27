package org.voting.gateway.web.rest;

import org.voting.gateway.MsappApp;

import org.voting.gateway.config.SecurityBeanOverrideConfiguration;

import org.voting.gateway.domain.Municipality;
import org.voting.gateway.repository.MunicipalityRepository;
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
 * Test class for the MunicipalityResource REST controller.
 *
 * @see MunicipalityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MsappApp.class, SecurityBeanOverrideConfiguration.class})
public class MunicipalityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMunicipalityMockMvc;

    private Municipality municipality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MunicipalityResource municipalityResource = new MunicipalityResource(municipalityRepository);
        this.restMunicipalityMockMvc = MockMvcBuilders.standaloneSetup(municipalityResource)
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
    public static Municipality createEntity(EntityManager em) {
        Municipality municipality = new Municipality()
            .name(DEFAULT_NAME);
        return municipality;
    }

    @Before
    public void initTest() {
        municipality = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipality() throws Exception {
        int databaseSizeBeforeCreate = municipalityRepository.findAll().size();

        // Create the Municipality
        restMunicipalityMockMvc.perform(post("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipality)))
            .andExpect(status().isCreated());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeCreate + 1);
        Municipality testMunicipality = municipalityList.get(municipalityList.size() - 1);
        assertThat(testMunicipality.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMunicipalityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipalityRepository.findAll().size();

        // Create the Municipality with an existing ID
        municipality.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipalityMockMvc.perform(post("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipality)))
            .andExpect(status().isBadRequest());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMunicipalities() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);

        // Get all the municipalityList
        restMunicipalityMockMvc.perform(get("/api/municipalities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipality.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMunicipality() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);

        // Get the municipality
        restMunicipalityMockMvc.perform(get("/api/municipalities/{id}", municipality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(municipality.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipality() throws Exception {
        // Get the municipality
        restMunicipalityMockMvc.perform(get("/api/municipalities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipality() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);
        int databaseSizeBeforeUpdate = municipalityRepository.findAll().size();

        // Update the municipality
        Municipality updatedMunicipality = municipalityRepository.findOne(municipality.getId());
        // Disconnect from session so that the updates on updatedMunicipality are not directly saved in db
        em.detach(updatedMunicipality);
        updatedMunicipality
            .name(UPDATED_NAME);

        restMunicipalityMockMvc.perform(put("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMunicipality)))
            .andExpect(status().isOk());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeUpdate);
        Municipality testMunicipality = municipalityList.get(municipalityList.size() - 1);
        assertThat(testMunicipality.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipality() throws Exception {
        int databaseSizeBeforeUpdate = municipalityRepository.findAll().size();

        // Create the Municipality

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMunicipalityMockMvc.perform(put("/api/municipalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipality)))
            .andExpect(status().isCreated());

        // Validate the Municipality in the database
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMunicipality() throws Exception {
        // Initialize the database
        municipalityRepository.saveAndFlush(municipality);
        int databaseSizeBeforeDelete = municipalityRepository.findAll().size();

        // Get the municipality
        restMunicipalityMockMvc.perform(delete("/api/municipalities/{id}", municipality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Municipality> municipalityList = municipalityRepository.findAll();
        assertThat(municipalityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipality.class);
        Municipality municipality1 = new Municipality();
        municipality1.setId(1L);
        Municipality municipality2 = new Municipality();
        municipality2.setId(municipality1.getId());
        assertThat(municipality1).isEqualTo(municipality2);
        municipality2.setId(2L);
        assertThat(municipality1).isNotEqualTo(municipality2);
        municipality1.setId(null);
        assertThat(municipality1).isNotEqualTo(municipality2);
    }
}
