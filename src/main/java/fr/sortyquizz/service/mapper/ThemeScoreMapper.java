package fr.sortyquizz.service.mapper;


import fr.sortyquizz.domain.*;
import fr.sortyquizz.service.dto.ThemeScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThemeScore} and its DTO {@link ThemeScoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, ThemeMapper.class})
public interface ThemeScoreMapper extends EntityMapper<ThemeScoreDTO, ThemeScore> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "theme.id", target = "themeId")
    ThemeScoreDTO toDto(ThemeScore themeScore);

    @Mapping(source = "profileId", target = "profile")
    @Mapping(source = "themeId", target = "theme")
    ThemeScore toEntity(ThemeScoreDTO themeScoreDTO);

    default ThemeScore fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThemeScore themeScore = new ThemeScore();
        themeScore.setId(id);
        return themeScore;
    }
}
