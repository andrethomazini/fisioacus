package com.fisioacus.web.rest;

import com.fisioacus.FisioacusApp;

import com.fisioacus.domain.Atendimento;
import com.fisioacus.repository.AtendimentoRepository;
import com.fisioacus.service.AtendimentoService;
import com.fisioacus.service.dto.AtendimentoDTO;
import com.fisioacus.service.mapper.AtendimentoMapper;
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
 * Test class for the AtendimentoResource REST controller.
 *
 * @see AtendimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FisioacusApp.class)
public class AtendimentoResourceIntTest {

    private static final String DEFAULT_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CARTAO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CARTAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESCONTO = false;
    private static final Boolean UPDATED_DESCONTO = true;

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final String DEFAULT_QUEIXA_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_QUEIXA_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_HIPOTESE_DIAGNOSTICA = "AAAAAAAAAA";
    private static final String UPDATED_HIPOTESE_DIAGNOSTICA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_AUTENTICADOR = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_AUTENTICADOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_TERMINO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_TERMINO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QUANTIDADE_SESSOES = 1;
    private static final Integer UPDATED_QUANTIDADE_SESSOES = 2;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CID = "AAAAAAAAAA";
    private static final String UPDATED_CID = "BBBBBBBBBB";

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private AtendimentoMapper atendimentoMapper;

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAtendimentoMockMvc;

    private Atendimento atendimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AtendimentoResource atendimentoResource = new AtendimentoResource(atendimentoService);
        this.restAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(atendimentoResource)
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
    public static Atendimento createEntity(EntityManager em) {
        Atendimento atendimento = new Atendimento()
            .medico(DEFAULT_MEDICO)
            .numeroCartao(DEFAULT_NUMERO_CARTAO)
            .desconto(DEFAULT_DESCONTO)
            .valor(DEFAULT_VALOR)
            .queixaPrincipal(DEFAULT_QUEIXA_PRINCIPAL)
            .hipoteseDiagnostica(DEFAULT_HIPOTESE_DIAGNOSTICA)
            .dtInicio(DEFAULT_DT_INICIO)
            .numeroAutenticador(DEFAULT_NUMERO_AUTENTICADOR)
            .dtTermino(DEFAULT_DT_TERMINO)
            .quantidadeSessoes(DEFAULT_QUANTIDADE_SESSOES)
            .observacao(DEFAULT_OBSERVACAO)
            .cid(DEFAULT_CID);
        return atendimento;
    }

    @Before
    public void initTest() {
        atendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtendimento() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);
        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getMedico()).isEqualTo(DEFAULT_MEDICO);
        assertThat(testAtendimento.getNumeroCartao()).isEqualTo(DEFAULT_NUMERO_CARTAO);
        assertThat(testAtendimento.isDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testAtendimento.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testAtendimento.getQueixaPrincipal()).isEqualTo(DEFAULT_QUEIXA_PRINCIPAL);
        assertThat(testAtendimento.getHipoteseDiagnostica()).isEqualTo(DEFAULT_HIPOTESE_DIAGNOSTICA);
        assertThat(testAtendimento.getDtInicio()).isEqualTo(DEFAULT_DT_INICIO);
        assertThat(testAtendimento.getNumeroAutenticador()).isEqualTo(DEFAULT_NUMERO_AUTENTICADOR);
        assertThat(testAtendimento.getDtTermino()).isEqualTo(DEFAULT_DT_TERMINO);
        assertThat(testAtendimento.getQuantidadeSessoes()).isEqualTo(DEFAULT_QUANTIDADE_SESSOES);
        assertThat(testAtendimento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testAtendimento.getCid()).isEqualTo(DEFAULT_CID);
    }

    @Test
    @Transactional
    public void createAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // Create the Atendimento with an existing ID
        atendimento.setId(1L);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAtendimentos() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList
        restAtendimentoMockMvc.perform(get("/api/atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].medico").value(hasItem(DEFAULT_MEDICO.toString())))
            .andExpect(jsonPath("$.[*].numeroCartao").value(hasItem(DEFAULT_NUMERO_CARTAO.toString())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].queixaPrincipal").value(hasItem(DEFAULT_QUEIXA_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].hipoteseDiagnostica").value(hasItem(DEFAULT_HIPOTESE_DIAGNOSTICA.toString())))
            .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].numeroAutenticador").value(hasItem(DEFAULT_NUMERO_AUTENTICADOR.toString())))
            .andExpect(jsonPath("$.[*].dtTermino").value(hasItem(DEFAULT_DT_TERMINO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeSessoes").value(hasItem(DEFAULT_QUANTIDADE_SESSOES)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].cid").value(hasItem(DEFAULT_CID.toString())));
    }

    @Test
    @Transactional
    public void getAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get the atendimento
        restAtendimentoMockMvc.perform(get("/api/atendimentos/{id}", atendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atendimento.getId().intValue()))
            .andExpect(jsonPath("$.medico").value(DEFAULT_MEDICO.toString()))
            .andExpect(jsonPath("$.numeroCartao").value(DEFAULT_NUMERO_CARTAO.toString()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.booleanValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.queixaPrincipal").value(DEFAULT_QUEIXA_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.hipoteseDiagnostica").value(DEFAULT_HIPOTESE_DIAGNOSTICA.toString()))
            .andExpect(jsonPath("$.dtInicio").value(DEFAULT_DT_INICIO.toString()))
            .andExpect(jsonPath("$.numeroAutenticador").value(DEFAULT_NUMERO_AUTENTICADOR.toString()))
            .andExpect(jsonPath("$.dtTermino").value(DEFAULT_DT_TERMINO.toString()))
            .andExpect(jsonPath("$.quantidadeSessoes").value(DEFAULT_QUANTIDADE_SESSOES))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.cid").value(DEFAULT_CID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAtendimento() throws Exception {
        // Get the atendimento
        restAtendimentoMockMvc.perform(get("/api/atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Update the atendimento
        Atendimento updatedAtendimento = atendimentoRepository.findOne(atendimento.getId());
        updatedAtendimento
            .medico(UPDATED_MEDICO)
            .numeroCartao(UPDATED_NUMERO_CARTAO)
            .desconto(UPDATED_DESCONTO)
            .valor(UPDATED_VALOR)
            .queixaPrincipal(UPDATED_QUEIXA_PRINCIPAL)
            .hipoteseDiagnostica(UPDATED_HIPOTESE_DIAGNOSTICA)
            .dtInicio(UPDATED_DT_INICIO)
            .numeroAutenticador(UPDATED_NUMERO_AUTENTICADOR)
            .dtTermino(UPDATED_DT_TERMINO)
            .quantidadeSessoes(UPDATED_QUANTIDADE_SESSOES)
            .observacao(UPDATED_OBSERVACAO)
            .cid(UPDATED_CID);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(updatedAtendimento);

        restAtendimentoMockMvc.perform(put("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getMedico()).isEqualTo(UPDATED_MEDICO);
        assertThat(testAtendimento.getNumeroCartao()).isEqualTo(UPDATED_NUMERO_CARTAO);
        assertThat(testAtendimento.isDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testAtendimento.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testAtendimento.getQueixaPrincipal()).isEqualTo(UPDATED_QUEIXA_PRINCIPAL);
        assertThat(testAtendimento.getHipoteseDiagnostica()).isEqualTo(UPDATED_HIPOTESE_DIAGNOSTICA);
        assertThat(testAtendimento.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
        assertThat(testAtendimento.getNumeroAutenticador()).isEqualTo(UPDATED_NUMERO_AUTENTICADOR);
        assertThat(testAtendimento.getDtTermino()).isEqualTo(UPDATED_DT_TERMINO);
        assertThat(testAtendimento.getQuantidadeSessoes()).isEqualTo(UPDATED_QUANTIDADE_SESSOES);
        assertThat(testAtendimento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testAtendimento.getCid()).isEqualTo(UPDATED_CID);
    }

    @Test
    @Transactional
    public void updateNonExistingAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAtendimentoMockMvc.perform(put("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        int databaseSizeBeforeDelete = atendimentoRepository.findAll().size();

        // Get the atendimento
        restAtendimentoMockMvc.perform(delete("/api/atendimentos/{id}", atendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atendimento.class);
        Atendimento atendimento1 = new Atendimento();
        atendimento1.setId(1L);
        Atendimento atendimento2 = new Atendimento();
        atendimento2.setId(atendimento1.getId());
        assertThat(atendimento1).isEqualTo(atendimento2);
        atendimento2.setId(2L);
        assertThat(atendimento1).isNotEqualTo(atendimento2);
        atendimento1.setId(null);
        assertThat(atendimento1).isNotEqualTo(atendimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtendimentoDTO.class);
        AtendimentoDTO atendimentoDTO1 = new AtendimentoDTO();
        atendimentoDTO1.setId(1L);
        AtendimentoDTO atendimentoDTO2 = new AtendimentoDTO();
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
        atendimentoDTO2.setId(atendimentoDTO1.getId());
        assertThat(atendimentoDTO1).isEqualTo(atendimentoDTO2);
        atendimentoDTO2.setId(2L);
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
        atendimentoDTO1.setId(null);
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(atendimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(atendimentoMapper.fromId(null)).isNull();
    }
}
