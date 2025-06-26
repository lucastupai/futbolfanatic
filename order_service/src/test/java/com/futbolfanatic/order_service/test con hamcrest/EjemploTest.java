import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class EjemploTest {

    @Test
    public void pruebaHamcrest() {
        String resultado = "orden lista";
        assertThat(resultado, containsString("lista"));
    }
}
