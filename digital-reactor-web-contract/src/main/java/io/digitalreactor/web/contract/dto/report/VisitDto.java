package io.digitalreactor.web.contract.dto.report;

import java.util.Objects;

/**
 * Created by MStepachev on 11.05.2016.
 */
public class VisitDto {
    public enum DayType {
        HOLIDAY, WEEKDAY
    }

    private final int number;
    private final String date;
    private final DayType dayType;

    public VisitDto(
           final int number,
           final String date,
           final DayType dayType
    ) {
        this.number = number;
        this.date = date;
        this.dayType = dayType;
    }

    public int getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public DayType getDayType() {
        return dayType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitDto visitDto = (VisitDto) o;
        return number == visitDto.number &&
                Objects.equals(date, visitDto.date) &&
                dayType == visitDto.dayType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, dayType);
    }
}
