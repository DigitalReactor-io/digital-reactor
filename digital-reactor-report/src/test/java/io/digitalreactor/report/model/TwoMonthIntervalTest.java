package io.digitalreactor.report.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by ingvard on 02.10.16.
 */
public class TwoMonthIntervalTest {

    private final static int MARCH_CONTAINS_DAYS = 31;
    private final static int FIRST_DAY_OF_MONTH = 1;

    @Test
    public void firstAndLastDaysForIntervalWithShift() {
        LocalDate currentShiftDate = LocalDate.of(2016, 4, 17);

        TwoMonthInterval interval = TwoMonthInterval.previous(currentShiftDate);

        assertThat(interval.first(), is(equalTo(LocalDate.of(2016, 2, FIRST_DAY_OF_MONTH))));
        assertThat(interval.last(), is(equalTo(LocalDate.of(2016, 3, MARCH_CONTAINS_DAYS))));
    }
}