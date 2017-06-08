package com.fisioacus.web.rest;

import com.fisioacus.FisioacusApp;

import com.fisioacus.domain.Pessoa;
import com.fisioacus.repository.PessoaRepository;
import com.fisioacus.service.PessoaService;
import com.fisioacus.service.dto.PessoaDTO;
import com.fisioacus.service.mapper.PessoaMapper;
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

import com.fisioacus.domain.enumeration.Genero;
/**
 * Test class for the PessoaResource REST controller.
 *
 * @see PessoaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FisioacusApp.class)
public class PessoaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Genero DEFAULT_GENERO = Genero.MASCULINO;
    private static final Genero UPDATED_GENERO = Genero.FEMININO;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PessoaResource pessoaResource = new PessoaResource(pessoaService);
        this.restPessoaMockMvc = MockMvcBuilders.standaloneSetup(pessoaResource)
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
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .celular(DEFAULT_CELULAR)
            .endereco(DEFAULT_ENDERECO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .cep(DEFAULT_CEP)
            .contato(DEFAULT_CONTATO)
            .dtNascimento(DEFAULT_DT_NASCIMENTO)
            .genero(DEFAULT_GENERO)
            .email(DEFAULT_EMAIL);
        return pessoa;
    }

    @Before
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testPessoa.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testPessoa.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testPessoa.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPessoa.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testPessoa.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testPessoa.getContato()).isEqualTo(DEFAULT_CONTATO);
        assertThat(testPessoa.getDtNascimento()).isEqualTo(DEFAULT_DT_NASCIMENTO);
        assertThat(testPessoa.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPessoa.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa with an existing ID
        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setCelular(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setEndereco(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNumero(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setCep(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDtNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setDtNascimento(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO.toString())))
            .andExpect(jsonPath("$.[*].dtNascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO.toString()))
            .andExpect(jsonPath("$.dtNascimento").value(DEFAULT_DT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findOne(pessoa.getId());
        updatedPessoa
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .celular(UPDATED_CELULAR)
            .endereco(UPDATED_ENDERECO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .cep(UPDATED_CEP)
            .contato(UPDATED_CONTATO)
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .genero(UPDATED_GENERO)
            .email(UPDATED_EMAIL);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testPessoa.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testPessoa.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testPessoa.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPessoa.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testPessoa.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testPessoa.getContato()).isEqualTo(UPDATED_CONTATO);
        assertThat(testPessoa.getDtNascimento()).isEqualTo(UPDATED_DT_NASCIMENTO);
        assertThat(testPessoa.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPessoa.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Get the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(pessoa1.getId());
        assertThat(pessoa1).isEqualTo(pessoa2);
        pessoa2.setId(2L);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
        pessoa1.setId(null);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaDTO.class);
        PessoaDTO pessoaDTO1 = new PessoaDTO();
        pessoaDTO1.setId(1L);
        PessoaDTO pessoaDTO2 = new PessoaDTO();
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
        pessoaDTO2.setId(pessoaDTO1.getId());
        assertThat(pessoaDTO1).isEqualTo(pessoaDTO2);
        pessoaDTO2.setId(2L);
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
        pessoaDTO1.setId(null);
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pessoaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pessoaMapper.fromId(null)).isNull();
    }
}
