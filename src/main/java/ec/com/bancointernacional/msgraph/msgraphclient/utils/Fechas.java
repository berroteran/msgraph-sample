package ec.com.bancointernacional.msgraph.msgraphclient.utils;

import com.microsoft.graph.models.DateTimeTimeZone;
import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

public class Fechas {
    final public static String MY_TIME_ZONE = "America/Lima";

    final static String FECHA_TIME_FORMATO = "yyyy-MM-dd HH:mm:ss";
    final static String FECHA_FORMATO_UNIVERSAL = "yyyyMMdd";
    final static String FECHA_FORMATO_HUMAN = "dd/MM/yyyy";

    final static DateTimeFormatter fechaTimeFormato = DateTimeFormatter.ofPattern( FECHA_TIME_FORMATO );

    public static LocalDate getDateToday () {
        LocalDate currentDate = LocalDate.now( ZoneId.of( MY_TIME_ZONE ) );
        return currentDate;
    }
    public static LocalDateTime getTimeDateNow () {
        LocalDateTime currentDate = LocalDateTime.now( ZoneId.of( MY_TIME_ZONE ) );
        return currentDate;
    }
    public static int getDiffDay (LocalDate fechaInicial, LocalDate fechaFinal) {
        int dato = Math.toIntExact( DAYS.between( fechaInicial, fechaFinal ) );
        return dato;
    }

    public static String getDateTimeFormatComputer(DateTimeTimeZone start) {
        return DateTimeFormatter.ofPattern(FECHA_FORMATO_UNIVERSAL, Locale.ENGLISH).format((TemporalAccessor) start);
    }

    public LocalDate getDatePlusDayHability(LocalDate startDate, int days){
        //TODO create u obtener lista de dias habiles.
        LocalDate  endDate = startDate;

        return endDate;
    }

    public static DateTimeFormatter getFormatDateUniversal () {
        return DateTimeFormatter.ofPattern( FECHA_FORMATO_UNIVERSAL );
    }

    public static DateTimeFormatter getDateTimeFormat () {
        return fechaTimeFormato;
    }

    public static String getDateFormatComputer (LocalDateTime fecha) {
        return DateTimeFormatter.ofPattern(FECHA_FORMATO_UNIVERSAL, Locale.ENGLISH).format(fecha);
    }

    public static String getDateTimeFormatComputer (LocalDateTime fecha) {
        return DateTimeFormatter.ofPattern(FECHA_TIME_FORMATO, Locale.ENGLISH).format(fecha);
    }


    public static LocalDate addDaysTodaySkippingWeekends( int days) {
        return addDaysSkippingWeekends( getDateToday(), days);
    }

    public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }
}
