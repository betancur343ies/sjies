package co.com.iesonline.sjies.service.impl;

import co.com.iesonline.sjies.service.SorteoService;
import co.com.iesonline.sjies.domain.Sorteo;
import co.com.iesonline.sjies.repository.SorteoRepository;
import co.com.iesonline.sjies.service.dto.SorteoDTO;
import co.com.iesonline.sjies.service.mapper.SorteoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Sorteo.
 */
@Service
@Transactional
public class SorteoServiceImpl implements SorteoService {

    private final Logger log = LoggerFactory.getLogger(SorteoServiceImpl.class);

    private final SorteoRepository sorteoRepository;

    private final SorteoMapper sorteoMapper;

    public SorteoServiceImpl(SorteoRepository sorteoRepository, SorteoMapper sorteoMapper) {
        this.sorteoRepository = sorteoRepository;
        this.sorteoMapper = sorteoMapper;
    }

    /**
     * Save a sorteo.
     *
     * @param sorteoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SorteoDTO save(SorteoDTO sorteoDTO) {
        log.debug("Request to save Sorteo : {}", sorteoDTO);
        Sorteo sorteo = sorteoMapper.toEntity(sorteoDTO);
        sorteo = sorteoRepository.save(sorteo);
        return sorteoMapper.toDto(sorteo);
    }

    /**
     * Get all the sorteos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SorteoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sorteos");
        return sorteoRepository.findAll(pageable)
            .map(sorteoMapper::toDto);
    }


    /**
     * Get one sorteo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SorteoDTO> findOne(Long id) {
        log.debug("Request to get Sorteo : {}", id);
        return sorteoRepository.findById(id)
            .map(sorteoMapper::toDto);
    }

    /**
     * Delete the sorteo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sorteo : {}", id);
        sorteoRepository.deleteById(id);
    }
}
