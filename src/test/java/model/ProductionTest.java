package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductionTest {
    @ParameterizedTest
    @NullSource
    @ValueSource(
        strings = {"탄산2+1"}
    )
    void 아이템_생성_테스트(String promotion) {
        Production production = new Production("콜라", 1000, promotion);
        Assertions.assertThat(production).isInstanceOf(Production.class);
    }
}
