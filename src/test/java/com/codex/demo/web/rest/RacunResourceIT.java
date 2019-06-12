package com.codex.demo.web.rest;

import com.codex.demo.ProdavnicaApp;
import com.codex.demo.domain.Racun;
import com.codex.demo.repository.RacunRepository;
import com.codex.demo.service.RacunService;
import com.codex.demo.service.dto.RacunDTO;
import com.codex.demo.service.mapper.RacunMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.codex.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RacunResource} REST controller.
 */
@SpringBootTest(classes = ProdavnicaApp.class)
public class RacunResourceIT {

    private static final String DEFAULT_BROJ_RACUNA = "AAAAAAAAAA";
    private static final String UPDATED_BROJ_RACUNA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RacunRepository racunRepository;

    @Autowired
    private RacunMapper racunMapper;

    @Autowired
    private RacunService racunService;

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

    private MockMvc restRacunMockMvc;

    private Racun racun;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacunResource racunResource = new RacunResource(racunService);
        this.restRacunMockMvc = MockMvcBuilders.standaloneSetup(racunResource)
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
    public static Racun createEntity(EntityManager em) {
        Racun racun = new Racun()
            .brojRacuna(DEFAULT_BROJ_RACUNA)
            .datum(DEFAULT_DATUM);
        return racun;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Racun createUpdatedEntity(EntityManager em) {
        Racun racun = new Racun()
            .brojRacuna(UPDATED_BROJ_RACUNA)
            .datum(UPDATED_DATUM);
        return racun;
    }

    @BeforeEach
    public void initTest() {
        racun = createEntity(em);
    }

    @Test
    @Transactional
    public void createRacun() throws Exception {
        int databaseSizeBeforeCreate = racunRepository.findAll().size();

        // Create the Racun
        RacunDTO racunDTO = racunMapper.toDto(racun);
        restRacunMockMvc.perform(post("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunDTO)))
            .andExpect(status().isCreated());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeCreate + 1);
        Racun testRacun = racunList.get(racunList.size() - 1);
        assertThat(testRacun.getBrojRacuna()).isEqualTo(DEFAULT_BROJ_RACUNA);
        assertThat(testRacun.getDatum()).isEqualTo(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    public void createRacunWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racunRepository.findAll().size();

        // Create the Racun with an existing ID
        racun.setId(1L);
        RacunDTO racunDTO = racunMapper.toDto(racun);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacunMockMvc.perform(post("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRacuns() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);

        // Get all the racunList
        restRacunMockMvc.perform(get("/api/racuns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racun.getId().intValue())))
            .andExpect(jsonPath("$.[*].brojRacuna").value(hasItem(DEFAULT_BROJ_RACUNA.toString())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }
    
    @Test
    @Transactional
    public void getRacun() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);

        // Get the racun
        restRacunMockMvc.perform(get("/api/racuns/{id}", racun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(racun.getId().intValue()))
            .andExpect(jsonPath("$.brojRacuna").value(DEFAULT_BROJ_RACUNA.toString()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRacun() throws Exception {
        // Get the racun
        restRacunMockMvc.perform(get("/api/racuns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacun() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);

        int databaseSizeBeforeUpdate = racunRepository.findAll().size();

        // Update the racun
        Racun updatedRacun = racunRepository.findById(racun.getId()).get();
        // Disconnect from session so that the updates on updatedRacun are not directly saved in db
        em.detach(updatedRacun);
        updatedRacun
            .brojRacuna(UPDATED_BROJ_RACUNA)
            .datum(UPDATED_DATUM);
        RacunDTO racunDTO = racunMapper.toDto(updatedRacun);

        restRacunMockMvc.perform(put("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunDTO)))
            .andExpect(status().isOk());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeUpdate);
        Racun testRacun = racunList.get(racunList.size() - 1);
        assertThat(testRacun.getBrojRacuna()).isEqualTo(UPDATED_BROJ_RACUNA);
        assertThat(testRacun.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    public void updateNonExistingRacun() throws Exception {
        int databaseSizeBeforeUpdate = racunRepository.findAll().size();

        // Create the Racun
        RacunDTO racunDTO = racunMapper.toDto(racun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacunMockMvc.perform(put("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRacun() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);

        int databaseSizeBeforeDelete = racunRepository.findAll().size();

        // Delete the racun
        restRacunMockMvc.perform(delete("/api/racuns/{id}", racun.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Racun.class);
        Racun racun1 = new Racun();
        racun1.setId(1L);
        Racun racun2 = new Racun();
        racun2.setId(racun1.getId());
        assertThat(racun1).isEqualTo(racun2);
        racun2.setId(2L);
        assertThat(racun1).isNotEqualTo(racun2);
        racun1.setId(null);
        assertThat(racun1).isNotEqualTo(racun2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacunDTO.class);
        RacunDTO racunDTO1 = new RacunDTO();
        racunDTO1.setId(1L);
        RacunDTO racunDTO2 = new RacunDTO();
        assertThat(racunDTO1).isNotEqualTo(racunDTO2);
        racunDTO2.setId(racunDTO1.getId());
        assertThat(racunDTO1).isEqualTo(racunDTO2);
        racunDTO2.setId(2L);
        assertThat(racunDTO1).isNotEqualTo(racunDTO2);
        racunDTO1.setId(null);
        assertThat(racunDTO1).isNotEqualTo(racunDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(racunMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(racunMapper.fromId(null)).isNull();
    }
}
