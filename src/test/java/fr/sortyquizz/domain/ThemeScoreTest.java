package fr.sortyquizz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class ThemeScoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThemeScore.class);
        ThemeScore themeScore1 = new ThemeScore();
        themeScore1.setId(1L);
        ThemeScore themeScore2 = new ThemeScore();
        themeScore2.setId(themeScore1.getId());
        assertThat(themeScore1).isEqualTo(themeScore2);
        themeScore2.setId(2L);
        assertThat(themeScore1).isNotEqualTo(themeScore2);
        themeScore1.setId(null);
        assertThat(themeScore1).isNotEqualTo(themeScore2);
    }
}
