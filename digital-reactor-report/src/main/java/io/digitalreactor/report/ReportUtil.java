package io.digitalreactor.report;

import io.digitalreactor.web.contract.dto.report.VisitDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MStepachev on 28.09.2016.
 */
public class ReportUtil {


    public static List<VisitDto> visitsListWithDay(List<Integer> visits, LocalDate startTime) {

        List<VisitDto> result = new ArrayList<VisitDto>();
        LocalDate pointerDate = startTime;

        for (int visit : visits) {
            result.add(new VisitDto(
                    visit,
                    pointerDate.toString(),
                    isHoliday(pointerDate) ? VisitDto.DayType.HOLIDAY : VisitDto.DayType.WEEKDAY
            ));
            pointerDate = pointerDate.plusDays(1);
        }

        return result;
    }

    private static boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
