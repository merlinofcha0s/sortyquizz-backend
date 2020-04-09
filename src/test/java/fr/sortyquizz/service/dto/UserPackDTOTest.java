package fr.sortyquizz.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class UserPackDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPackDTO.class);
        UserPackDTO userPackDTO1 = new UserPackDTO();
        userPackDTO1.setId(1L);
        UserPackDTO userPackDTO2 = new UserPackDTO();
        assertThat(userPackDTO1).isNotEqualTo(userPackDTO2);
        userPackDTO2.setId(userPackDTO1.getId());
        assertThat(userPackDTO1).isEqualTo(userPackDTO2);
        userPackDTO2.setId(2L);
        assertThat(userPackDTO1).isNotEqualTo(userPackDTO2);
        userPackDTO1.setId(null);
        assertThat(userPackDTO1).isNotEqualTo(userPackDTO2);
    }
}
