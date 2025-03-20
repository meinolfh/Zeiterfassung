package de.mherbst.zeiterfassung;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public final class CalcTime {

    // Ermittlung der aktuellen Startzeit
    private static final LocalTime aktStartzeit = LocalTime.of ( LocalTime.now ( ).getHour ( ), LocalTime.now ( ).getMinute ( ), 0 );

    // Benötigte Variablen zur Differenzbildung
    private static LocalTime kommenZeit = null;
    private static LocalTime gehenZeit = null;

    /**
     * Don't let anyone instantiate this class.
     */
    private CalcTime () {
    }

    /**
     * Setter für die kommenZeit
     *
     * @param kommen als LocalTime-Objekt
     */
    public static void setKommenZeit ( LocalTime kommen ) {
        kommenZeit = kommen;
    }

    /**
     * Setter für die gehenZeit
     *
     * @param gehen als LocalTime-Objekt
     */
    public static void setGehenZeit ( LocalTime gehen ) {
        gehenZeit = gehen;
    }

    /**
     * @return Zeitdifferenz zwischen kommenZeit und gehenZeit in Minuten (Anwesenheitszeit)
     */
    public static int getTimeDiff () {
        int diff = 0;
        if ( gehenZeit != null && kommenZeit != null ) {
            int gehenMinuten = 60 * gehenZeit.getHour ( ) + gehenZeit.getMinute ( );
            int kommenMinuten = 60 * kommenZeit.getHour ( ) + kommenZeit.getMinute ( );
            diff = Math.abs ( gehenMinuten - kommenMinuten );
        }
        return diff;
    }

    /**
     * Ermittelt die Pausenzeit aus der Anwesenheitszeit
     *
     * @return Pausenzeit in Minuten
     */
    public static int calcPause () {
        int anwesenheit = getTimeDiff ( );

        int pause;
        if ( anwesenheit < 360 ) {
            pause = 0;
        } else if ( anwesenheit < 570 ) {
            pause = 30;
        } else if ( anwesenheit < 585 ) {
            pause = anwesenheit - 540;
        } else {
            pause = 45;
        }
        return pause;
    }

    /**
     * Ermittelt die Arbeitszeit aus der Anwesenheit und der Pause
     *
     * @return Arbeitszeit in Minuten
     */
    public static int calcWorktime () {
        int anwesenheit = getTimeDiff ( );
        int pause = calcPause ( );
        return anwesenheit - pause;
    }

    /**
     *
     * @return Arbeitszeit in Industrieangaben "hh,mm"
     */
    public static String calcIndustryTime () {
        int arbeitszeit = calcWorktime ( );
        int stunden = arbeitszeit / 60;
        int minuten = arbeitszeit - 60 * stunden;
        float industrieMinuten = ( float ) ( minuten / 60.0 * 100.0 );
        return String.format ( "%d,%.0f", stunden, industrieMinuten );
    }

    /**
     * Ermittelt die aktuelle Startzeit des Programms
     *
     * @return aktStartzeit
     */
    public static LocalTime getStartzeit () {
        return aktStartzeit;
    }

    /**
     * Addiert zur übergebenen Zeit die Anzahl minutes
     *
     * @param localTime beliebiges Local-Time-Objekt
     * @param minutes   werden auf die localTime aufaddiert
     * @return LocalTime-Objekt mit den aufaddierten minutes
     */
    public static LocalTime addMinutesToLocalTime ( @NotNull LocalTime localTime, int minutes ) {
        return localTime.plusMinutes ( minutes );
    }

    /**
     * Wandelt das String-Objekt uhrzeit in ein LocalTime-Objekt um
     *
     * @param uhrzeit als String
     * @return LocalTime-Objekt des Arguments string
     */
    public static LocalTime convertStringToLocalTime ( @NotNull String uhrzeit ) {
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder ( )
                .parseCaseInsensitive ( )
                .appendPattern ( "HH:mm" )
                .toFormatter ( Locale.GERMAN );
        return LocalTime.parse ( uhrzeit, timeFormatter );
    }

    /**
     * Konvertiert das Argument minuten in das Format "hh:mm"
     *
     * @param minuten im Bereich 0 .. 1440 (24 h)
     * @return formatierter String "hh:mm" des Arguments minuten
     */
    public static String convertMinutesToTimeString ( int minuten ) {
        int diffStunden = minuten / 60;
        int diffMinuten = minuten - 60 * diffStunden;
        return convertIntegersToTimeString ( diffStunden, diffMinuten );
    }

    /**
     * @param localTime beliebiges Local-Time-Objekt
     * @return formatierter String "hh:mm" des Arguments localTime
     */
    public static String convertLocalTimeToTimeString ( @NotNull LocalTime localTime ) {
        return String.format ( "%02d:%02d", localTime.getHour ( ), localTime.getMinute ( ) );
    }

    /**
     * @param stunden im Bereich von 00-23
     * @param minuten im Bereich von 00-59
     * @return formatierter String "hh:mm" der Argumente stunden und minuten
     */
    public static String convertIntegersToTimeString ( int stunden, int minuten ) {
        return String.format ( "%02d:%02d", stunden, minuten );
    }

    /**
     * @param pattern
     * @return true if pattern matches "hh:mm"
     */
    public static boolean checkTimePattern ( String pattern ) {
        boolean isDigit = false;
        int stunde;
        int minute;

        String newPattern = pattern.trim ( );
        int firstColon = newPattern.indexOf ( ':' );
        int len = newPattern.length ( );

        if ( len == 5 && firstColon == 2 ) {
            String hourString = newPattern.substring ( 0, 2 );
            String minString = newPattern.substring ( 3, 5 );
            try {
                stunde = Integer.parseInt ( hourString );
                minute = Integer.parseInt ( minString );
                isDigit = true;
            } catch (NumberFormatException ignored) {
            }
        }
        return isDigit;
    }
}
