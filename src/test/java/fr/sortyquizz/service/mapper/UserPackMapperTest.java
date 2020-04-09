package fr.sortyquizz.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserPackMapperTest {

    private UserPackMapper userPackMapper;

    @BeforeEach
    public void setUp() {
        userPackMapper = new UserPackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userPackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userPackMapper.fromId(null)).isNull();
    }
}
