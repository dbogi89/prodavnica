package com.codex.demo.web.rest;

import com.codex.demo.ProdavnicaApp;
import com.codex.demo.domain.RacunStavke;
import com.codex.demo.repository.RacunStavkeRepository;
import com.codex.demo.service.RacunStavkeService;
import com.codex.demo.service.dto.RacunStavkeDTO;
import com.codex.demo.service.mapper.RacunStavkeMapper;
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
 * Integration tests for the {@Link RacunStavkeResource} REST controller.
 */
@SpringBootTest(classes = ProdavnicaApp.class)
public class RacunStavkeResourceIT {

    private static final Integer DEFAULT_REDNI_BROJ_STAVKE = 1;
    private static final Integer UPDATED_REDNI_BROJ_STAVKE = 2;

    private static final Integer DEFAULT_KOLICINA = 1;
    private static final Integer UPDATED_KOLICINA = 2;

    @Autowired
    private RacunStavkeRepository racunStavkeRepository;

    @Autowired
    private RacunStavkeMapper racunStavkeMapper;

    @Autowired
    private RacunStavkeService racunStavkeService;

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

    private MockMvc restRacunStavkeMockMvc;

    private RacunStavke racunStavke;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacunStavkeResource racunStavkeResource = new RacunStavkeResource(racunStavkeService);
        this.restRacunStavkeMockMvc = MockMvcBuilders.standaloneSetup(racunStavkeResource)
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
    public static RacunStavke createEntity(EntityManager em) {
        RacunStavke racunStavke = new RacunStavke()
            .redniBrojStavke(DEFAULT_REDNI_BROJ_STAVKE)
            .kolicina(DEFAULT_KOLICINA);
        return racunStavke;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RacunStavke createUpdatedEntity(EntityManager em) {
        RacunStavke racunStavke = new RacunStavke()
            .redniBrojStavke(UPDATED_REDNI_BROJ_STAVKE)
            .kolicina(UPDATED_KOLICINA);
        return racunStavke;
    }

    @BeforeEach
    public void initTest() {
        racunStavke = createEntity(em);
    }

    @Test
    @Transactional
    public void createRacunStavke() throws Exception {
        int databaseSizeBeforeCreate = racunStavkeRepository.findAll().size();

        // Create the RacunStavke
        RacunStavkeDTO racunStavkeDTO = racunStavkeMapper.toDto(racunStavke);
        restRacunStavkeMockMvc.perform(post("/api/racun-stavkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunStavkeDTO)))
            .andExpect(status().isCreated());

        // Validate the RacunStavke in the database
        List<RacunStavke> racunStavkeList = racunStavkeRepository.findAll();
        assertThat(racunStavkeList).hasSize(databaseSizeBeforeCreate + 1);
        RacunStavke testRacunStavke = racunStavkeList.get(racunStavkeList.size() - 1);
        assertThat(testRacunStavke.getRedniBrojStavke()).isEqualTo(DEFAULT_REDNI_BROJ_STAVKE);
        assertThat(testRacunStavke.getKolicina()).isEqualTo(DEFAULT_KOLICINA);
    }

    @Test
    @Transactional
    public void createRacunStavkeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racunStavkeRepository.findAll().size();

        // Create the RacunStavke with an existing ID
        racunStavke.setId(1L);
        RacunStavkeDTO racunStavkeDTO = racunStavkeMapper.toDto(racunStavke);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacunStavkeMockMvc.perform(post("/api/racun-stavkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunStavkeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RacunStavke in the database
        List<RacunStavke> racunStavkeList = racunStavkeRepository.findAll();
        assertThat(racunStavkeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRedniBrojStavkeIsRequired() throws Exception {
        int databaseSizeBeforeTest = racunStavkeRepository.findAll().size();
        // set the field null
        racunStavke.setRedniBrojStavke(null);

        // Create the RacunStavke, which fails.
        RacunStavkeDTO racunStavkeDTO = racunStavkeMapper.toDto(racunStavke);

        restRacunStavkeMockMvc.perform(post("/api/racun-stavkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunStavkeDTO)))
            .andExpect(status().isBadRequest());

        List<RacunStavke> racunStavkeList = racunStavkeRepository.findAll();
        assertThat(racunStavkeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRacunStavkes() throws Exception {
        // Initialize the database
        racunStavkeRepository.saveAndFlush(racunStavke);

        // Get all the racunStavkeList
        restRacunStavkeMockMvc.perform(get("/api/racun-stavkes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racunStavke.getId().intValue())))
            .andExpect(jsonPath("$.[*].redniBrojStavke").value(hasItem(DEFAULT_REDNI_BROJ_STAVKE)))
            .andExpect(jsonPath("$.[*].kolicina").value(hasItem(DEFAULT_KOLICINA)));
    }
    
    @Test
    @Transactional
    public void getRacunStavke() throws Exception {
        // Initialize the database
        racunStavkeRepository.saveAndFlush(racunStavke);

        // Get the racunStavke
        restRacunStavkeMockMvc.perform(get("/api/racun-stavkes/{id}", racunStavke.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(racunStavke.getId().intValue()))
            .andExpect(jsonPath("$.redniBrojStavke").value(DEFAULT_REDNI_BROJ_STAVKE))
            .andExpect(jsonPath("$.kolicina").value(DEFAULT_KOLICINA));
    }

    @Test
    @Transactional
    public void getNonExistingRacunStavke() throws Exception {
        // Get the racunStavke
        restRacunStavkeMockMvc.perform(get("/api/racun-stavkes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacunStavke() throws Exception {
        // Initialize the database
        racunStavkeRepository.saveAndFlush(racunStavke);

        int databaseSizeBeforeUpdate = racunStavkeRepository.findAll().size();

        // Update the racunStavke
        RacunStavke updatedRacunStavke = racunStavkeRepository.findById(racunStavke.getId()).get();
        // Disconnect from session so that the updates on updatedRacunStavke are not directly saved in db
        em.detach(updatedRacunStavke);
        updatedRacunStavke
            .redniBrojStavke(UPDATED_REDNI_BROJ_STAVKE)
            .kolicina(UPDATED_KOLICINA);
        RacunStavkeDTO racunStavkeDTO = racunStavkeMapper.toDto(updatedRacunStavke);

        restRacunStavkeMockMvc.perform(put("/api/racun-stavkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunStavkeDTO)))
            .andExpect(status().isOk());

        // Validate the RacunStavke in the database
        List<RacunStavke> racunStavkeList = racunStavkeRepository.findAll();
        assertThat(racunStavkeList).hasSize(databaseSizeBeforeUpdate);
        RacunStavke testRacunStavke = racunStavkeList.get(racunStavkeList.size() - 1);
        assertThat(testRacunStavke.getRedniBrojStavke()).isEqualTo(UPDATED_REDNI_BROJ_STAVKE);
        assertThat(testRacunStavke.getKolicina()).isEqualTo(UPDATED_KOLICINA);
    }

    @Test
    @Transactional
    public void updateNonExistingRacunStavke() throws Exception {
        int databaseSizeBeforeUpdate = racunStavkeRepository.findAll().size();

        // Create the RacunStavke
        RacunStavkeDTO racunStavkeDTO = racunStavkeMapper.toDto(racunStavke);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacunStavkeMockMvc.perform(put("/api/racun-stavkes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racunStavkeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RacunStavke in the database
        List<RacunStavke> racunStavkeList = racunStavkeRepository.findAll();
        assertThat(racunStavkeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRacunStavke() throws Exception {
        // Initialize the database
        racunStavkeRepository.saveAndFlush(racunStavke);

        int databaseSizeBeforeDelete = racunStavkeRepository.findAll().size();

        // Delete the racunStavke
        restRacunStavkeMockMvc.perform(delete("/api/racun-stavkes/{id}", racunStavke.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RacunStavke> racunStavkeList = racunStavkeRepository.findAll();
        assertThat(racunStavkeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacunStavke.class);
        RacunStavke racunStavke1 = new RacunStavke();
        racunStavke1.setId(1L);
        RacunStavke racunStavke2 = new RacunStavke();
        racunStavke2.setId(racunStavke1.getId());
        assertThat(racunStavke1).isEqualTo(racunStavke2);
        racunStavke2.setId(2L);
        assertThat(racunStavke1).isNotEqualTo(racunStavke2);
        racunStavke1.setId(null);
        assertThat(racunStavke1).isNotEqualTo(racunStavke2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacunStavkeDTO.class);
        RacunStavkeDTO racunStavkeDTO1 = new RacunStavkeDTO();
        racunStavkeDTO1.setId(1L);
        RacunStavkeDTO racunStavkeDTO2 = new RacunStavkeDTO();
        assertThat(racunStavkeDTO1).isNotEqualTo(racunStavkeDTO2);
        racunStavkeDTO2.setId(racunStavkeDTO1.getId());
        assertThat(racunStavkeDTO1).isEqualTo(racunStavkeDTO2);
        racunStavkeDTO2.setId(2L);
        assertThat(racunStavkeDTO1).isNotEqualTo(racunStavkeDTO2);
        racunStavkeDTO1.setId(null);
        assertThat(racunStavkeDTO1).isNotEqualTo(racunStavkeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(racunStavkeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(racunStavkeMapper.fromId(null)).isNull();
    }
}
