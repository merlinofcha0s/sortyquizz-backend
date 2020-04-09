package fr.sortyquizz.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ThemeScoreMapperTest {

    private ThemeScoreMapper themeScoreMapper;

    @BeforeEach
    public void setUp() {
        themeScoreMapper = new ThemeScoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(themeScoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(themeScoreMapper.fromId(null)).isNull();
    }
}
