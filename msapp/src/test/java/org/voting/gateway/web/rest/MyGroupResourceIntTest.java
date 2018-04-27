package org.voting.gateway.web.rest;

import org.voting.gateway.MsappApp;

import org.voting.gateway.config.SecurityBeanOverrideConfiguration;

import org.voting.gateway.domain.MyGroup;
import org.voting.gateway.repository.MyGroupRepository;
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
 * Test class for the MyGroupResource REST controller.
 *
 * @see MyGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MsappApp.class, SecurityBeanOverrideConfiguration.class})
public class MyGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MyGroupRepository myGroupRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyGroupMockMvc;

    private MyGroup myGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyGroupResource myGroupResource = new MyGroupResource(myGroupRepository);
        this.restMyGroupMockMvc = MockMvcBuilders.standaloneSetup(myGroupResource)
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
    public static MyGroup createEntity(EntityManager em) {
        MyGroup myGroup = new MyGroup()
            .name(DEFAULT_NAME);
        return myGroup;
    }

    @Before
    public void initTest() {
        myGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyGroup() throws Exception {
        int databaseSizeBeforeCreate = myGroupRepository.findAll().size();

        // Create the MyGroup
        restMyGroupMockMvc.perform(post("/api/my-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myGroup)))
            .andExpect(status().isCreated());

        // Validate the MyGroup in the database
        List<MyGroup> myGroupList = myGroupRepository.findAll();
        assertThat(myGroupList).hasSize(databaseSizeBeforeCreate + 1);
        MyGroup testMyGroup = myGroupList.get(myGroupList.size() - 1);
        assertThat(testMyGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMyGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myGroupRepository.findAll().size();

        // Create the MyGroup with an existing ID
        myGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyGroupMockMvc.perform(post("/api/my-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myGroup)))
            .andExpect(status().isBadRequest());

        // Validate the MyGroup in the database
        List<MyGroup> myGroupList = myGroupRepository.findAll();
        assertThat(myGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myGroupRepository.findAll().size();
        // set the field null
        myGroup.setName(null);

        // Create the MyGroup, which fails.

        restMyGroupMockMvc.perform(post("/api/my-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myGroup)))
            .andExpect(status().isBadRequest());

        List<MyGroup> myGroupList = myGroupRepository.findAll();
        assertThat(myGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyGroups() throws Exception {
        // Initialize the database
        myGroupRepository.saveAndFlush(myGroup);

        // Get all the myGroupList
        restMyGroupMockMvc.perform(get("/api/my-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMyGroup() throws Exception {
        // Initialize the database
        myGroupRepository.saveAndFlush(myGroup);

        // Get the myGroup
        restMyGroupMockMvc.perform(get("/api/my-groups/{id}", myGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyGroup() throws Exception {
        // Get the myGroup
        restMyGroupMockMvc.perform(get("/api/my-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyGroup() throws Exception {
        // Initialize the database
        myGroupRepository.saveAndFlush(myGroup);
        int databaseSizeBeforeUpdate = myGroupRepository.findAll().size();

        // Update the myGroup
        MyGroup updatedMyGroup = myGroupRepository.findOne(myGroup.getId());
        // Disconnect from session so that the updates on updatedMyGroup are not directly saved in db
        em.detach(updatedMyGroup);
        updatedMyGroup
            .name(UPDATED_NAME);

        restMyGroupMockMvc.perform(put("/api/my-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyGroup)))
            .andExpect(status().isOk());

        // Validate the MyGroup in the database
        List<MyGroup> myGroupList = myGroupRepository.findAll();
        assertThat(myGroupList).hasSize(databaseSizeBeforeUpdate);
        MyGroup testMyGroup = myGroupList.get(myGroupList.size() - 1);
        assertThat(testMyGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMyGroup() throws Exception {
        int databaseSizeBeforeUpdate = myGroupRepository.findAll().size();

        // Create the MyGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyGroupMockMvc.perform(put("/api/my-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myGroup)))
            .andExpect(status().isCreated());

        // Validate the MyGroup in the database
        List<MyGroup> myGroupList = myGroupRepository.findAll();
        assertThat(myGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyGroup() throws Exception {
        // Initialize the database
        myGroupRepository.saveAndFlush(myGroup);
        int databaseSizeBeforeDelete = myGroupRepository.findAll().size();

        // Get the myGroup
        restMyGroupMockMvc.perform(delete("/api/my-groups/{id}", myGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyGroup> myGroupList = myGroupRepository.findAll();
        assertThat(myGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyGroup.class);
        MyGroup myGroup1 = new MyGroup();
        myGroup1.setId(1L);
        MyGroup myGroup2 = new MyGroup();
        myGroup2.setId(myGroup1.getId());
        assertThat(myGroup1).isEqualTo(myGroup2);
        myGroup2.setId(2L);
        assertThat(myGroup1).isNotEqualTo(myGroup2);
        myGroup1.setId(null);
        assertThat(myGroup1).isNotEqualTo(myGroup2);
    }
}
