package co.com.iesonline.sjies.web.rest;

import co.com.iesonline.sjies.SjiesApp;

import co.com.iesonline.sjies.domain.Operador;
import co.com.iesonline.sjies.repository.OperadorRepository;
import co.com.iesonline.sjies.service.OperadorService;
import co.com.iesonline.sjies.service.dto.OperadorDTO;
import co.com.iesonline.sjies.service.mapper.OperadorMapper;
import co.com.iesonline.sjies.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static co.com.iesonline.sjies.web.rest.TestUtil.sameInstant;
import static co.com.iesonline.sjies.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OperadorResource REST controller.
 *
 * @see OperadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SjiesApp.class)
public class OperadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_CREACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_CREACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TOTAL_SORTEOS = 1;
    private static final Integer UPDATED_TOTAL_SORTEOS = 2;

    private static final Integer DEFAULT_SORTEOS_ACTIVOS = 1;
    private static final Integer UPDATED_SORTEOS_ACTIVOS = 2;

    @Autowired
    private OperadorRepository operadorRepository;


    @Autowired
    private OperadorMapper operadorMapper;
    

    @Autowired
    private OperadorService operadorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperadorMockMvc;

    private Operador operador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperadorResource operadorResource = new OperadorResource(operadorService);
        this.restOperadorMockMvc = MockMvcBuilders.standaloneSetup(operadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operador createEntity(EntityManager em) {
        Operador operador = new Operador()
            .nombre(DEFAULT_NOMBRE)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .totalSorteos(DEFAULT_TOTAL_SORTEOS)
            .sorteosActivos(DEFAULT_SORTEOS_ACTIVOS);
        return operador;
    }

    @Before
    public void initTest() {
        operador = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperador() throws Exception {
        int databaseSizeBeforeCreate = operadorRepository.findAll().size();

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);
        restOperadorMockMvc.perform(post("/api/operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Operador in the database
        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeCreate + 1);
        Operador testOperador = operadorList.get(operadorList.size() - 1);
        assertThat(testOperador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testOperador.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testOperador.getTotalSorteos()).isEqualTo(DEFAULT_TOTAL_SORTEOS);
        assertThat(testOperador.getSorteosActivos()).isEqualTo(DEFAULT_SORTEOS_ACTIVOS);
    }

    @Test
    @Transactional
    public void createOperadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operadorRepository.findAll().size();

        // Create the Operador with an existing ID
        operador.setId(1L);
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperadorMockMvc.perform(post("/api/operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = operadorRepository.findAll().size();
        // set the field null
        operador.setNombre(null);

        // Create the Operador, which fails.
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        restOperadorMockMvc.perform(post("/api/operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = operadorRepository.findAll().size();
        // set the field null
        operador.setFechaCreacion(null);

        // Create the Operador, which fails.
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        restOperadorMockMvc.perform(post("/api/operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperadors() throws Exception {
        // Initialize the database
        operadorRepository.saveAndFlush(operador);

        // Get all the operadorList
        restOperadorMockMvc.perform(get("/api/operadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(sameInstant(DEFAULT_FECHA_CREACION))))
            .andExpect(jsonPath("$.[*].totalSorteos").value(hasItem(DEFAULT_TOTAL_SORTEOS)))
            .andExpect(jsonPath("$.[*].sorteosActivos").value(hasItem(DEFAULT_SORTEOS_ACTIVOS)));
    }
    

    @Test
    @Transactional
    public void getOperador() throws Exception {
        // Initialize the database
        operadorRepository.saveAndFlush(operador);

        // Get the operador
        restOperadorMockMvc.perform(get("/api/operadors/{id}", operador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fechaCreacion").value(sameInstant(DEFAULT_FECHA_CREACION)))
            .andExpect(jsonPath("$.totalSorteos").value(DEFAULT_TOTAL_SORTEOS))
            .andExpect(jsonPath("$.sorteosActivos").value(DEFAULT_SORTEOS_ACTIVOS));
    }
    @Test
    @Transactional
    public void getNonExistingOperador() throws Exception {
        // Get the operador
        restOperadorMockMvc.perform(get("/api/operadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperador() throws Exception {
        // Initialize the database
        operadorRepository.saveAndFlush(operador);

        int databaseSizeBeforeUpdate = operadorRepository.findAll().size();

        // Update the operador
        Operador updatedOperador = operadorRepository.findById(operador.getId()).get();
        // Disconnect from session so that the updates on updatedOperador are not directly saved in db
        em.detach(updatedOperador);
        updatedOperador
            .nombre(UPDATED_NOMBRE)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .totalSorteos(UPDATED_TOTAL_SORTEOS)
            .sorteosActivos(UPDATED_SORTEOS_ACTIVOS);
        OperadorDTO operadorDTO = operadorMapper.toDto(updatedOperador);

        restOperadorMockMvc.perform(put("/api/operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operadorDTO)))
            .andExpect(status().isOk());

        // Validate the Operador in the database
        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeUpdate);
        Operador testOperador = operadorList.get(operadorList.size() - 1);
        assertThat(testOperador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testOperador.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testOperador.getTotalSorteos()).isEqualTo(UPDATED_TOTAL_SORTEOS);
        assertThat(testOperador.getSorteosActivos()).isEqualTo(UPDATED_SORTEOS_ACTIVOS);
    }

    @Test
    @Transactional
    public void updateNonExistingOperador() throws Exception {
        int databaseSizeBeforeUpdate = operadorRepository.findAll().size();

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restOperadorMockMvc.perform(put("/api/operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperador() throws Exception {
        // Initialize the database
        operadorRepository.saveAndFlush(operador);

        int databaseSizeBeforeDelete = operadorRepository.findAll().size();

        // Get the operador
        restOperadorMockMvc.perform(delete("/api/operadors/{id}", operador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operador> operadorList = operadorRepository.findAll();
        assertThat(operadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operador.class);
        Operador operador1 = new Operador();
        operador1.setId(1L);
        Operador operador2 = new Operador();
        operador2.setId(operador1.getId());
        assertThat(operador1).isEqualTo(operador2);
        operador2.setId(2L);
        assertThat(operador1).isNotEqualTo(operador2);
        operador1.setId(null);
        assertThat(operador1).isNotEqualTo(operador2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperadorDTO.class);
        OperadorDTO operadorDTO1 = new OperadorDTO();
        operadorDTO1.setId(1L);
        OperadorDTO operadorDTO2 = new OperadorDTO();
        assertThat(operadorDTO1).isNotEqualTo(operadorDTO2);
        operadorDTO2.setId(operadorDTO1.getId());
        assertThat(operadorDTO1).isEqualTo(operadorDTO2);
        operadorDTO2.setId(2L);
        assertThat(operadorDTO1).isNotEqualTo(operadorDTO2);
        operadorDTO1.setId(null);
        assertThat(operadorDTO1).isNotEqualTo(operadorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operadorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operadorMapper.fromId(null)).isNull();
    }
}
