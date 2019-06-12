package com.codex.demo.web.rest;

import com.codex.demo.ProdavnicaApp;
import com.codex.demo.domain.Adresa;
import com.codex.demo.repository.AdresaRepository;
import com.codex.demo.service.AdresaService;
import com.codex.demo.service.dto.AdresaDTO;
import com.codex.demo.service.mapper.AdresaMapper;
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
 * Integration tests for the {@Link AdresaResource} REST controller.
 */
@SpringBootTest(classes = ProdavnicaApp.class)
public class AdresaResourceIT {

    private static final String DEFAULT_ULICA = "AAAAAAAAAA";
    private static final String UPDATED_ULICA = "BBBBBBBBBB";

    private static final Long DEFAULT_PTT = 1L;
    private static final Long UPDATED_PTT = 2L;

    @Autowired
    private AdresaRepository adresaRepository;

    @Autowired
    private AdresaMapper adresaMapper;

    @Autowired
    private AdresaService adresaService;

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

    private MockMvc restAdresaMockMvc;

    private Adresa adresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdresaResource adresaResource = new AdresaResource(adresaService);
        this.restAdresaMockMvc = MockMvcBuilders.standaloneSetup(adresaResource)
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
    public static Adresa createEntity(EntityManager em) {
        Adresa adresa = new Adresa()
            .ulica(DEFAULT_ULICA)
            .ptt(DEFAULT_PTT);
        return adresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresa createUpdatedEntity(EntityManager em) {
        Adresa adresa = new Adresa()
            .ulica(UPDATED_ULICA)
            .ptt(UPDATED_PTT);
        return adresa;
    }

    @BeforeEach
    public void initTest() {
        adresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresa() throws Exception {
        int databaseSizeBeforeCreate = adresaRepository.findAll().size();

        // Create the Adresa
        AdresaDTO adresaDTO = adresaMapper.toDto(adresa);
        restAdresaMockMvc.perform(post("/api/adresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Adresa in the database
        List<Adresa> adresaList = adresaRepository.findAll();
        assertThat(adresaList).hasSize(databaseSizeBeforeCreate + 1);
        Adresa testAdresa = adresaList.get(adresaList.size() - 1);
        assertThat(testAdresa.getUlica()).isEqualTo(DEFAULT_ULICA);
        assertThat(testAdresa.getPtt()).isEqualTo(DEFAULT_PTT);
    }

    @Test
    @Transactional
    public void createAdresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresaRepository.findAll().size();

        // Create the Adresa with an existing ID
        adresa.setId(1L);
        AdresaDTO adresaDTO = adresaMapper.toDto(adresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresaMockMvc.perform(post("/api/adresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresa in the database
        List<Adresa> adresaList = adresaRepository.findAll();
        assertThat(adresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdresas() throws Exception {
        // Initialize the database
        adresaRepository.saveAndFlush(adresa);

        // Get all the adresaList
        restAdresaMockMvc.perform(get("/api/adresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ulica").value(hasItem(DEFAULT_ULICA.toString())))
            .andExpect(jsonPath("$.[*].ptt").value(hasItem(DEFAULT_PTT.intValue())));
    }
    
    @Test
    @Transactional
    public void getAdresa() throws Exception {
        // Initialize the database
        adresaRepository.saveAndFlush(adresa);

        // Get the adresa
        restAdresaMockMvc.perform(get("/api/adresas/{id}", adresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adresa.getId().intValue()))
            .andExpect(jsonPath("$.ulica").value(DEFAULT_ULICA.toString()))
            .andExpect(jsonPath("$.ptt").value(DEFAULT_PTT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdresa() throws Exception {
        // Get the adresa
        restAdresaMockMvc.perform(get("/api/adresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresa() throws Exception {
        // Initialize the database
        adresaRepository.saveAndFlush(adresa);

        int databaseSizeBeforeUpdate = adresaRepository.findAll().size();

        // Update the adresa
        Adresa updatedAdresa = adresaRepository.findById(adresa.getId()).get();
        // Disconnect from session so that the updates on updatedAdresa are not directly saved in db
        em.detach(updatedAdresa);
        updatedAdresa
            .ulica(UPDATED_ULICA)
            .ptt(UPDATED_PTT);
        AdresaDTO adresaDTO = adresaMapper.toDto(updatedAdresa);

        restAdresaMockMvc.perform(put("/api/adresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresaDTO)))
            .andExpect(status().isOk());

        // Validate the Adresa in the database
        List<Adresa> adresaList = adresaRepository.findAll();
        assertThat(adresaList).hasSize(databaseSizeBeforeUpdate);
        Adresa testAdresa = adresaList.get(adresaList.size() - 1);
        assertThat(testAdresa.getUlica()).isEqualTo(UPDATED_ULICA);
        assertThat(testAdresa.getPtt()).isEqualTo(UPDATED_PTT);
    }

    @Test
    @Transactional
    public void updateNonExistingAdresa() throws Exception {
        int databaseSizeBeforeUpdate = adresaRepository.findAll().size();

        // Create the Adresa
        AdresaDTO adresaDTO = adresaMapper.toDto(adresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresaMockMvc.perform(put("/api/adresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresa in the database
        List<Adresa> adresaList = adresaRepository.findAll();
        assertThat(adresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdresa() throws Exception {
        // Initialize the database
        adresaRepository.saveAndFlush(adresa);

        int databaseSizeBeforeDelete = adresaRepository.findAll().size();

        // Delete the adresa
        restAdresaMockMvc.perform(delete("/api/adresas/{id}", adresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Adresa> adresaList = adresaRepository.findAll();
        assertThat(adresaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresa.class);
        Adresa adresa1 = new Adresa();
        adresa1.setId(1L);
        Adresa adresa2 = new Adresa();
        adresa2.setId(adresa1.getId());
        assertThat(adresa1).isEqualTo(adresa2);
        adresa2.setId(2L);
        assertThat(adresa1).isNotEqualTo(adresa2);
        adresa1.setId(null);
        assertThat(adresa1).isNotEqualTo(adresa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdresaDTO.class);
        AdresaDTO adresaDTO1 = new AdresaDTO();
        adresaDTO1.setId(1L);
        AdresaDTO adresaDTO2 = new AdresaDTO();
        assertThat(adresaDTO1).isNotEqualTo(adresaDTO2);
        adresaDTO2.setId(adresaDTO1.getId());
        assertThat(adresaDTO1).isEqualTo(adresaDTO2);
        adresaDTO2.setId(2L);
        assertThat(adresaDTO1).isNotEqualTo(adresaDTO2);
        adresaDTO1.setId(null);
        assertThat(adresaDTO1).isNotEqualTo(adresaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adresaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adresaMapper.fromId(null)).isNull();
    }
}
