package fr.sortyquizz.service.mapper;


import fr.sortyquizz.domain.*;
import fr.sortyquizz.service.dto.PackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pack} and its DTO {@link PackDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PackMapper extends EntityMapper<PackDTO, Pack> {


    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestion", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "removeCard", ignore = true)
    Pack toEntity(PackDTO packDTO);

    default Pack fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pack pack = new Pack();
        pack.setId(id);
        return pack;
    }
}
