package fr.sortyquizz.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class ThemeScoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeScoreDTO.class);
        ThemeScoreDTO themeScoreDTO1 = new ThemeScoreDTO();
        themeScoreDTO1.setId(1L);
        ThemeScoreDTO themeScoreDTO2 = new ThemeScoreDTO();
        assertThat(themeScoreDTO1).isNotEqualTo(themeScoreDTO2);
        themeScoreDTO2.setId(themeScoreDTO1.getId());
        assertThat(themeScoreDTO1).isEqualTo(themeScoreDTO2);
        themeScoreDTO2.setId(2L);
        assertThat(themeScoreDTO1).isNotEqualTo(themeScoreDTO2);
        themeScoreDTO1.setId(null);
        assertThat(themeScoreDTO1).isNotEqualTo(themeScoreDTO2);
    }
}
