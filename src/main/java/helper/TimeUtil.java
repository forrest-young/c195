package helper;
import model.Appointment;

import javafx.scene.control.Alert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class TimeUtil {
    /**
     * Local variables
     */
    private static final Locale loc = Locale.getDefault();
    private static final ZoneId localZoneId = ZoneId.systemDefault();
    private static final ZoneId utcZoneId = ZoneId.of("Etc/UTC");
    private static final ZoneId estZoneId = ZoneId.of("America/New_York");
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final LocalTime openHour = LocalTime.of(8, 0, 0);
    public static final LocalTime closeHour = LocalTime.of(22, 0, 0);

    /**
     * Method used to get timestamp for UTC zone ID
     *
     * @return the zoned timestamp
     */
    public static LocalDateTime utcNow(){ return LocalDateTime.now(utcZoneId); }

    /**
     * Method used to get timestamp for user's system default zone ID
     *
     * @return the zoned timestamp
     */
    public static LocalDateTime localNow(){ return LocalDateTime.now(localZoneId); }

    /**
     * Method used to convert a UTC zoned timestamp to a user's system default timestamp
     *
     * @param ldt the timestamp to be converted
     * @return the converted timestamp
     */
    public static LocalDateTime utcToLocal(LocalDateTime ldt){
        ZonedDateTime zdtUtc = ZonedDateTime.of(ldt, utcZoneId);
        Instant instant = zdtUtc.toInstant();
        return LocalDateTime.ofInstant(instant, localZoneId);
    }

    /**
     * Method used to convert a user's system default zoned timestamp to a UTC timestamp
     *
     * @param ldt the timestamp to be converted
     * @return the converted timestamp
     */
    public static LocalDateTime localToUtc(LocalDateTime ldt) {
        ZonedDateTime zdtLocal = ZonedDateTime.of(ldt, localZoneId);
        Instant instant = zdtLocal.toInstant();
        return LocalDateTime.ofInstant(instant, utcZoneId);
    }

    /**
     * Method used to convert a user's system default zoned timestamp to an EST timestamp
     *
     * @param ldt the timestamp to be converted
     * @return the converted timestamp
     */
    public static LocalDateTime localToEst(LocalDateTime ldt) {
        ZonedDateTime zdtLocal = ZonedDateTime.of(ldt, localZoneId);
        Instant instant = zdtLocal.toInstant();
        return LocalDateTime.ofInstant(instant, estZoneId);
    }

    /**
     * Method used to get starting hour from an appointment's start time
     *
     * @param appt the appointment to parse
     * @return the hour parsed
     */
    public static int getStartHour(Appointment appt) {
        CharSequence cs = String.valueOf(appt.getStart());
        return Integer.parseInt(cs, 11, 13, 10);
    }

    /**
     * Method used to get starting minute from an appointment's start time
     *
     * @param appt the appointment to parse
     * @return the minute parsed
     */
    public static int getStartMin(Appointment appt) {
        CharSequence cs = String.valueOf(appt.getStart());
        return Integer.parseInt(cs, 14, 16, 10);
    }

    /**
     * Method used to get ending hour from an appointment's end time
     *
     * @param appt the appointment to parse
     * @return the hour parsed
     */
    public static int getEndHour(Appointment appt) {
        CharSequence cs = String.valueOf(appt.getEnd());
        return Integer.parseInt(cs, 11, 13, 10);
    }

    /**
     * Method used to get ending minute from an appointment's end time
     *
     * @param appt the appointment to parse
     * @return the minute parsed
     */
    public static int getEndMin(Appointment appt) {
        CharSequence cs = String.valueOf(appt.getEnd());
        return Integer.parseInt(cs, 14, 16, 10);
    }

    /**
     * Method used to assist filling the appointment form
     *
     * @param date  Starting date
     * @param sHour Starting hour
     * @param sMin  Starting minute
     * @param eHour Ending hour
     * @param eMin  Ending minute
     * @return the end date
     */
    public static LocalDate getEndDate(LocalDate date, String sHour, String sMin, String eHour, String eMin) {
        if (Integer.parseInt(sHour) < Integer.parseInt(eHour) || (Integer.parseInt(sHour) == Integer.parseInt(eHour) && Integer.parseInt(sMin) <= Integer.parseInt(eMin))) return date;
        else return date.plusDays(1);
    }

    /**
     * Method used to verify an appointment's validity
     *
     * @param id    Appointment ID
     * @param start Appointment Start Time
     * @param end   Appointment End Time
     * @return whether appointment is valid
     */
    public static boolean isValid(int id, LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end) || start.equals(end)){
            alert(0); return false;
        }else if (start.isBefore(localNow())) {
            alert(1); return false;
        }else if (!btwnBusinessHours(start, end)) {
            alert(2); return false;
        }else if (SQLUtil.isOverlapping(id, start, end)) {
            alert(3); return false;
        }
        return true;
    }

    /**
     * Method used to verify an appointment's validity: checks opening and closing hours
     *
     * @param start Appointment Start Time
     * @param end   Appointment End Time
     * @return whether appointment is during business hours
     */
    private static boolean btwnBusinessHours(LocalDateTime start, LocalDateTime end) {
        if (localToUtc(start).toLocalDate().equals(localToUtc(end).toLocalDate())) {
            LocalTime slt = localToEst(start).toLocalTime();
            LocalTime elt = localToEst(end).toLocalTime();
            if ((slt.equals(openHour) || slt.isAfter(openHour) && slt.isBefore(closeHour)) && (elt.equals(closeHour) || elt.isBefore(closeHour) && elt.isAfter(openHour))) return true;
        }
        return false;
    }

    /**
     * Method used to inform user of alert cases
     *
     * @param alertCase the alert case to be handled
     */
    private static void alert(int alertCase) {
        ResourceBundle rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());

        switch (alertCase) {
            case 0 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah9"));
                alert.setContentText(rb.getString("ac12"));
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah9"));
                alert.setContentText(rb.getString("ac13"));
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah9"));
                alert.setContentText(rb.getString("ac15"));
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah9"));
                alert.setContentText(rb.getString("ac14"));
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }

    /**
     * TimeUtil getters
     */
    public static Locale getLoc(){ return loc; }
    public static ZoneId getLocalZone(){ return localZoneId; }
}