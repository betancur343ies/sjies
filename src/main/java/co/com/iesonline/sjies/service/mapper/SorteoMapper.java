package co.com.iesonline.sjies.service.mapper;

import co.com.iesonline.sjies.domain.*;
import co.com.iesonline.sjies.service.dto.SorteoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sorteo and its DTO SorteoDTO.
 */
@Mapper(componentModel = "spring", uses = {OperadorMapper.class})
public interface SorteoMapper extends EntityMapper<SorteoDTO, Sorteo> {

    @Mapping(source = "operador.id", target = "operadorId")
    @Mapping(source = "operador.nombre", target = "operadorNombre")
    SorteoDTO toDto(Sorteo sorteo);

    @Mapping(source = "operadorId", target = "operador")
    Sorteo toEntity(SorteoDTO sorteoDTO);

    default Sorteo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sorteo sorteo = new Sorteo();
        sorteo.setId(id);
        return sorteo;
    }
}
