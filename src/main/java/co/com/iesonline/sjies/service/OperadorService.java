package co.com.iesonline.sjies.service;

import co.com.iesonline.sjies.service.dto.OperadorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Operador.
 */
public interface OperadorService {

    /**
     * Save a operador.
     *
     * @param operadorDTO the entity to save
     * @return the persisted entity
     */
    OperadorDTO save(OperadorDTO operadorDTO);

    /**
     * Get all the operadors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OperadorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" operador.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OperadorDTO> findOne(Long id);

    /**
     * Delete the "id" operador.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
