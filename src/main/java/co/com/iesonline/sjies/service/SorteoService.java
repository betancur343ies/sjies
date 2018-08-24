package co.com.iesonline.sjies.service;

import co.com.iesonline.sjies.service.dto.SorteoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Sorteo.
 */
public interface SorteoService {

    /**
     * Save a sorteo.
     *
     * @param sorteoDTO the entity to save
     * @return the persisted entity
     */
    SorteoDTO save(SorteoDTO sorteoDTO);

    /**
     * Get all the sorteos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SorteoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sorteo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SorteoDTO> findOne(Long id);

    /**
     * Delete the "id" sorteo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
