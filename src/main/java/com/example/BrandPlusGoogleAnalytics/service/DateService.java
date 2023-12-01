package com.example.BrandPlusGoogleAnalytics.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {
    private static final String PACIFIC_TIME_ZONE = "America/Los_Angeles";

    public String getFirstEndDayInPDT() {
        ZonedDateTime pdtDateTime = ZonedDateTime.now(ZoneId.of(PACIFIC_TIME_ZONE));

        // Subtract one day to get the previous day
        pdtDateTime = pdtDateTime.minusDays(1);

        // Format the PDT time as "YYYY-MM-DD"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedPDT = pdtDateTime.format(formatter);

        return formattedPDT;
    }

    public String getPreviousDayInPDT(String inputDate) {
        // Parse the input date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, formatter);

        // Convert to Pacific Time Zone
        ZonedDateTime pdtDateTime = ZonedDateTime.of(date.atStartOfDay(), ZoneId.of(PACIFIC_TIME_ZONE));

        // Get the previous day
        ZonedDateTime previousDay = pdtDateTime.minusDays(1);

        // Format the result as "YYYY-MM-DD"
        return previousDay.format(formatter);
    }

    public Long convertToUnixTimeInPDT(String dateString) {
        // Parse the input date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Create a ZonedDateTime with the given date and PDT time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.of(date.atStartOfDay(), ZoneId.of("America/Los_Angeles"));

        // Convert ZonedDateTime to Unix time (seconds since the epoch)
        return zonedDateTime.toEpochSecond();
    }
}
