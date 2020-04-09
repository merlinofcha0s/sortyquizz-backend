package fr.sortyquizz.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RuleMapperTest {

    private RuleMapper ruleMapper;

    @BeforeEach
    public void setUp() {
        ruleMapper = new RuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ruleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ruleMapper.fromId(null)).isNull();
    }
}
