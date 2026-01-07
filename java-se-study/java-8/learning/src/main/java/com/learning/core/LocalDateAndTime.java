package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;

public class LocalDateAndTime {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(LocalDateAndTime.class);

    public static void main(String[] args) {
        InstanceMessageLogger.configureLogger(LocalDateAndTime.class.getResourceAsStream("/logger.properties"));

        // temporal represents things that are some form of tangible representation of time as abstract concept, like dates, time, duration,
        // etc.
        Temporal nowInstant = Instant.now();
        Temporal nowDate = LocalDate.now();
        Temporal nowTime = LocalTime.now();
        Temporal nowDateTime = LocalDateTime.now();

        // temporal amount represent things that mutate on the object representations of time as abstract concepts, such as adding
        // amounts to date/time like adding days to a LocalDate, or adding hours to LocalTime. Really only Period and Duration are temporal
        // amounts, everything else is temporal unit
        TemporalAmount duration = Duration.ofMinutes(60L);
        TemporalAmount period = Period.ofDays(1);
        LOGGER.logInfo(nowDate);

        // the temporal object is added with period, the new object that is produced by this operation, is the original date + the added
        // period representation. We also use the native local date api to demonstrate that it has somewhat extended on the Temporal one,
        // and has different methods to add days, months, years to the local date object
        Temporal nowDateWithPeriodOffset = nowDate.plus(period);
        Temporal nowDateWithInstantOffset = ((LocalDate) nowDate).plusDays(1);
        LOGGER.logInfo(nowDateWithPeriodOffset);
        LOGGER.logInfo(nowDateWithInstantOffset);

        // note that we are adjusting this LocalDate with a valid unit, in this case month, but other valid units are day, year as well.
        // This is possible because both Month.* and the LocalDate represent the same unit of time
        LocalDate nowDateLocalDateMonthExtractor = (LocalDate) nowDate;
        int nextMonth = nowDateLocalDateMonthExtractor.getMonthValue();
        TemporalAdjuster nextMonthAdjuster = Month.of(nextMonth).plus(1);
        Temporal nowDateTemporalAdjustedWithMonth = nowDate.with(nextMonthAdjuster);
        LOGGER.logInfo(nowDateTemporalAdjustedWithMonth);

        // we adjust the nowDateTime, with a nowTime + the duration, which would, offset the local date time object with a single hour
        // forward, that is possible because the Duartion and LocalTime, both represent the same unit of time
        TemporalAdjuster nextTimeDurationOffsetAdjuster = (TemporalAdjuster) nowTime.plus(duration);
        Temporal nowDateTimeTemporalAdjustedWithTime = nowDateTime.with(nextTimeDurationOffsetAdjuster);
        LOGGER.logInfo(nowDateTimeTemporalAdjustedWithTime);

        // the adjust interface controls how a given temporal adjuster mutates a target temporal unit, this is providing a way of mutating
        // the different components of a temporal unit more precisely, unlike the plus method and its variations, the adjustInfo allows for
        // custom implementations to adjust the target temporal unit however they see fit, e.g. one can implement a temporal adjuster that
        // always moves a date to the last friday or last working day of a month, to allow users to perform monthly autiding
        LocalDate nowDateLocalDate = (LocalDate) nowDate;
        LocalDate nowDateAdjustedWithMonth = (LocalDate) nowDateTemporalAdjustedWithMonth;
        Temporal nowDateLocalDateAdjustedWithAnotherLocalDate = nowDateLocalDate.adjustInto(nowDateAdjustedWithMonth);
        LOGGER.logInfo(nowDateLocalDateAdjustedWithAnotherLocalDate);

        // offset the LocalTime with a given amount of a predefined duaration, in this case this is possible because, the LocalTime and the
        // Duration represented by CENTURIES define the same units, in this case Hours, Minutes etc.
        Temporal nowDateTimeCenturies = nowDateTime.plus(ChronoUnit.CENTURIES.getDuration());
        LOGGER.logInfo(nowDateTimeCenturies);

        // now note that this is also possible because duration is valid unit to offset nowTime with, however it will only offset the time
        // component of the local time unit in this case the hours/minutes/seconds
        Temporal nowTimeCenturies = nowTime.plus(ChronoUnit.CENTURIES.getDuration());
        LOGGER.logInfo(nowTimeCenturies);

        Period oneDayPeriod = Period.ofDays(1);
        Temporal nextNowDateOffsetWithPeriod = nowDate.plus(oneDayPeriod);
        LOGGER.logInfo(nextNowDateOffsetWithPeriod);

        try {
            // this just like .with, is not possible due to the mismatch of the types, we are trying to add Duration which has main units of
            // hours/minutes/seconds, to a unit of time which only understands Days, Months and Years.
            Duration oneDayDuration = Duration.ofDays(1);
            Temporal nextNowDateOffsetWithDuration = nowDate.plus(oneDayDuration);
            LOGGER.logInfo(nextNowDateOffsetWithDuration);
        } catch (Exception e) {
            LOGGER.logSevere(e);
        }

        try {
            // This is however invalid, why would that be, well the adjuster here is of type LocalTime, which has no meaningful units for
            // the type LocalDate, in this case it has units such as seconds, minutes, hours, and we will have to use LocalDateTime instead
            // to adjust it with a LocalTime
            Temporal nowDateTemporalAdjustedWithTime = nowDate.with((TemporalAdjuster) nowTime);
            LOGGER.logInfo(nowDateTemporalAdjustedWithTime);
        } catch (Exception e) {
            LOGGER.logSevere(e);
        }

        try {
            // The duration is really constructed from minutes, and being added to a date object, that has no internal representation of
            // hours/minutes at all, and will actually cause exception since we are adding it to a local date object, we can do this only
            // with a LocalTime or LocalDateTime objects
            Temporal nowDateWithDurationOffset = nowDate.plus(duration);
            LOGGER.logInfo(nowDateWithDurationOffset);
        } catch (Exception e) {
            LOGGER.logSevere(e);
        }

        // To summarize the interfaces, we have 3 main interfaces that the new java time world defines, those are as follows
        // Temporal and TemporalAccessor - defines aboslute unit, LocalTime, LocalDate and LocalDateTime - this is basically the time unit,
        // it described absolute values or units that we can use to express time unit.
        // TemporalAmount - relative unit, Duration and Period, the temporal interface has the bridge methods to offset/add an absolute unit
        // of time Temporal with a relative unit of time TemporalAmount.
        // TemporalAdjuster - every adjuster implementations define the rules of how we can adjust the existing fields of a given Temporal
        // unit for a given target like Month, Day, Minutes, Hours etc, or some combination of all
        // ChronoUnit - the default implementation of TemporalUnit, provides a list of different time based units, all of them really, from
        // NANOS to ERAS. It also provides a Duration representation of these same units, but not Periods, because some are not possible to
        // be represented as Period, also the resolution of the period class might not be big enough to Represent them

        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.systemDefault());
        LOGGER.logInfo(zonedDateTimeNow);

    }
}
