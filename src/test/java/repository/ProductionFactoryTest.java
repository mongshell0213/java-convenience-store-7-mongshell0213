package repository;

import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductionFactoryTest {
    @Test
    void 입력_테스트() throws IOException {
        Productions productions = new Productions();

        ProductionFactory.input(productions);

        Assertions.assertThat(productions.size()).isEqualTo(16);
    }
}
