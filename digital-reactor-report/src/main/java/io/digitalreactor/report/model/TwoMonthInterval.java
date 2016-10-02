package io.digitalreactor.report.model;

import java.time.LocalDate;

/**
 * Created by ingvard on 02.10.16.
 */
public class TwoMonthInterval {
    private final LocalDate firstMonth;
    private final LocalDate secondMonth;

    public TwoMonthInterval(LocalDate firstMonth, LocalDate secondMonth) {
        this.firstMonth = firstMonth;
        this.secondMonth = secondMonth;
    }

    public static TwoMonthInterval previous(LocalDate currentDate) {
        return new TwoMonthInterval(currentDate.minusMonths(2), currentDate.minusMonths(1));
    }

    public LocalDate last() {
        return secondMonth.withDayOfMonth(secondMonth.lengthOfMonth());
    }

    public LocalDate firstDayOfSecondMonth() {
        return secondMonth.withDayOfMonth(1);
    }

    public LocalDate first() {
        return firstMonth.withDayOfMonth(1);
    }
}
