package fr.sortyquizz.service.mapper;


import fr.sortyquizz.domain.*;
import fr.sortyquizz.service.dto.ReferenceXPDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferenceXP} and its DTO {@link ReferenceXPDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReferenceXPMapper extends EntityMapper<ReferenceXPDTO, ReferenceXP> {



    default ReferenceXP fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferenceXP referenceXP = new ReferenceXP();
        referenceXP.setId(id);
        return referenceXP;
    }
}
