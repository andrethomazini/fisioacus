package com.fisioacus.web.rest;

import com.fisioacus.FisioacusApp;

import com.fisioacus.domain.Procedimento;
import com.fisioacus.repository.ProcedimentoRepository;
import com.fisioacus.service.ProcedimentoService;
import com.fisioacus.service.dto.ProcedimentoDTO;
import com.fisioacus.service.mapper.ProcedimentoMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProcedimentoResource REST controller.
 *
 * @see ProcedimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FisioacusApp.class)
public class ProcedimentoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private ProcedimentoMapper procedimentoMapper;

    @Autowired
    private ProcedimentoService procedimentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcedimentoMockMvc;

    private Procedimento procedimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcedimentoResource procedimentoResource = new ProcedimentoResource(procedimentoService);
        this.restProcedimentoMockMvc = MockMvcBuilders.standaloneSetup(procedimentoResource)
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
    public static Procedimento createEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento()
            .descricao(DEFAULT_DESCRICAO)
            .duration(DEFAULT_DURATION);
        return procedimento;
    }

    @Before
    public void initTest() {
        procedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedimento() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);
        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProcedimento.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createProcedimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento with an existing ID
        procedimento.setId(1L);
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setDescricao(null);

        // Create the Procedimento, which fails.
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setDuration(null);

        // Create the Procedimento, which fails.
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedimentos() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get all the procedimentoList
        restProcedimentoMockMvc.perform(get("/api/procedimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)));
    }

    @Test
    @Transactional
    public void getProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", procedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingProcedimento() throws Exception {
        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento
        Procedimento updatedProcedimento = procedimentoRepository.findOne(procedimento.getId());
        updatedProcedimento
            .descricao(UPDATED_DESCRICAO)
            .duration(UPDATED_DURATION);
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(updatedProcedimento);

        restProcedimentoMockMvc.perform(put("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcedimento.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcedimentoMockMvc.perform(put("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);
        int databaseSizeBeforeDelete = procedimentoRepository.findAll().size();

        // Get the procedimento
        restProcedimentoMockMvc.perform(delete("/api/procedimentos/{id}", procedimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedimento.class);
        Procedimento procedimento1 = new Procedimento();
        procedimento1.setId(1L);
        Procedimento procedimento2 = new Procedimento();
        procedimento2.setId(procedimento1.getId());
        assertThat(procedimento1).isEqualTo(procedimento2);
        procedimento2.setId(2L);
        assertThat(procedimento1).isNotEqualTo(procedimento2);
        procedimento1.setId(null);
        assertThat(procedimento1).isNotEqualTo(procedimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedimentoDTO.class);
        ProcedimentoDTO procedimentoDTO1 = new ProcedimentoDTO();
        procedimentoDTO1.setId(1L);
        ProcedimentoDTO procedimentoDTO2 = new ProcedimentoDTO();
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(procedimentoDTO1.getId());
        assertThat(procedimentoDTO1).isEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(2L);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO1.setId(null);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procedimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procedimentoMapper.fromId(null)).isNull();
    }
}
