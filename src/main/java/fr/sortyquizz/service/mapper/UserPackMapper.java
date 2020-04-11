package fr.sortyquizz.service.mapper;


import fr.sortyquizz.domain.UserPack;
import fr.sortyquizz.service.dto.UserPackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link UserPack} and its DTO {@link UserPackDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, PackMapper.class, ThemeMapper.class})
public interface UserPackMapper extends EntityMapper<UserPackDTO, UserPack> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "pack.id", target = "packId")
    @Mapping(source = "pack.name", target = "packName")
    @Mapping(source = "pack.level", target = "packLevel")
    @Mapping(source = "pack.theme.name", target = "themeName")
    UserPackDTO toDto(UserPack userPack);

    @Mapping(source = "profileId", target = "profile")
    @Mapping(source = "packId", target = "pack")
    UserPack toEntity(UserPackDTO userPackDTO);

    default UserPack fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPack userPack = new UserPack();
        userPack.setId(id);
        return userPack;
    }
}
