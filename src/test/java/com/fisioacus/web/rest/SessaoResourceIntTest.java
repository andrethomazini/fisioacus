package com.fisioacus.web.rest;

import com.fisioacus.FisioacusApp;

import com.fisioacus.domain.Sessao;
import com.fisioacus.repository.SessaoRepository;
import com.fisioacus.service.SessaoService;
import com.fisioacus.service.dto.SessaoDTO;
import com.fisioacus.service.mapper.SessaoMapper;
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
 * Test class for the SessaoResource REST controller.
 *
 * @see SessaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FisioacusApp.class)
public class SessaoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_INICIO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private SessaoMapper sessaoMapper;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSessaoMockMvc;

    private Sessao sessao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SessaoResource sessaoResource = new SessaoResource(sessaoService);
        this.restSessaoMockMvc = MockMvcBuilders.standaloneSetup(sessaoResource)
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
    public static Sessao createEntity(EntityManager em) {
        Sessao sessao = new Sessao()
            .descricao(DEFAULT_DESCRICAO)
            .dtInicio(DEFAULT_DT_INICIO);
        return sessao;
    }

    @Before
    public void initTest() {
        sessao = createEntity(em);
    }

    @Test
    @Transactional
    public void createSessao() throws Exception {
        int databaseSizeBeforeCreate = sessaoRepository.findAll().size();

        // Create the Sessao
        SessaoDTO sessaoDTO = sessaoMapper.toDto(sessao);
        restSessaoMockMvc.perform(post("/api/sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Sessao in the database
        List<Sessao> sessaoList = sessaoRepository.findAll();
        assertThat(sessaoList).hasSize(databaseSizeBeforeCreate + 1);
        Sessao testSessao = sessaoList.get(sessaoList.size() - 1);
        assertThat(testSessao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testSessao.getDtInicio()).isEqualTo(DEFAULT_DT_INICIO);
    }

    @Test
    @Transactional
    public void createSessaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessaoRepository.findAll().size();

        // Create the Sessao with an existing ID
        sessao.setId(1L);
        SessaoDTO sessaoDTO = sessaoMapper.toDto(sessao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessaoMockMvc.perform(post("/api/sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sessao> sessaoList = sessaoRepository.findAll();
        assertThat(sessaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDtInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessaoRepository.findAll().size();
        // set the field null
        sessao.setDtInicio(null);

        // Create the Sessao, which fails.
        SessaoDTO sessaoDTO = sessaoMapper.toDto(sessao);

        restSessaoMockMvc.perform(post("/api/sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessaoDTO)))
            .andExpect(status().isBadRequest());

        List<Sessao> sessaoList = sessaoRepository.findAll();
        assertThat(sessaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSessaos() throws Exception {
        // Initialize the database
        sessaoRepository.saveAndFlush(sessao);

        // Get all the sessaoList
        restSessaoMockMvc.perform(get("/api/sessaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())));
    }

    @Test
    @Transactional
    public void getSessao() throws Exception {
        // Initialize the database
        sessaoRepository.saveAndFlush(sessao);

        // Get the sessao
        restSessaoMockMvc.perform(get("/api/sessaos/{id}", sessao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sessao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dtInicio").value(DEFAULT_DT_INICIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSessao() throws Exception {
        // Get the sessao
        restSessaoMockMvc.perform(get("/api/sessaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSessao() throws Exception {
        // Initialize the database
        sessaoRepository.saveAndFlush(sessao);
        int databaseSizeBeforeUpdate = sessaoRepository.findAll().size();

        // Update the sessao
        Sessao updatedSessao = sessaoRepository.findOne(sessao.getId());
        updatedSessao
            .descricao(UPDATED_DESCRICAO)
            .dtInicio(UPDATED_DT_INICIO);
        SessaoDTO sessaoDTO = sessaoMapper.toDto(updatedSessao);

        restSessaoMockMvc.perform(put("/api/sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessaoDTO)))
            .andExpect(status().isOk());

        // Validate the Sessao in the database
        List<Sessao> sessaoList = sessaoRepository.findAll();
        assertThat(sessaoList).hasSize(databaseSizeBeforeUpdate);
        Sessao testSessao = sessaoList.get(sessaoList.size() - 1);
        assertThat(testSessao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSessao.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
    }

    @Test
    @Transactional
    public void updateNonExistingSessao() throws Exception {
        int databaseSizeBeforeUpdate = sessaoRepository.findAll().size();

        // Create the Sessao
        SessaoDTO sessaoDTO = sessaoMapper.toDto(sessao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSessaoMockMvc.perform(put("/api/sessaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Sessao in the database
        List<Sessao> sessaoList = sessaoRepository.findAll();
        assertThat(sessaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSessao() throws Exception {
        // Initialize the database
        sessaoRepository.saveAndFlush(sessao);
        int databaseSizeBeforeDelete = sessaoRepository.findAll().size();

        // Get the sessao
        restSessaoMockMvc.perform(delete("/api/sessaos/{id}", sessao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sessao> sessaoList = sessaoRepository.findAll();
        assertThat(sessaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sessao.class);
        Sessao sessao1 = new Sessao();
        sessao1.setId(1L);
        Sessao sessao2 = new Sessao();
        sessao2.setId(sessao1.getId());
        assertThat(sessao1).isEqualTo(sessao2);
        sessao2.setId(2L);
        assertThat(sessao1).isNotEqualTo(sessao2);
        sessao1.setId(null);
        assertThat(sessao1).isNotEqualTo(sessao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessaoDTO.class);
        SessaoDTO sessaoDTO1 = new SessaoDTO();
        sessaoDTO1.setId(1L);
        SessaoDTO sessaoDTO2 = new SessaoDTO();
        assertThat(sessaoDTO1).isNotEqualTo(sessaoDTO2);
        sessaoDTO2.setId(sessaoDTO1.getId());
        assertThat(sessaoDTO1).isEqualTo(sessaoDTO2);
        sessaoDTO2.setId(2L);
        assertThat(sessaoDTO1).isNotEqualTo(sessaoDTO2);
        sessaoDTO1.setId(null);
        assertThat(sessaoDTO1).isNotEqualTo(sessaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sessaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sessaoMapper.fromId(null)).isNull();
    }
}
