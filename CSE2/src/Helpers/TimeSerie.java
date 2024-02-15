package Helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;
import static java.util.Set.of;

public class TimeSerie {
    private LocalDate startDate;
    private LocalDate endDate;

    private final Set<DayOfWeek> businessDays = of(
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    );

    private Set<LocalDate> fixHoliday;
    private Set<LocalDate> flexHoliday;
    private Set<LocalDate> forcedWorkDay;

    private List<LocalDate> workDays;

    public void generateDates()
    {
        workDays= startDate.datesUntil(endDate)
                // filtering for business days.
                .collect(Collectors.toList());
    }
    private boolean isWorkDay(LocalDate date)
    {
        if (checkForcedWorkday(date)) return true;
        if (checkForcedHoliday(date)) return false;
        return businessDays.contains(date.getDayOfWeek());

    }
    public void generateWorkDates()
    {
        workDays= startDate.datesUntil(endDate)
                // filtering for business days.
                .filter(t -> isWorkDay(t)).collect(Collectors.toList());
    }
    public List<LocalDate> getWorkDates()
    {
        if (workDays==null) generateWorkDates();
        return workDays;
    }
    public int getNumberofWorkDates()
    {
        List<LocalDate> dates=getWorkDates();
        return dates.size();
    }
    public int getNumberofWorkDatesUntil(LocalDate endDate)
    {
        int rc=0;
        List<LocalDate> dates=getWorkDates();
        for(LocalDate actDate:dates)
        {
            if (actDate.isBefore(endDate)) rc++;
            else break ;

        }
        return rc;
    }

    public TimeSerie(LocalDate startDate, LocalDate endDate)
    {
        changeTime(startDate, endDate);
        fixHoliday=new HashSet<>();
        flexHoliday=new HashSet<>();
        forcedWorkDay=new HashSet<>();
    }
    public void changeTime(LocalDate startDate, LocalDate endDate)
    {
        this.startDate=startDate;
        this.endDate=endDate;
        workDays=null;
    }
    public void resetLength(int days)
    {
        if (days<=0)
            return;
        workDays=null;
        int i=0;
        endDate=startDate;
        while (days>0)
        {
            if (isWorkDay(endDate))
                days--;
            endDate=endDate.plusDays(1);
        }
    }
    public boolean checkForcedWorkday(LocalDate actDate)
    {
        if (forcedWorkDay.contains(actDate)) return true;
        return false;
    }
    public boolean checkForcedHoliday(LocalDate actDate)
    {
        if (fixHoliday.contains(actDate)) return true;
        for (var day:flexHoliday) {
            if (day.getMonthValue()==actDate.getMonthValue() &&
                    day.getDayOfMonth()==actDate.getDayOfMonth()) return true;
        }
        return false;
    }
    public void addFixHoliday(LocalDate date)
    {
        fixHoliday.add(date);
    }
    public void addFlexHoliday(LocalDate date)
    {
        flexHoliday.add(date);
    }
    public void addForcedWorkDay(LocalDate date)
    {
        forcedWorkDay.add(date);
    }

}
