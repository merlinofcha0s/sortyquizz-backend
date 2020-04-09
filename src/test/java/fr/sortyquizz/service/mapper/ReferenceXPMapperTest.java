package fr.sortyquizz.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceXPMapperTest {

    private ReferenceXPMapper referenceXPMapper;

    @BeforeEach
    public void setUp() {
        referenceXPMapper = new ReferenceXPMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(referenceXPMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(referenceXPMapper.fromId(null)).isNull();
    }
}
