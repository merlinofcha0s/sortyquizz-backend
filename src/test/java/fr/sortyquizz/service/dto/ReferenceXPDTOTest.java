package fr.sortyquizz.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class ReferenceXPDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceXPDTO.class);
        ReferenceXPDTO referenceXPDTO1 = new ReferenceXPDTO();
        referenceXPDTO1.setId(1L);
        ReferenceXPDTO referenceXPDTO2 = new ReferenceXPDTO();
        assertThat(referenceXPDTO1).isNotEqualTo(referenceXPDTO2);
        referenceXPDTO2.setId(referenceXPDTO1.getId());
        assertThat(referenceXPDTO1).isEqualTo(referenceXPDTO2);
        referenceXPDTO2.setId(2L);
        assertThat(referenceXPDTO1).isNotEqualTo(referenceXPDTO2);
        referenceXPDTO1.setId(null);
        assertThat(referenceXPDTO1).isNotEqualTo(referenceXPDTO2);
    }
}
