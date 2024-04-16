package com.stock.analysis.Momentum_Strategy.Util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static LocalDate getStartMonthDate(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        while (start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY) {
            start = start.plusDays(1);
        }
        return start;
    }

    public static LocalDate getEndMonthDate(int year, int month) {
        LocalDate end = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
        while (end.getDayOfWeek() == DayOfWeek.SATURDAY || end.getDayOfWeek() == DayOfWeek.SUNDAY) {
            end = end.minusDays(1);
        }
        return end;
    }

    public static DayOfWeek getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek();
    }

    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public static LocalDate getDateBeforeMonth(LocalDate date, int month) {
        return date.minusMonths(month);
    }

    public static LocalDate getDateBeforeYear(LocalDate date, int year) {
        return date.minusYears(year);
    }

public static List<LocalDate> getFirstDateOfMonthBetweenYears(int startYear,int endYear) {
    List<LocalDate> eachMonthFirstDateList = new ArrayList<>();
    for (int year = startYear; year <= endYear; year++) {
        // Iterate over months
        for (int month = 1; month <= 12; month++) {
            LocalDate firstDateOfMonth = LocalDate.of(year, month, 1);
            if (firstDateOfMonth.isBefore(LocalDate.now())) {
            eachMonthFirstDateList.add(firstDateOfMonth);
            }
        }
    }
    return eachMonthFirstDateList;
}
}
