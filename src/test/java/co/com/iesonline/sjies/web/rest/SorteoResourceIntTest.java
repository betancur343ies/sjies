package co.com.iesonline.sjies.web.rest;

import co.com.iesonline.sjies.SjiesApp;

import co.com.iesonline.sjies.domain.Sorteo;
import co.com.iesonline.sjies.repository.SorteoRepository;
import co.com.iesonline.sjies.service.OperadorService;
import co.com.iesonline.sjies.service.SorteoService;
import co.com.iesonline.sjies.service.dto.SorteoDTO;
import co.com.iesonline.sjies.service.mapper.SorteoMapper;
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

import co.com.iesonline.sjies.domain.enumeration.Juegos;
import co.com.iesonline.sjies.domain.enumeration.Estado;
/**
 * Test class for the SorteoResource REST controller.
 *
 * @see SorteoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SjiesApp.class)
public class SorteoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Juegos DEFAULT_TIPO = Juegos.RULETA;
    private static final Juegos UPDATED_TIPO = Juegos.DADOS;

    private static final ZonedDateTime DEFAULT_FECHA_CREACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_CREACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final ZonedDateTime DEFAULT_FECHA_REALIZACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_REALIZACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GANADOR = "AAAAAAAAAA";
    private static final String UPDATED_GANADOR = "BBBBBBBBBB";

    @Autowired
    private SorteoRepository sorteoRepository;


    @Autowired
    private SorteoMapper sorteoMapper;
    

    @Autowired
    private SorteoService sorteoService;
    
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

    private MockMvc restSorteoMockMvc;

    private Sorteo sorteo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SorteoResource sorteoResource = new SorteoResource(sorteoService, operadorService);
        this.restSorteoMockMvc = MockMvcBuilders.standaloneSetup(sorteoResource)
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
    public static Sorteo createEntity(EntityManager em) {
        Sorteo sorteo = new Sorteo()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .estado(DEFAULT_ESTADO)
            .fechaRealizacion(DEFAULT_FECHA_REALIZACION)
            .ganador(DEFAULT_GANADOR);
        return sorteo;
    }

    @Before
    public void initTest() {
        sorteo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSorteo() throws Exception {
        int databaseSizeBeforeCreate = sorteoRepository.findAll().size();

        // Create the Sorteo
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);
        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isCreated());

        // Validate the Sorteo in the database
        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeCreate + 1);
        Sorteo testSorteo = sorteoList.get(sorteoList.size() - 1);
        assertThat(testSorteo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSorteo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testSorteo.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testSorteo.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testSorteo.getFechaRealizacion()).isEqualTo(DEFAULT_FECHA_REALIZACION);
        assertThat(testSorteo.getGanador()).isEqualTo(DEFAULT_GANADOR);
    }

    @Test
    @Transactional
    public void createSorteoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sorteoRepository.findAll().size();

        // Create the Sorteo with an existing ID
        sorteo.setId(1L);
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sorteo in the database
        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorteoRepository.findAll().size();
        // set the field null
        sorteo.setNombre(null);

        // Create the Sorteo, which fails.
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorteoRepository.findAll().size();
        // set the field null
        sorteo.setTipo(null);

        // Create the Sorteo, which fails.
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorteoRepository.findAll().size();
        // set the field null
        sorteo.setFechaCreacion(null);

        // Create the Sorteo, which fails.
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorteoRepository.findAll().size();
        // set the field null
        sorteo.setEstado(null);

        // Create the Sorteo, which fails.
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaRealizacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sorteoRepository.findAll().size();
        // set the field null
        sorteo.setFechaRealizacion(null);

        // Create the Sorteo, which fails.
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        restSorteoMockMvc.perform(post("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSorteos() throws Exception {
        // Initialize the database
        sorteoRepository.saveAndFlush(sorteo);

        // Get all the sorteoList
        restSorteoMockMvc.perform(get("/api/sorteos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sorteo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(sameInstant(DEFAULT_FECHA_CREACION))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fechaRealizacion").value(hasItem(sameInstant(DEFAULT_FECHA_REALIZACION))))
            .andExpect(jsonPath("$.[*].ganador").value(hasItem(DEFAULT_GANADOR.toString())));
    }
    

    @Test
    @Transactional
    public void getSorteo() throws Exception {
        // Initialize the database
        sorteoRepository.saveAndFlush(sorteo);

        // Get the sorteo
        restSorteoMockMvc.perform(get("/api/sorteos/{id}", sorteo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sorteo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.fechaCreacion").value(sameInstant(DEFAULT_FECHA_CREACION)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.fechaRealizacion").value(sameInstant(DEFAULT_FECHA_REALIZACION)))
            .andExpect(jsonPath("$.ganador").value(DEFAULT_GANADOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSorteo() throws Exception {
        // Get the sorteo
        restSorteoMockMvc.perform(get("/api/sorteos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSorteo() throws Exception {
        // Initialize the database
        sorteoRepository.saveAndFlush(sorteo);

        int databaseSizeBeforeUpdate = sorteoRepository.findAll().size();

        // Update the sorteo
        Sorteo updatedSorteo = sorteoRepository.findById(sorteo.getId()).get();
        // Disconnect from session so that the updates on updatedSorteo are not directly saved in db
        em.detach(updatedSorteo);
        updatedSorteo
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estado(UPDATED_ESTADO)
            .fechaRealizacion(UPDATED_FECHA_REALIZACION)
            .ganador(UPDATED_GANADOR);
        SorteoDTO sorteoDTO = sorteoMapper.toDto(updatedSorteo);

        restSorteoMockMvc.perform(put("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isOk());

        // Validate the Sorteo in the database
        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeUpdate);
        Sorteo testSorteo = sorteoList.get(sorteoList.size() - 1);
        assertThat(testSorteo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSorteo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testSorteo.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testSorteo.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testSorteo.getFechaRealizacion()).isEqualTo(UPDATED_FECHA_REALIZACION);
        assertThat(testSorteo.getGanador()).isEqualTo(UPDATED_GANADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSorteo() throws Exception {
        int databaseSizeBeforeUpdate = sorteoRepository.findAll().size();

        // Create the Sorteo
        SorteoDTO sorteoDTO = sorteoMapper.toDto(sorteo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restSorteoMockMvc.perform(put("/api/sorteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sorteoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sorteo in the database
        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSorteo() throws Exception {
        // Initialize the database
        sorteoRepository.saveAndFlush(sorteo);

        int databaseSizeBeforeDelete = sorteoRepository.findAll().size();

        // Get the sorteo
        restSorteoMockMvc.perform(delete("/api/sorteos/{id}", sorteo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sorteo> sorteoList = sorteoRepository.findAll();
        assertThat(sorteoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sorteo.class);
        Sorteo sorteo1 = new Sorteo();
        sorteo1.setId(1L);
        Sorteo sorteo2 = new Sorteo();
        sorteo2.setId(sorteo1.getId());
        assertThat(sorteo1).isEqualTo(sorteo2);
        sorteo2.setId(2L);
        assertThat(sorteo1).isNotEqualTo(sorteo2);
        sorteo1.setId(null);
        assertThat(sorteo1).isNotEqualTo(sorteo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SorteoDTO.class);
        SorteoDTO sorteoDTO1 = new SorteoDTO();
        sorteoDTO1.setId(1L);
        SorteoDTO sorteoDTO2 = new SorteoDTO();
        assertThat(sorteoDTO1).isNotEqualTo(sorteoDTO2);
        sorteoDTO2.setId(sorteoDTO1.getId());
        assertThat(sorteoDTO1).isEqualTo(sorteoDTO2);
        sorteoDTO2.setId(2L);
        assertThat(sorteoDTO1).isNotEqualTo(sorteoDTO2);
        sorteoDTO1.setId(null);
        assertThat(sorteoDTO1).isNotEqualTo(sorteoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sorteoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sorteoMapper.fromId(null)).isNull();
    }
}
