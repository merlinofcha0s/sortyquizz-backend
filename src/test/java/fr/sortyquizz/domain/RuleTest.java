package fr.sortyquizz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class RuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rule.class);
        Rule rule1 = new Rule();
        rule1.setId(1L);
        Rule rule2 = new Rule();
        rule2.setId(rule1.getId());
        assertThat(rule1).isEqualTo(rule2);
        rule2.setId(2L);
        assertThat(rule1).isNotEqualTo(rule2);
        rule1.setId(null);
        assertThat(rule1).isNotEqualTo(rule2);
    }
}
