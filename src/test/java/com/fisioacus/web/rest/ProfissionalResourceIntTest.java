package com.fisioacus.web.rest;

import com.fisioacus.FisioacusApp;

import com.fisioacus.domain.Profissional;
import com.fisioacus.repository.ProfissionalRepository;
import com.fisioacus.service.ProfissionalService;
import com.fisioacus.service.dto.ProfissionalDTO;
import com.fisioacus.service.mapper.ProfissionalMapper;
import com.fisioacus.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfissionalResource REST controller.
 *
 * @see ProfissionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FisioacusApp.class)
public class ProfissionalResourceIntTest {

    private static final LocalDate DEFAULT_DT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DT_TERMINO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_TERMINO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ProfissionalMapper profissionalMapper;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfissionalMockMvc;

    private Profissional profissional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfissionalResource profissionalResource = new ProfissionalResource(profissionalService);
        this.restProfissionalMockMvc = MockMvcBuilders.standaloneSetup(profissionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissional createEntity(EntityManager em) {
        Profissional profissional = new Profissional()
            .dtInicio(DEFAULT_DT_INICIO)
            .dtTermino(DEFAULT_DT_TERMINO);
        return profissional;
    }

    @Before
    public void initTest() {
        profissional = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfissional() throws Exception {
        int databaseSizeBeforeCreate = profissionalRepository.findAll().size();

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);
        restProfissionalMockMvc.perform(post("/api/profissionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profissionalDTO)))
            .andExpect(status().isCreated());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeCreate + 1);
        Profissional testProfissional = profissionalList.get(profissionalList.size() - 1);
        assertThat(testProfissional.getDtInicio()).isEqualTo(DEFAULT_DT_INICIO);
        assertThat(testProfissional.getDtTermino()).isEqualTo(DEFAULT_DT_TERMINO);
    }

    @Test
    @Transactional
    public void createProfissionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profissionalRepository.findAll().size();

        // Create the Profissional with an existing ID
        profissional.setId(1L);
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfissionalMockMvc.perform(post("/api/profissionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDtInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = profissionalRepository.findAll().size();
        // set the field null
        profissional.setDtInicio(null);

        // Create the Profissional, which fails.
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        restProfissionalMockMvc.perform(post("/api/profissionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profissionalDTO)))
            .andExpect(status().isBadRequest());

        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfissionals() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);

        // Get all the profissionalList
        restProfissionalMockMvc.perform(get("/api/profissionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissional.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dtTermino").value(hasItem(DEFAULT_DT_TERMINO.toString())));
    }

    @Test
    @Transactional
    public void getProfissional() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);

        // Get the profissional
        restProfissionalMockMvc.perform(get("/api/profissionals/{id}", profissional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profissional.getId().intValue()))
            .andExpect(jsonPath("$.dtInicio").value(DEFAULT_DT_INICIO.toString()))
            .andExpect(jsonPath("$.dtTermino").value(DEFAULT_DT_TERMINO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfissional() throws Exception {
        // Get the profissional
        restProfissionalMockMvc.perform(get("/api/profissionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfissional() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);
        int databaseSizeBeforeUpdate = profissionalRepository.findAll().size();

        // Update the profissional
        Profissional updatedProfissional = profissionalRepository.findOne(profissional.getId());
        updatedProfissional
            .dtInicio(UPDATED_DT_INICIO)
            .dtTermino(UPDATED_DT_TERMINO);
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(updatedProfissional);

        restProfissionalMockMvc.perform(put("/api/profissionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profissionalDTO)))
            .andExpect(status().isOk());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeUpdate);
        Profissional testProfissional = profissionalList.get(profissionalList.size() - 1);
        assertThat(testProfissional.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
        assertThat(testProfissional.getDtTermino()).isEqualTo(UPDATED_DT_TERMINO);
    }

    @Test
    @Transactional
    public void updateNonExistingProfissional() throws Exception {
        int databaseSizeBeforeUpdate = profissionalRepository.findAll().size();

        // Create the Profissional
        ProfissionalDTO profissionalDTO = profissionalMapper.toDto(profissional);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfissionalMockMvc.perform(put("/api/profissionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profissionalDTO)))
            .andExpect(status().isCreated());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfissional() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);
        int databaseSizeBeforeDelete = profissionalRepository.findAll().size();

        // Get the profissional
        restProfissionalMockMvc.perform(delete("/api/profissionals/{id}", profissional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profissional.class);
        Profissional profissional1 = new Profissional();
        profissional1.setId(1L);
        Profissional profissional2 = new Profissional();
        profissional2.setId(profissional1.getId());
        assertThat(profissional1).isEqualTo(profissional2);
        profissional2.setId(2L);
        assertThat(profissional1).isNotEqualTo(profissional2);
        profissional1.setId(null);
        assertThat(profissional1).isNotEqualTo(profissional2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfissionalDTO.class);
        ProfissionalDTO profissionalDTO1 = new ProfissionalDTO();
        profissionalDTO1.setId(1L);
        ProfissionalDTO profissionalDTO2 = new ProfissionalDTO();
        assertThat(profissionalDTO1).isNotEqualTo(profissionalDTO2);
        profissionalDTO2.setId(profissionalDTO1.getId());
        assertThat(profissionalDTO1).isEqualTo(profissionalDTO2);
        profissionalDTO2.setId(2L);
        assertThat(profissionalDTO1).isNotEqualTo(profissionalDTO2);
        profissionalDTO1.setId(null);
        assertThat(profissionalDTO1).isNotEqualTo(profissionalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profissionalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profissionalMapper.fromId(null)).isNull();
    }
}
