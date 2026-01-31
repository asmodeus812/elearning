## Temporal

The temporal interface is one of the few primary ones from the java time package, that is implemented by most
important classes such as `LocalDate LocalTime LocalDateTime ZonedDateTime`

### Properties

### Interface

- `plus(TemporalAmount)` -adds a given temporal amount to the current value of the temporal object, and returns the
  result as a new object
- `minus(TemporalAmount)` - subtracts a given temporal amount from the current value of this temporal object and
  returns the result as a new object
- `until(Temporal endExclusive, TemporalUnit unit)` - calculates the amount of time until a given temporal in terms
  of the specified unit, the result is basically how many of the target temporal units are until the given temporal
  temporal target, but that is exclusive.

- LocalDate.now()
- LocalDateTime.now()
- ZonedDateTime.now()

- `DateTimeFormatter.ofLocalizedTime({FormatStyle})` - create a date time formatter that extracts only the time
component and formats in using the formatter style,
- `DateTimeFormatter.ofLocalizedDate({FormatStyle})` - create a date time formatter that extracts only the date
component and formats it using the formatter style


    - format styles - FULL, LONG, MEDIUM, SHORT;

`All methods in the temporal that appear to mutate the object are actually always returning a copy of the object,
they are immutable methods`

### Implementations

1. `LocalDate` - default date representation

```java
public final class LocalDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {}
```

- not synchronized structure
- represents ONLY year, month and day
- stores year, month and day, as separate properties

2. `LocalTime` - default time representation

```java
public final class LocalTime
        implements Temporal, TemporalAdjuster, Comparable<LocalTime>, Serializable {}
```

- not synchronized structure
- represents ONLY hour, minutes, seconds and nanoseconds
- stores hour, minutes, seconds and nanoseconds as separate properties

3. `LocalDateTime`- default date & time representation

```java
public final class LocalDateTime
        implements Temporal, TemporalAdjuster, ChronoLocalDateTime<LocalDate>, Serializable {}
```

- not synchronized structure
- date-time is NOT time zone aware
- stores `LocalTime` and `LocalDate`

## TemporalAmount

### Properties

### Implementations

## Caveats

- `toString` - each type of Temporal and TemporalAmount has a different format and approach to printing

- `ofLocalizedTime` - combined with FULL format style, should be applied only to temporal objects that are of type
zoned date and time, otherwise an exception will be thrown
-
