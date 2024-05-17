package Helpers;

import javafx.util.converter.LocalDateStringConverter;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarExcludingNYCHolidays;
import net.finmath.time.daycount.DayCountConvention_30U_360;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;
import static java.util.Set.of;


public class TimeSeries {

    private BusinessdayCalendarExcludingNYCHolidays isBusinessday = new BusinessdayCalendarExcludingNYCHolidays();
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> workDays;

    public TimeSeries(LocalDate firstDate, LocalDate lastDate) {
        startDate = firstDate;
        endDate = lastDate;
        generateWorkDates();
    }


    private boolean isWorkDay(LocalDate date) {
        return isBusinessday.isBusinessday(date);
    }

    public void generateWorkDates() {
        workDays = startDate.datesUntil(endDate)
                .filter(this::isWorkDay).collect(Collectors.toList());
    }

    public int getNumberOfWorkDays() {
        return getWorkDates().size();
    }

    public int getNumberOfWorkDaysUntil(LocalDate endDate) {
        int rc = 0;
        List<LocalDate> workDays = getWorkDates();
        for (LocalDate actDate : workDays) {
            if (actDate.isBefore(endDate)) {
                rc++;
            } else {
                break;
            }
        }
        return rc;
    }

    public List<LocalDate> getWorkDates() {
        return workDays;
    }



}
