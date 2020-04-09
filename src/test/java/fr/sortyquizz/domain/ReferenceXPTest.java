package fr.sortyquizz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class ReferenceXPTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceXP.class);
        ReferenceXP referenceXP1 = new ReferenceXP();
        referenceXP1.setId(1L);
        ReferenceXP referenceXP2 = new ReferenceXP();
        referenceXP2.setId(referenceXP1.getId());
        assertThat(referenceXP1).isEqualTo(referenceXP2);
        referenceXP2.setId(2L);
        assertThat(referenceXP1).isNotEqualTo(referenceXP2);
        referenceXP1.setId(null);
        assertThat(referenceXP1).isNotEqualTo(referenceXP2);
    }
}
