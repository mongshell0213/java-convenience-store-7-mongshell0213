package validation;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidationTest extends NsTest {

    @ParameterizedTest
    @NullSource
    void 공백_테스트(String string){
        assertThatThrownBy(()->Validation.blank(string))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 숫자_에러_테스트(){
        String string = "콜라";

        assertThatThrownBy(()->Validation.number(string))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문_패턴_테스트(){
        String order = "[사이다-2]";
        assertSimpleTest(()->{
            Validation.pattern(order);
            assertThat(output()).isBlank();
        });
    }

    @ParameterizedTest
    @ValueSource(strings={"사이다-2","사이다","[사이다-2","사이다-2]"})
    void 주문_패턴_실패_테스트(String order){
        assertThatThrownBy(()->Validation.pattern(order))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected void runMain() {

    }
}
