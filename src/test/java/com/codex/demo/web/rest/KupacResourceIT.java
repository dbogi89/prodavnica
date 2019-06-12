package com.codex.demo.web.rest;

import com.codex.demo.ProdavnicaApp;
import com.codex.demo.domain.Kupac;
import com.codex.demo.repository.KupacRepository;
import com.codex.demo.service.KupacService;
import com.codex.demo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.codex.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link KupacResource} REST controller.
 */
@SpringBootTest(classes = ProdavnicaApp.class)
public class KupacResourceIT {

    private static final String DEFAULT_NAZIV_KUPCA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_KUPCA = "BBBBBBBBBB";

    private static final String DEFAULT_PIB = "AAAAAAAAAA";
    private static final String UPDATED_PIB = "BBBBBBBBBB";

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private KupacService kupacService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restKupacMockMvc;

    private Kupac kupac;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KupacResource kupacResource = new KupacResource(kupacService);
        this.restKupacMockMvc = MockMvcBuilders.standaloneSetup(kupacResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kupac createEntity(EntityManager em) {
        Kupac kupac = new Kupac()
            .nazivKupca(DEFAULT_NAZIV_KUPCA)
            .pib(DEFAULT_PIB);
        return kupac;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kupac createUpdatedEntity(EntityManager em) {
        Kupac kupac = new Kupac()
            .nazivKupca(UPDATED_NAZIV_KUPCA)
            .pib(UPDATED_PIB);
        return kupac;
    }

    @BeforeEach
    public void initTest() {
        kupac = createEntity(em);
    }

    @Test
    @Transactional
    public void createKupac() throws Exception {
        int databaseSizeBeforeCreate = kupacRepository.findAll().size();

        // Create the Kupac
        restKupacMockMvc.perform(post("/api/kupacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kupac)))
            .andExpect(status().isCreated());

        // Validate the Kupac in the database
        List<Kupac> kupacList = kupacRepository.findAll();
        assertThat(kupacList).hasSize(databaseSizeBeforeCreate + 1);
        Kupac testKupac = kupacList.get(kupacList.size() - 1);
        assertThat(testKupac.getNazivKupca()).isEqualTo(DEFAULT_NAZIV_KUPCA);
        assertThat(testKupac.getPib()).isEqualTo(DEFAULT_PIB);
    }

    @Test
    @Transactional
    public void createKupacWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kupacRepository.findAll().size();

        // Create the Kupac with an existing ID
        kupac.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKupacMockMvc.perform(post("/api/kupacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kupac)))
            .andExpect(status().isBadRequest());

        // Validate the Kupac in the database
        List<Kupac> kupacList = kupacRepository.findAll();
        assertThat(kupacList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKupacs() throws Exception {
        // Initialize the database
        kupacRepository.saveAndFlush(kupac);

        // Get all the kupacList
        restKupacMockMvc.perform(get("/api/kupacs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kupac.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazivKupca").value(hasItem(DEFAULT_NAZIV_KUPCA.toString())))
            .andExpect(jsonPath("$.[*].pib").value(hasItem(DEFAULT_PIB.toString())));
    }
    
    @Test
    @Transactional
    public void getKupac() throws Exception {
        // Initialize the database
        kupacRepository.saveAndFlush(kupac);

        // Get the kupac
        restKupacMockMvc.perform(get("/api/kupacs/{id}", kupac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kupac.getId().intValue()))
            .andExpect(jsonPath("$.nazivKupca").value(DEFAULT_NAZIV_KUPCA.toString()))
            .andExpect(jsonPath("$.pib").value(DEFAULT_PIB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKupac() throws Exception {
        // Get the kupac
        restKupacMockMvc.perform(get("/api/kupacs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKupac() throws Exception {
        // Initialize the database
        kupacService.save(kupac);

        int databaseSizeBeforeUpdate = kupacRepository.findAll().size();

        // Update the kupac
        Kupac updatedKupac = kupacRepository.findById(kupac.getId()).get();
        // Disconnect from session so that the updates on updatedKupac are not directly saved in db
        em.detach(updatedKupac);
        updatedKupac
            .nazivKupca(UPDATED_NAZIV_KUPCA)
            .pib(UPDATED_PIB);

        restKupacMockMvc.perform(put("/api/kupacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKupac)))
            .andExpect(status().isOk());

        // Validate the Kupac in the database
        List<Kupac> kupacList = kupacRepository.findAll();
        assertThat(kupacList).hasSize(databaseSizeBeforeUpdate);
        Kupac testKupac = kupacList.get(kupacList.size() - 1);
        assertThat(testKupac.getNazivKupca()).isEqualTo(UPDATED_NAZIV_KUPCA);
        assertThat(testKupac.getPib()).isEqualTo(UPDATED_PIB);
    }

    @Test
    @Transactional
    public void updateNonExistingKupac() throws Exception {
        int databaseSizeBeforeUpdate = kupacRepository.findAll().size();

        // Create the Kupac

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKupacMockMvc.perform(put("/api/kupacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kupac)))
            .andExpect(status().isBadRequest());

        // Validate the Kupac in the database
        List<Kupac> kupacList = kupacRepository.findAll();
        assertThat(kupacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKupac() throws Exception {
        // Initialize the database
        kupacService.save(kupac);

        int databaseSizeBeforeDelete = kupacRepository.findAll().size();

        // Delete the kupac
        restKupacMockMvc.perform(delete("/api/kupacs/{id}", kupac.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Kupac> kupacList = kupacRepository.findAll();
        assertThat(kupacList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kupac.class);
        Kupac kupac1 = new Kupac();
        kupac1.setId(1L);
        Kupac kupac2 = new Kupac();
        kupac2.setId(kupac1.getId());
        assertThat(kupac1).isEqualTo(kupac2);
        kupac2.setId(2L);
        assertThat(kupac1).isNotEqualTo(kupac2);
        kupac1.setId(null);
        assertThat(kupac1).isNotEqualTo(kupac2);
    }
}
