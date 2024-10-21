package lotto.application;

import lotto.application.strategy.LottoNumberGenerator;
import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoProgramTest {

    @DisplayName("구매한 로또 갯수만큼 로또를 생성하여 반환한다.")
    @Test
    void generate_quickPick_and_manual_lottos() {
        int totalLottoQuantity = 2;
        Lotto quickPickLotto = new Lotto(1, 2, 3, 4, 5, 6);
        List<String[]> manualLottos = new ArrayList<>();
        manualLottos.add(new String[]{"7", "8", "9", "10", "11", "12"});

        LottoProgram lottoProgram = new LottoProgram(new LottoNumberGenerator() {
            @Override
            public List<Lotto> generate(int generateCount) {
                return List.of(quickPickLotto);
            }
        });

        List<Lotto> result = lottoProgram.generateLottos(totalLottoQuantity, manualLottos);

        assertThat(result).containsExactly(new Lotto(7, 8, 9, 10, 11, 12), quickPickLotto);
    }

}
