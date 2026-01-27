1. Which of the following creates valid locales, assuming that the language and country codes follow standard
   conventions? (Choose all that apply.)

The locale constructor exists, and it is overloaded to take 2 arguments at most the language and the country/region.
The rest of the local properties can be constructed using a builder class, like script variant etc.

A. `new Locale("hi");`
B. `new Locale("hi", "IN");`
C. new Locale("IN");
D. new Locale("IN", "hi");
E. Locale.create("hi");
F. Locale.create("IN");

2. Which of the following are common types to localize? (Choose all that apply.)

The things that make sense to be localized here are specific to the regions or language, like currency, numbers and
dates, most of which are governed by the specific region that they are represented in.

A. Booleans
B. Class names
C. `Currency`
D. `Dates`
E. `Numbers`
F. Variable names

3. Which of the following are true? (Choose all that apply.)

A. All keys must be in the same resource bundle file to be used.
B. All resource bundles defined as Java classes can be expressed using the property file format instead.
C. All resource bundles defined as property files can be expressed using the Java class list bundle format instead.
D. Changing the default locale lasts for only a single run of the program.
E. It is forbidden to have both Props_en.java and Props_en.properties in the class- path of an application.

4. Assume that all bundles mentioned in the answers exist and define the same keys. Which one will be used to find the key in line 8?

```java
Locale.setDefault(new Locale("en", "US"));
ResourceBundle b = ResourceBundle.getBundle("Dolphins");
b.getString("name");
```

A. Dolphins.properties
B. Dolphins_en.java
C. Dolphins_en.properties
D. Whales.properties
E. Whales_en_US.properties
F. The code does not compile.

5. Suppose that we have the following property files and code. Which bundles are used on lines 8 and 9 respectively?

```properties
Dolphins.properties
name=The Dolphin
age=0
Dolphins_en.properties
name=Dolly
age=4
Dolphins_fr.properties
name=Dolly
```

```java
Locale fr = new Locale("fr");
Locale.setDefault(new Locale("en", "US"));
ResourceBundle b = ResourceBundle.getBundle("Dolphins", fr);
b.getString("name");
b.getString("age");
```

A. Dolphins.properties and Dolphins.properties
B. Dolphins.properties and Dolphins_en.properties
C. Dolphins_en.properties and Dolphins_en.properties
D. Dolphins_fr.properties and Dolphins.properties
E. Dolphins_fr.properties and Dolphins_en.properties
F. The code does not compile.

6. Which of the following can be inserted into the blank to create a date of June 21, 2014? (Choose all that apply.)

```java
import java.time.*;
public class StartOfSummer {
    public static void main(String[] args) {
        LocalDate date = -----------------
    }
}
```

The local date can be constructed from both an integer moth value as well as the exposed Month enumeration, both are
valid, the reason for the Month to exist as enumeration is twofold, first it is not possible to confuse the month
and day arguments when using the overloaded method variant, and also the Months are fixed, and that enumeration
implements several Temporal interfaces that allow us to adjust the month values with a relative calculations.

A. new LocalDate(2014, 5, 21);
B. new LocalDate(2014, 6, 21);
C. LocalDate.of(2014, 5, 21);
D. `LocalDate.of(2014, 6, 21);`
E. LocalDate.of(2014, Calendar.JUNE, 21);
F. `LocalDate.of(2014, Month.JUNE, 21);`

7. What is the output of the following code?

```java
LocalDate date = LocalDate.parse("2018–04–30", DateTimeFormatter.ISO_LOCAL_DATE);
date.plusDays(2);
date.plusHours(3);
System.out.println(date.getYear() + " " + date.getMonth() + " "+ date.getDayOfMonth());
```

Local date has not method to add hours to it since is is based on representing only day, month and years. It can not
hold or represent values based on time such as seconds, minutes, hours etc.

A. 2018 APRIL 2
B. 2018 APRIL 30
C. 2018 MAY 2
D. `The code does not compile.`
E. A runtime exception is thrown.

8. What is the output of the following code?

```java
LocalDate date = LocalDate.of(2018, Month.APRIL, 40);
System.out.println(date.getYear() + " " + date.getMonth() + " " + date.getDayOfMonth());
```

The value that is being constructed is incorrect as the day of the month being 40, is invalid therefore the
implementation will throw at runtime at the moment of the construction of the local date.

A. 2018 APRIL 4
B. 2018 APRIL 30
C. 2018 MAY 10
D. Another date
E. The code does not compile.
F. `A runtime exception is thrown.`

9. What is the output of the following code?

```java
LocalDate date = LocalDate.of(2018, Month.APRIL, 30);
date.plusDays(2);
date.plusYears(3);
System.out.println(date.getYear() + " " + date.getMonth() + " " + date.getDayOfMonth());
```

The mutation / adjuster methods on the time based classes indeed exist however just as with String, the
LocalDate/Time classes are immutable meaning that we are NOT going to mutate the actual source but instead create a
new one, every time we adjust the instance with some temporal amount. The date therefore that is going to be printed is the one that was originally created.

A. 2018 APRIL 2
B. `2018 APRIL 30`
C. 2018 MAY 2
D. 2021 APRIL 2
E. 2021 APRIL 30
F. 2021 MAY 2
G. A runtime exception is thrown.

10. What is the output of the following code?

```java
LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
Period p = Period.of(1, 2, 3);
d = d.minus(p);
DateTimeFormatter f = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
System.out.println(d.format(f));
```

A. 3/7/14 11:22 AM
B. 5/10/15 11:22 AM
C. 3/7/14
D. 5/10/15
E. `11:22 AM`
F. The code does not compile.
G. A runtime exception is thrown.

11. What is the output of the following code?

```java
LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
Period p = Period.ofDays(1).ofYears(2);
d = d.minus(p);
DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
System.out.println(f.format(d));
```

The date is the one that has the format

A. 5/9/13 11:22 AM
B. 5/10/13 11:22 AM
C. 5/9/14
D. 5/10/14
E. The code does not compile.
G. A runtime exception is thrown.

12. Which of the answer choices is true given the following code? (Choose all that apply.)

```plaintext
    2016–08–28T05:00 GMT-04:00
    2016–08–28T09:00 GMT-06:00
```

A. The first date/time is earlier.
B. The second date/time is earlier.
C. Both date/times are the same.
D. The date/times are 2 hours apart.
E. The date/times are 6 hours apart.
F. The date/times are 10 hours apart.

13. Note that March 13, 2016, is the weekend that clocks spring ahead for daylight savings time. What is the output of the following?

```java
LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
LocalTime time = LocalTime.of(1, 30);
ZoneId zone = ZoneId.of("US/Eastern");

ZonedDateTime dateTime1 = ZonedDateTime.of(date, time, zone);
ZonedDateTime dateTime2 = dateTime1.plus(1, ChronoUnit.HOURS);

long hours = ChronoUnit.HOURS.between(dateTime1, dateTime2);
int clock1 = dateTime1.getHour();
int clock2 = dateTime2.getHour();

System.out.println(hours + "," + clock1 + "," + clock2);
```

A. 1,1,2
B. 1,1,3
C. 2,1,2
D. 2,1,3
E. The code does not compile.
F. A runtime exception is thrown.

14. Note that March 13, 2016, is the weekend that we spring forward, and November 6, 2016, is when we fall back for
    daylight savings time. Which of the following can fill in the blank without the code throwing an exception?

```java
ZoneId zone = ZoneId.of("US/Eastern");
LocalDate date = ___________________
    LocalTime time1 = LocalTime.of(2, 15);
ZonedDateTime a = ZonedDateTime.of(date4, time1, zone);
```

A. LocalDate.of(2016, 3, 13)
B. LocalDate.of(2016, 3, 40)
C. LocalDate.of(2016, 11, 6)
D. LocalDate.of(2016, 11, 7)
E. LocalDate.of(2017, 2, 29)

15. Given the following code, which of the answer choices can fill in the blank to print true?
    (Choose all that apply.)
    String m1 = Duration.of(1, ChronoUnit.MINUTES).toString();
    String m2 = Duration.ofMinutes(1).toString();
    String s = Duration.of(60, ChronoUnit.SECONDS).toString();
    String d = Duration.ofDays(1).toString();
    String p = Period.ofDays(1).toString();
    System.out.println(
    );
    A. m1 == m2
    B.m1.equals(m2)
    C.m1.equals(s)
    D.d == p
    E.d.equals(p)
16. Given the following, which answers can correctly fill in the blank? (Choose all that apply.)
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
    LocalDateTime dateTime = LocalDateTime.now();
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);
    long epochSeconds = 0;
    ;
    Instant instant =
    A. Instant.now()
    B.Instant.ofEpochSecond(epochSeconds)
    C.date.toInstant()
    D.dateTime.toInstant()
    E.time.toInstant()
    F.zonedDateTime.toInstant()
17. What is the output of the following method if props contains {veggies=brontosaurus,
    meat=velociraptor}?
    private static void print(Properties props) {
    System.out.println(props.get("veggies", "none")

- " " + props.get("omni", "none"));
  }
  WOW! eBook
  www.wowebook.orgChapter 5 ■ Dates, Strings, and Localization
  282
  A. brontosaurus none
  B.brontosaurus null
  C.none none
  D.none null
  E.The code does not compile.
  F.A runtime exception is thrown.

18. Which of the following prints out all of the values in props?
    A. props.keys().stream().map(k -> k .forEach(System.out::println);
    B.props.keys().stream().map(k -> props.get(k))
    .forEach(System.out::println);
    C.props.keySet().stream().map(k -> k) .forEach(System.out::println);
    D.props.keySet().stream().map(k -> props.get(k))
    .forEach(System.out::println);
    E.props.stream().map(k -> k) .forEach(System.out::println);
    F.props.stream().map(k -> props.get(k)) .forEach(System.out::println);
19. Which of the following are stored in a Period object? (Choose all that apply.)
    A. Year
    B.Month
    C.Day
    D.Hour
    E.Minute
    F.Second
20. Which of the following objects could contain the information “eastern standard time”?
    (Choose all that apply.)
    A. Instant
    B.LocalDate
    C.LocalDateTime
    D.LocalTime
    E.ZonedDateTime
