package ar.com.manflack.ada.web.rest;

import ar.com.manflack.ada.AdaWorkshopApp;
import ar.com.manflack.ada.domain.Cuenta;
import ar.com.manflack.ada.repository.CuentaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CuentaResource} REST controller.
 */
@SpringBootTest(classes = AdaWorkshopApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CuentaResourceIT {

    private static final Integer DEFAULT_BALANCE = 1;
    private static final Integer UPDATED_BALANCE = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuentaMockMvc;

    private Cuenta cuenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuenta createEntity(EntityManager em) {
        Cuenta cuenta = new Cuenta()
            .balance(DEFAULT_BALANCE)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO);
        return cuenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuenta createUpdatedEntity(EntityManager em) {
        Cuenta cuenta = new Cuenta()
            .balance(UPDATED_BALANCE)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO);
        return cuenta;
    }

    @BeforeEach
    public void initTest() {
        cuenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuenta() throws Exception {
        int databaseSizeBeforeCreate = cuentaRepository.findAll().size();
        // Create the Cuenta
        restCuentaMockMvc.perform(post("/api/cuentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cuenta)))
            .andExpect(status().isCreated());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeCreate + 1);
        Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
        assertThat(testCuenta.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testCuenta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCuenta.getApellido()).isEqualTo(DEFAULT_APELLIDO);
    }

    @Test
    @Transactional
    public void createCuentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuentaRepository.findAll().size();

        // Create the Cuenta with an existing ID
        cuenta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaMockMvc.perform(post("/api/cuentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cuenta)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCuentas() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList
        restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)));
    }
    
    @Test
    @Transactional
    public void getCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get the cuenta
        restCuentaMockMvc.perform(get("/api/cuentas/{id}", cuenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuenta.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO));
    }
    @Test
    @Transactional
    public void getNonExistingCuenta() throws Exception {
        // Get the cuenta
        restCuentaMockMvc.perform(get("/api/cuentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

        // Update the cuenta
        Cuenta updatedCuenta = cuentaRepository.findById(cuenta.getId()).get();
        // Disconnect from session so that the updates on updatedCuenta are not directly saved in db
        em.detach(updatedCuenta);
        updatedCuenta
            .balance(UPDATED_BALANCE)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO);

        restCuentaMockMvc.perform(put("/api/cuentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCuenta)))
            .andExpect(status().isOk());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
        Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
        assertThat(testCuenta.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testCuenta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCuenta.getApellido()).isEqualTo(UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingCuenta() throws Exception {
        int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaMockMvc.perform(put("/api/cuentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cuenta)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        int databaseSizeBeforeDelete = cuentaRepository.findAll().size();

        // Delete the cuenta
        restCuentaMockMvc.perform(delete("/api/cuentas/{id}", cuenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
