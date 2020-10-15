package ar.com.manflack.ada.web.rest;

import ar.com.manflack.ada.domain.Cuenta;
import ar.com.manflack.ada.repository.CuentaRepository;
import ar.com.manflack.ada.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.com.manflack.ada.domain.Cuenta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CuentaResource {

    private final Logger log = LoggerFactory.getLogger(CuentaResource.class);

    private static final String ENTITY_NAME = "cuenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuentaRepository cuentaRepository;

    public CuentaResource(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    /**
     * {@code POST  /cuentas} : Create a new cuenta.
     *
     * @param cuenta the cuenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuenta, or with status {@code 400 (Bad Request)} if the cuenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cuentas")
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) throws URISyntaxException {
        log.debug("REST request to save Cuenta : {}", cuenta);
        if (cuenta.getId() != null) {
            throw new BadRequestAlertException("A new cuenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cuenta result = cuentaRepository.save(cuenta);
        return ResponseEntity.created(new URI("/api/cuentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cuentas} : Updates an existing cuenta.
     *
     * @param cuenta the cuenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuenta,
     * or with status {@code 400 (Bad Request)} if the cuenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cuentas")
    public ResponseEntity<Cuenta> updateCuenta(@RequestBody Cuenta cuenta) throws URISyntaxException {
        log.debug("REST request to update Cuenta : {}", cuenta);
        if (cuenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cuenta result = cuentaRepository.save(cuenta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cuenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cuentas} : get all the cuentas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuentas in body.
     */
    @GetMapping("/cuentas")
    public List<Cuenta> getAllCuentas() {
        log.debug("REST request to get all Cuentas");
        return cuentaRepository.findAll();
    }

    /**
     * {@code GET  /cuentas/:id} : get the "id" cuenta.
     *
     * @param id the id of the cuenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Cuenta> getCuenta(@PathVariable Long id) {
        log.debug("REST request to get Cuenta : {}", id);
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cuenta);
    }

    /**
     * {@code DELETE  /cuentas/:id} : delete the "id" cuenta.
     *
     * @param id the id of the cuenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        log.debug("REST request to delete Cuenta : {}", id);
        cuentaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
