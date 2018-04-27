package org.voting.gateway.web.rest;

import org.voting.gateway.MsappApp;

import org.voting.gateway.config.SecurityBeanOverrideConfiguration;

import org.voting.gateway.domain.MyUser;
import org.voting.gateway.repository.MyUserRepository;
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
 * Test class for the MyUserResource REST controller.
 *
 * @see MyUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MsappApp.class, SecurityBeanOverrideConfiguration.class})
public class MyUserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NR = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "tr@\"M.Q";
    private static final String UPDATED_EMAIL = "Pj@y.D";

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PESEL = "AAAAAAAAAAA";
    private static final String UPDATED_PESEL = "BBBBBBBBBBB";

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyUserMockMvc;

    private MyUser myUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyUserResource myUserResource = new MyUserResource(myUserRepository);
        this.restMyUserMockMvc = MockMvcBuilders.standaloneSetup(myUserResource)
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
    public static MyUser createEntity(EntityManager em) {
        MyUser myUser = new MyUser()
            .username(DEFAULT_USERNAME)
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentNr(DEFAULT_DOCUMENT_NR)
            .email(DEFAULT_EMAIL)
            .birthdate(DEFAULT_BIRTHDATE)
            .pesel(DEFAULT_PESEL);
        return myUser;
    }

    @Before
    public void initTest() {
        myUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyUser() throws Exception {
        int databaseSizeBeforeCreate = myUserRepository.findAll().size();

        // Create the MyUser
        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isCreated());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate + 1);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMyUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMyUser.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testMyUser.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testMyUser.getDocumentNr()).isEqualTo(DEFAULT_DOCUMENT_NR);
        assertThat(testMyUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMyUser.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testMyUser.getPesel()).isEqualTo(DEFAULT_PESEL);
    }

    @Test
    @Transactional
    public void createMyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myUserRepository.findAll().size();

        // Create the MyUser with an existing ID
        myUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setUsername(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setName(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setSurname(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setDocumentType(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentNrIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setDocumentNr(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setEmail(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setBirthdate(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeselIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setPesel(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyUsers() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get all the myUserList
        restMyUserMockMvc.perform(get("/api/my-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentNr").value(hasItem(DEFAULT_DOCUMENT_NR.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].pesel").value(hasItem(DEFAULT_PESEL.toString())));
    }

    @Test
    @Transactional
    public void getMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get the myUser
        restMyUserMockMvc.perform(get("/api/my-users/{id}", myUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentNr").value(DEFAULT_DOCUMENT_NR.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.pesel").value(DEFAULT_PESEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyUser() throws Exception {
        // Get the myUser
        restMyUserMockMvc.perform(get("/api/my-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser
        MyUser updatedMyUser = myUserRepository.findOne(myUser.getId());
        // Disconnect from session so that the updates on updatedMyUser are not directly saved in db
        em.detach(updatedMyUser);
        updatedMyUser
            .username(UPDATED_USERNAME)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentNr(UPDATED_DOCUMENT_NR)
            .email(UPDATED_EMAIL)
            .birthdate(UPDATED_BIRTHDATE)
            .pesel(UPDATED_PESEL);

        restMyUserMockMvc.perform(put("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyUser)))
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMyUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMyUser.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testMyUser.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testMyUser.getDocumentNr()).isEqualTo(UPDATED_DOCUMENT_NR);
        assertThat(testMyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMyUser.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testMyUser.getPesel()).isEqualTo(UPDATED_PESEL);
    }

    @Test
    @Transactional
    public void updateNonExistingMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Create the MyUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyUserMockMvc.perform(put("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isCreated());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);
        int databaseSizeBeforeDelete = myUserRepository.findAll().size();

        // Get the myUser
        restMyUserMockMvc.perform(delete("/api/my-users/{id}", myUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyUser.class);
        MyUser myUser1 = new MyUser();
        myUser1.setId(1L);
        MyUser myUser2 = new MyUser();
        myUser2.setId(myUser1.getId());
        assertThat(myUser1).isEqualTo(myUser2);
        myUser2.setId(2L);
        assertThat(myUser1).isNotEqualTo(myUser2);
        myUser1.setId(null);
        assertThat(myUser1).isNotEqualTo(myUser2);
    }
}
