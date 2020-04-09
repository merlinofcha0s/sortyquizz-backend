package fr.sortyquizz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.sortyquizz.web.rest.TestUtil;

public class UserPackTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPack.class);
        UserPack userPack1 = new UserPack();
        userPack1.setId(1L);
        UserPack userPack2 = new UserPack();
        userPack2.setId(userPack1.getId());
        assertThat(userPack1).isEqualTo(userPack2);
        userPack2.setId(2L);
        assertThat(userPack1).isNotEqualTo(userPack2);
        userPack1.setId(null);
        assertThat(userPack1).isNotEqualTo(userPack2);
    }
}
