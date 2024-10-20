package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


class LottoTest {

    static Stream<Arguments> generateLottoNumbers() {
        return Stream.of(
                Arguments.of(new Integer[]{1, 2, 3, 4, 5, 6}, 6),
                Arguments.of(new Integer[]{7, 8, 9, 10, 11, 12}, 0)
        );
    }

    @DisplayName("인자로 받은 로또 번호 크기가 6개가 아니면 IllegalArgumentException을 발생시킨다.")
    @Test
    void throw_IllegalArgumentException_when_size_is_not_6() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Lotto(1, 2, 3, 4, 5))
                .withMessage("로또는 6개의 로또 번호로 구성되어야 합니다.");
    }

    @DisplayName("두 개의 LottoNumbers가 주어졌을 때 매칭되는 갯수를 반환한다.")
    @MethodSource("generateLottoNumbers")
    @ParameterizedTest(name = "LottoNumbers: {0}, matchingCount: {1}개")
    void return_counting_of_matching_lottoNumbers(Integer[] given, int expected) {
        Lotto winningLotto = new Lotto(1, 2, 3, 4, 5, 6);

        int actual = winningLotto.countMatchingNumbers(new Lotto(given));

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("로또 한장에 특정 로또 번호가 포함되어있는지 여부를 반환한다.")
    @ParameterizedTest(name = "LottoNumber: {0}, 로또에 포함 여부: {1}")
    @CsvSource(value = {"6, true", "7, false"})
    void return_check_Lotto_contains_LottoNumber(int lottoNumber, boolean expected) {
        LottoNumber number = new LottoNumber(lottoNumber);
        Lotto lotto = new Lotto(1, 2, 3, 4, 5, 6);

        assertThat(lotto.checkContainsLottoNumber(number)).isEqualTo(expected);
    }
}
