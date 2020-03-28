package fr.sortyquizz.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PackMapperTest {

    private PackMapper packMapper;

    @BeforeEach
    public void setUp() {
        packMapper = new PackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(packMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(packMapper.fromId(null)).isNull();
    }
}
