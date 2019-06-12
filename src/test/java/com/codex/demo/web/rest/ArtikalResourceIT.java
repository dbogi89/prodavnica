package com.codex.demo.web.rest;

import com.codex.demo.ProdavnicaApp;
import com.codex.demo.domain.Artikal;
import com.codex.demo.repository.ArtikalRepository;
import com.codex.demo.service.ArtikalService;
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
 * Integration tests for the {@Link ArtikalResource} REST controller.
 */
@SpringBootTest(classes = ProdavnicaApp.class)
public class ArtikalResourceIT {

    private static final String DEFAULT_IME_ARTIKLA = "AAAAAAAAAA";
    private static final String UPDATED_IME_ARTIKLA = "BBBBBBBBBB";

    private static final String DEFAULT_BAR_KOD = "AAAAAAAAAA";
    private static final String UPDATED_BAR_KOD = "BBBBBBBBBB";

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private ArtikalService artikalService;

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

    private MockMvc restArtikalMockMvc;

    private Artikal artikal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArtikalResource artikalResource = new ArtikalResource(artikalService);
        this.restArtikalMockMvc = MockMvcBuilders.standaloneSetup(artikalResource)
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
    public static Artikal createEntity(EntityManager em) {
        Artikal artikal = new Artikal()
            .imeArtikla(DEFAULT_IME_ARTIKLA)
            .barKod(DEFAULT_BAR_KOD)
            .cena(DEFAULT_CENA);
        return artikal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artikal createUpdatedEntity(EntityManager em) {
        Artikal artikal = new Artikal()
            .imeArtikla(UPDATED_IME_ARTIKLA)
            .barKod(UPDATED_BAR_KOD)
            .cena(UPDATED_CENA);
        return artikal;
    }

    @BeforeEach
    public void initTest() {
        artikal = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtikal() throws Exception {
        int databaseSizeBeforeCreate = artikalRepository.findAll().size();

        // Create the Artikal
        restArtikalMockMvc.perform(post("/api/artikals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isCreated());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeCreate + 1);
        Artikal testArtikal = artikalList.get(artikalList.size() - 1);
        assertThat(testArtikal.getImeArtikla()).isEqualTo(DEFAULT_IME_ARTIKLA);
        assertThat(testArtikal.getBarKod()).isEqualTo(DEFAULT_BAR_KOD);
        assertThat(testArtikal.getCena()).isEqualTo(DEFAULT_CENA);
    }

    @Test
    @Transactional
    public void createArtikalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artikalRepository.findAll().size();

        // Create the Artikal with an existing ID
        artikal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtikalMockMvc.perform(post("/api/artikals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArtikals() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        // Get all the artikalList
        restArtikalMockMvc.perform(get("/api/artikals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artikal.getId().intValue())))
            .andExpect(jsonPath("$.[*].imeArtikla").value(hasItem(DEFAULT_IME_ARTIKLA.toString())))
            .andExpect(jsonPath("$.[*].barKod").value(hasItem(DEFAULT_BAR_KOD.toString())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getArtikal() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        // Get the artikal
        restArtikalMockMvc.perform(get("/api/artikals/{id}", artikal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artikal.getId().intValue()))
            .andExpect(jsonPath("$.imeArtikla").value(DEFAULT_IME_ARTIKLA.toString()))
            .andExpect(jsonPath("$.barKod").value(DEFAULT_BAR_KOD.toString()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingArtikal() throws Exception {
        // Get the artikal
        restArtikalMockMvc.perform(get("/api/artikals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtikal() throws Exception {
        // Initialize the database
        artikalService.save(artikal);

        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();

        // Update the artikal
        Artikal updatedArtikal = artikalRepository.findById(artikal.getId()).get();
        // Disconnect from session so that the updates on updatedArtikal are not directly saved in db
        em.detach(updatedArtikal);
        updatedArtikal
            .imeArtikla(UPDATED_IME_ARTIKLA)
            .barKod(UPDATED_BAR_KOD)
            .cena(UPDATED_CENA);

        restArtikalMockMvc.perform(put("/api/artikals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtikal)))
            .andExpect(status().isOk());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
        Artikal testArtikal = artikalList.get(artikalList.size() - 1);
        assertThat(testArtikal.getImeArtikla()).isEqualTo(UPDATED_IME_ARTIKLA);
        assertThat(testArtikal.getBarKod()).isEqualTo(UPDATED_BAR_KOD);
        assertThat(testArtikal.getCena()).isEqualTo(UPDATED_CENA);
    }

    @Test
    @Transactional
    public void updateNonExistingArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();

        // Create the Artikal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtikalMockMvc.perform(put("/api/artikals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArtikal() throws Exception {
        // Initialize the database
        artikalService.save(artikal);

        int databaseSizeBeforeDelete = artikalRepository.findAll().size();

        // Delete the artikal
        restArtikalMockMvc.perform(delete("/api/artikals/{id}", artikal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artikal.class);
        Artikal artikal1 = new Artikal();
        artikal1.setId(1L);
        Artikal artikal2 = new Artikal();
        artikal2.setId(artikal1.getId());
        assertThat(artikal1).isEqualTo(artikal2);
        artikal2.setId(2L);
        assertThat(artikal1).isNotEqualTo(artikal2);
        artikal1.setId(null);
        assertThat(artikal1).isNotEqualTo(artikal2);
    }
}
