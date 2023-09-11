package vn.longvan.training.spring.user.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConvertUtil {
    public static long convertToMili(String birthDate){
        LocalDate birthday = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Convert LocalDate to LocalDateTime at midnight
        LocalDateTime birthdayDateTime = birthday.atStartOfDay();

        // Convert LocalDateTime to Instant
        Instant instant = birthdayDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // Convert Instant to milliseconds
        long milliseconds = instant.toEpochMilli();

        return milliseconds;
    }
}
