package co.com.iesonline.sjies.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.iesonline.sjies.service.OperadorService;
import co.com.iesonline.sjies.web.rest.errors.BadRequestAlertException;
import co.com.iesonline.sjies.web.rest.util.HeaderUtil;
import co.com.iesonline.sjies.web.rest.util.PaginationUtil;
import co.com.iesonline.sjies.service.dto.OperadorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Operador.
 */
@RestController
@RequestMapping("/api")
public class OperadorResource {

    private final Logger log = LoggerFactory.getLogger(OperadorResource.class);

    private static final String ENTITY_NAME = "operador";

    private final OperadorService operadorService;

    public OperadorResource(OperadorService operadorService) {
        this.operadorService = operadorService;
    }

    /**
     * POST  /operadors : Create a new operador.
     *
     * @param operadorDTO the operadorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operadorDTO, or with status 400 (Bad Request) if the operador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operadors")
    @Timed
    public ResponseEntity<OperadorDTO> createOperador(@Valid @RequestBody OperadorDTO operadorDTO) throws URISyntaxException {
        log.debug("REST request to save Operador : {}", operadorDTO);
        if (operadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new operador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperadorDTO result = operadorService.save(operadorDTO);
        return ResponseEntity.created(new URI("/api/operadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operadors : Updates an existing operador.
     *
     * @param operadorDTO the operadorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operadorDTO,
     * or with status 400 (Bad Request) if the operadorDTO is not valid,
     * or with status 500 (Internal Server Error) if the operadorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operadors")
    @Timed
    public ResponseEntity<OperadorDTO> updateOperador(@Valid @RequestBody OperadorDTO operadorDTO) throws URISyntaxException {
        log.debug("REST request to update Operador : {}", operadorDTO);
        if (operadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperadorDTO result = operadorService.save(operadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operadors : get all the operadors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operadors in body
     */
    @GetMapping("/operadors")
    @Timed
    public ResponseEntity<List<OperadorDTO>> getAllOperadors(Pageable pageable) {
        log.debug("REST request to get a page of Operadors");
        Page<OperadorDTO> page = operadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operadors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operadors/:id : get the "id" operador.
     *
     * @param id the id of the operadorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operadorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operadors/{id}")
    @Timed
    public ResponseEntity<OperadorDTO> getOperador(@PathVariable Long id) {
        log.debug("REST request to get Operador : {}", id);
        Optional<OperadorDTO> operadorDTO = operadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operadorDTO);
    }

    /**
     * DELETE  /operadors/:id : delete the "id" operador.
     *
     * @param id the id of the operadorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperador(@PathVariable Long id) {
        log.debug("REST request to delete Operador : {}", id);
        operadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
