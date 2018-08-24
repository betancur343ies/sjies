package co.com.iesonline.sjies.service.mapper;

import co.com.iesonline.sjies.domain.*;
import co.com.iesonline.sjies.service.dto.OperadorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operador and its DTO OperadorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OperadorMapper extends EntityMapper<OperadorDTO, Operador> {


    @Mapping(target = "sorteos", ignore = true)
    Operador toEntity(OperadorDTO operadorDTO);

    default Operador fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operador operador = new Operador();
        operador.setId(id);
        return operador;
    }
}
