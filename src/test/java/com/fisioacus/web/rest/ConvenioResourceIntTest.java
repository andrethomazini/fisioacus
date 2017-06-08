package com.fisioacus.web.rest;

import com.fisioacus.FisioacusApp;

import com.fisioacus.domain.Convenio;
import com.fisioacus.repository.ConvenioRepository;
import com.fisioacus.service.ConvenioService;
import com.fisioacus.service.dto.ConvenioDTO;
import com.fisioacus.service.mapper.ConvenioMapper;
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
 * Test class for the ConvenioResource REST controller.
 *
 * @see ConvenioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FisioacusApp.class)
public class ConvenioResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    @Autowired
    private ConvenioRepository convenioRepository;

    @Autowired
    private ConvenioMapper convenioMapper;

    @Autowired
    private ConvenioService convenioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConvenioMockMvc;

    private Convenio convenio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConvenioResource convenioResource = new ConvenioResource(convenioService);
        this.restConvenioMockMvc = MockMvcBuilders.standaloneSetup(convenioResource)
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
    public static Convenio createEntity(EntityManager em) {
        Convenio convenio = new Convenio()
            .descricao(DEFAULT_DESCRICAO)
            .contato(DEFAULT_CONTATO)
            .telefone(DEFAULT_TELEFONE);
        return convenio;
    }

    @Before
    public void initTest() {
        convenio = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvenio() throws Exception {
        int databaseSizeBeforeCreate = convenioRepository.findAll().size();

        // Create the Convenio
        ConvenioDTO convenioDTO = convenioMapper.toDto(convenio);
        restConvenioMockMvc.perform(post("/api/convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDTO)))
            .andExpect(status().isCreated());

        // Validate the Convenio in the database
        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeCreate + 1);
        Convenio testConvenio = convenioList.get(convenioList.size() - 1);
        assertThat(testConvenio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testConvenio.getContato()).isEqualTo(DEFAULT_CONTATO);
        assertThat(testConvenio.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    public void createConvenioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = convenioRepository.findAll().size();

        // Create the Convenio with an existing ID
        convenio.setId(1L);
        ConvenioDTO convenioDTO = convenioMapper.toDto(convenio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConvenioMockMvc.perform(post("/api/convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = convenioRepository.findAll().size();
        // set the field null
        convenio.setDescricao(null);

        // Create the Convenio, which fails.
        ConvenioDTO convenioDTO = convenioMapper.toDto(convenio);

        restConvenioMockMvc.perform(post("/api/convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDTO)))
            .andExpect(status().isBadRequest());

        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = convenioRepository.findAll().size();
        // set the field null
        convenio.setTelefone(null);

        // Create the Convenio, which fails.
        ConvenioDTO convenioDTO = convenioMapper.toDto(convenio);

        restConvenioMockMvc.perform(post("/api/convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDTO)))
            .andExpect(status().isBadRequest());

        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConvenios() throws Exception {
        // Initialize the database
        convenioRepository.saveAndFlush(convenio);

        // Get all the convenioList
        restConvenioMockMvc.perform(get("/api/convenios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convenio.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    @Test
    @Transactional
    public void getConvenio() throws Exception {
        // Initialize the database
        convenioRepository.saveAndFlush(convenio);

        // Get the convenio
        restConvenioMockMvc.perform(get("/api/convenios/{id}", convenio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convenio.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConvenio() throws Exception {
        // Get the convenio
        restConvenioMockMvc.perform(get("/api/convenios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvenio() throws Exception {
        // Initialize the database
        convenioRepository.saveAndFlush(convenio);
        int databaseSizeBeforeUpdate = convenioRepository.findAll().size();

        // Update the convenio
        Convenio updatedConvenio = convenioRepository.findOne(convenio.getId());
        updatedConvenio
            .descricao(UPDATED_DESCRICAO)
            .contato(UPDATED_CONTATO)
            .telefone(UPDATED_TELEFONE);
        ConvenioDTO convenioDTO = convenioMapper.toDto(updatedConvenio);

        restConvenioMockMvc.perform(put("/api/convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDTO)))
            .andExpect(status().isOk());

        // Validate the Convenio in the database
        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeUpdate);
        Convenio testConvenio = convenioList.get(convenioList.size() - 1);
        assertThat(testConvenio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testConvenio.getContato()).isEqualTo(UPDATED_CONTATO);
        assertThat(testConvenio.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void updateNonExistingConvenio() throws Exception {
        int databaseSizeBeforeUpdate = convenioRepository.findAll().size();

        // Create the Convenio
        ConvenioDTO convenioDTO = convenioMapper.toDto(convenio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConvenioMockMvc.perform(put("/api/convenios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDTO)))
            .andExpect(status().isCreated());

        // Validate the Convenio in the database
        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConvenio() throws Exception {
        // Initialize the database
        convenioRepository.saveAndFlush(convenio);
        int databaseSizeBeforeDelete = convenioRepository.findAll().size();

        // Get the convenio
        restConvenioMockMvc.perform(delete("/api/convenios/{id}", convenio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Convenio> convenioList = convenioRepository.findAll();
        assertThat(convenioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convenio.class);
        Convenio convenio1 = new Convenio();
        convenio1.setId(1L);
        Convenio convenio2 = new Convenio();
        convenio2.setId(convenio1.getId());
        assertThat(convenio1).isEqualTo(convenio2);
        convenio2.setId(2L);
        assertThat(convenio1).isNotEqualTo(convenio2);
        convenio1.setId(null);
        assertThat(convenio1).isNotEqualTo(convenio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConvenioDTO.class);
        ConvenioDTO convenioDTO1 = new ConvenioDTO();
        convenioDTO1.setId(1L);
        ConvenioDTO convenioDTO2 = new ConvenioDTO();
        assertThat(convenioDTO1).isNotEqualTo(convenioDTO2);
        convenioDTO2.setId(convenioDTO1.getId());
        assertThat(convenioDTO1).isEqualTo(convenioDTO2);
        convenioDTO2.setId(2L);
        assertThat(convenioDTO1).isNotEqualTo(convenioDTO2);
        convenioDTO1.setId(null);
        assertThat(convenioDTO1).isNotEqualTo(convenioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(convenioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(convenioMapper.fromId(null)).isNull();
    }
}
