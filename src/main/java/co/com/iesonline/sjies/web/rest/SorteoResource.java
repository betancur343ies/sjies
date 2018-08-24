package co.com.iesonline.sjies.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.com.iesonline.sjies.service.SorteoService;
import co.com.iesonline.sjies.web.rest.errors.BadRequestAlertException;
import co.com.iesonline.sjies.web.rest.util.HeaderUtil;
import co.com.iesonline.sjies.web.rest.util.PaginationUtil;
import co.com.iesonline.sjies.service.dto.SorteoDTO;
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
 * REST controller for managing Sorteo.
 */
@RestController
@RequestMapping("/api")
public class SorteoResource {

    private final Logger log = LoggerFactory.getLogger(SorteoResource.class);

    private static final String ENTITY_NAME = "sorteo";

    private final SorteoService sorteoService;

    public SorteoResource(SorteoService sorteoService) {
        this.sorteoService = sorteoService;
    }

    /**
     * POST  /sorteos : Create a new sorteo.
     *
     * @param sorteoDTO the sorteoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sorteoDTO, or with status 400 (Bad Request) if the sorteo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sorteos")
    @Timed
    public ResponseEntity<SorteoDTO> createSorteo(@Valid @RequestBody SorteoDTO sorteoDTO) throws URISyntaxException {
        log.debug("REST request to save Sorteo : {}", sorteoDTO);
        if (sorteoDTO.getId() != null) {
            throw new BadRequestAlertException("A new sorteo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SorteoDTO result = sorteoService.save(sorteoDTO);
        return ResponseEntity.created(new URI("/api/sorteos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sorteos : Updates an existing sorteo.
     *
     * @param sorteoDTO the sorteoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sorteoDTO,
     * or with status 400 (Bad Request) if the sorteoDTO is not valid,
     * or with status 500 (Internal Server Error) if the sorteoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sorteos")
    @Timed
    public ResponseEntity<SorteoDTO> updateSorteo(@Valid @RequestBody SorteoDTO sorteoDTO) throws URISyntaxException {
        log.debug("REST request to update Sorteo : {}", sorteoDTO);
        if (sorteoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SorteoDTO result = sorteoService.save(sorteoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sorteoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sorteos : get all the sorteos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sorteos in body
     */
    @GetMapping("/sorteos")
    @Timed
    public ResponseEntity<List<SorteoDTO>> getAllSorteos(Pageable pageable) {
        log.debug("REST request to get a page of Sorteos");
        Page<SorteoDTO> page = sorteoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sorteos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sorteos/:id : get the "id" sorteo.
     *
     * @param id the id of the sorteoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sorteoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sorteos/{id}")
    @Timed
    public ResponseEntity<SorteoDTO> getSorteo(@PathVariable Long id) {
        log.debug("REST request to get Sorteo : {}", id);
        Optional<SorteoDTO> sorteoDTO = sorteoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sorteoDTO);
    }

    /**
     * DELETE  /sorteos/:id : delete the "id" sorteo.
     *
     * @param id the id of the sorteoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sorteos/{id}")
    @Timed
    public ResponseEntity<Void> deleteSorteo(@PathVariable Long id) {
        log.debug("REST request to delete Sorteo : {}", id);
        sorteoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
