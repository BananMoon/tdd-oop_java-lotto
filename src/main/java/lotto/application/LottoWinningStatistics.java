package lotto.application;

import lotto.domain.Lotto;
import lotto.domain.LottoPrice;
import lotto.domain.LottoRank;

import java.util.*;

public class LottoWinningStatistics {
    private final Map<LottoRank, Integer> values;

    public LottoWinningStatistics(List<Lotto> userLottos, Lotto winningLotto) {
        Map<LottoRank, Integer> result = initStatistics();

        for (Lotto userLotto : userLottos) {
            putRankedLottoQuantity(result, userLotto, winningLotto);
        }
        this.values = result;
    }

    private Map<LottoRank, Integer> initStatistics() {
        Map<LottoRank, Integer> statistics = new EnumMap<>(LottoRank.class);
        Arrays.stream(LottoRank.values())
                .forEach(value -> statistics.put(value, 0));
        return statistics;
    }

    public LottoWinningStatistics(Map<LottoRank, Integer> values) {
        this.values = values;
    }

    private void putRankedLottoQuantity(Map<LottoRank, Integer> result,
                                        Lotto userLotto,
                                        Lotto winningLotto) {
        LottoRank key = LottoRank.from(winningLotto.countMatchingNumbers(userLotto));
        result.put(key, result.get(key) + 1);
    }

    public float calculateReturnRate(LottoPrice lottoPurchaseAmount) {
        return Math.round((this.calculateWinningAmount() / lottoPurchaseAmount.floatValue()) * 100) / 100.0f;
    }

    private int calculateWinningAmount() {
        int winningAmount = 0;
        for (Map.Entry<LottoRank, Integer> result : this.values.entrySet()) {
            winningAmount += result.getKey().getDistributionRatioPrice() * result.getValue();
        }
        return winningAmount;
    }

    public Integer getLottoQuantityOfRanking(LottoRank ranking) {
        return this.values.get(ranking);
    }
}
