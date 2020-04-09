package fr.sortyquizz.service.mapper;


import fr.sortyquizz.domain.*;
import fr.sortyquizz.service.dto.RuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rule} and its DTO {@link RuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RuleMapper extends EntityMapper<RuleDTO, Rule> {



    default Rule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rule rule = new Rule();
        rule.setId(id);
        return rule;
    }
}
