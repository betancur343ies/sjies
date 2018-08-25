package co.com.iesonline.sjies.service.impl;

import co.com.iesonline.sjies.service.OperadorService;
import co.com.iesonline.sjies.domain.Operador;
import co.com.iesonline.sjies.domain.enumeration.Estado;
import co.com.iesonline.sjies.repository.OperadorRepository;
import co.com.iesonline.sjies.service.dto.OperadorDTO;
import co.com.iesonline.sjies.service.dto.SorteoDTO;
import co.com.iesonline.sjies.service.mapper.OperadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import javax.validation.Valid;
/**
 * Service Implementation for managing Operador.
 */
@Service
@Transactional
public class OperadorServiceImpl implements OperadorService {

    private final Logger log = LoggerFactory.getLogger(OperadorServiceImpl.class);

    private final OperadorRepository operadorRepository;

    private final OperadorMapper operadorMapper;

    public OperadorServiceImpl(OperadorRepository operadorRepository, OperadorMapper operadorMapper) {
        this.operadorRepository = operadorRepository;
        this.operadorMapper = operadorMapper;
    }

    /**
     * Save a operador.
     *
     * @param operadorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OperadorDTO save(OperadorDTO operadorDTO) {
        log.debug("Request to save Operador : {}", operadorDTO);
        Operador operador = operadorMapper.toEntity(operadorDTO);
        operador = operadorRepository.save(operador);
        return operadorMapper.toDto(operador);
    }

    /**
     * Get all the operadors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperadorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Operadors");
        return operadorRepository.findAll(pageable)
            .map(operadorMapper::toDto);
    }


    /**
     * Get one operador by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OperadorDTO> findOne(Long id) {
        log.debug("Request to get Operador : {}", id);
        return operadorRepository.findById(id)
            .map(operadorMapper::toDto);
    }

    /**
     * Delete the operador by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operador : {}", id);
        operadorRepository.deleteById(id);
    }
    
    /**
     * Update counters of "sorteosActivos" n "totalSorteos" of an operator.
     * 
     * @param operatorId
     * @param sorteoDTO
     * @param creatingSorteo
     * @return
     */
    public Long updateCounters(Long operatorId, @Valid SorteoDTO sorteoDTO, Boolean creatingSorteo) {
    	
    	log.debug("Request to update Sorteos Info : {}", operatorId);
    
    	//find operator by id
    	Optional<OperadorDTO> operadorDTO = findOne(operatorId);
    	
    	//update totalSorteos
    	if (creatingSorteo) {
    		
    		operadorDTO.get().setTotalSorteos(operadorDTO.get().getTotalSorteos() + 1);
		}
    	
    	//update sorteosActivos
    	if (sorteoDTO.getEstado().equals(Estado.ACTIVO)) {    		
    	
    		operadorDTO.get().setSorteosActivos(operadorDTO.get().getSorteosActivos() + 1);
		} else {
    		
    		operadorDTO.get().setSorteosActivos(operadorDTO.get().getSorteosActivos() - 1);
		}    			   	
    	
    	//update Operator
        OperadorDTO result = save(operadorDTO.get());
        
		return result.getId();
	}
}
