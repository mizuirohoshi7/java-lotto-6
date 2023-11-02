package lotto;

import static lotto.constant.LottoRanking.FIFTH;
import static lotto.constant.LottoRanking.FIRST;
import static lotto.constant.LottoRanking.FOURTH;
import static lotto.constant.LottoRanking.NONE;
import static lotto.constant.LottoRanking.SECOND;
import static lotto.constant.LottoRanking.THIRD;

import java.util.HashSet;
import java.util.List;
import lotto.constant.LottoRanking;
import lotto.model.LottoNumber;

public class Lotto {

    private static final int LOTTO_NUMBER_SIZE = 6;
    private static final int LOTTO_NUMBER_MIN = 1;
    private static final int LOTTO_NUMBER_MAX = 45;

    private final List<Integer> numbers;
    private int bonusNumber;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        checkIsDuplicated(numbers);
        validateRange(numbers);
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개만 가능합니다.");
        }
    }

    private void checkIsDuplicated(List<Integer> numbers) {
        if (new HashSet<>(numbers).size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (Integer number : numbers) {
            checkIsInValidRange(number);
        }
    }

    private void checkIsInValidRange(int number) {
        if (number < LOTTO_NUMBER_MIN || LOTTO_NUMBER_MAX < number) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1~45 사이의 숫자만 가능합니다.");
        }
    }

    public LottoRanking calculateLottoRanking(LottoNumber lottoNumber) {
        long count = lottoNumber.getNumbers().stream().filter(numbers::contains).count();
        boolean isBonus = lottoNumber.getNumbers().stream().anyMatch(number -> number == bonusNumber);
        return toLottoRanking(count, isBonus);
    }

    private LottoRanking toLottoRanking(long count, boolean isBonus) {
        if (count == 6) {
            return FIRST;
        }
        if (count == 5 && isBonus) {
            return SECOND;
        }
        if (count == 5) {
            return THIRD;
        }
        if (count == 4) {
            return FOURTH;
        }
        if (count == 3) {
            return FIFTH;
        }
        return NONE;
    }

    public void setBonusNumber(int bonusNumber) {
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호와 당첨 번호가 중복됩니다.");
        }
        this.bonusNumber = bonusNumber;
    }
}
