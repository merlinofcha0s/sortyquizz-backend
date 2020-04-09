package fr.sortyquizz.service.mapper;


import fr.sortyquizz.domain.*;
import fr.sortyquizz.service.dto.ThemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Theme} and its DTO {@link ThemeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThemeMapper extends EntityMapper<ThemeDTO, Theme> {


    @Mapping(target = "packs", ignore = true)
    @Mapping(target = "removePack", ignore = true)
    @Mapping(target = "themeScores", ignore = true)
    @Mapping(target = "removeThemeScore", ignore = true)
    Theme toEntity(ThemeDTO themeDTO);

    default Theme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Theme theme = new Theme();
        theme.setId(id);
        return theme;
    }
}
