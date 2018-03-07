package com.altamirano.fabricio.libraryast;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @autor Created by Fabricio Altamirano on 7/3/18.
 */

public class DateTools {

    private static DateFormat formatComplete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    /**
     * @return Return calendar as yyyy-MM-dd 23:59:59
     */
    public static Calendar getLastHourOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar;
    }

    /**
     * @param dayOfMonth Dia del mes
     * @param monthOfYear Mes del año
     * @param year Año
     * @return Return calendar as yyyy-MM-dd 00:00:00
     */
    public static Calendar createCalendar(int dayOfMonth, int monthOfYear, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    /**
     * @return Current Calendar as java.util.Date
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * @return date with format utc example SPAIN Summer -2 Winter -1
     */
    public static Date getDateCurrentUTC() throws ParseException {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String d = formatter.format(date);
        return getDateFromSqlite(d);
    }

    /**
     * @return The Date in 01/01/yyyy 00:00:00
     */
    public static Date getStartYear(){
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.MONTH,0);
        cl.set(Calendar.DAY_OF_MONTH, cl.getMinimum(Calendar.DAY_OF_MONTH));
        cl.set(Calendar.HOUR,0);
        cl.set(Calendar.MINUTE,0);
        cl.set(Calendar.SECOND,0);
        return  cl.getTime();
    }

    /**
     * @return The Date in 12/31/yyyy 00:00:00
     */
    public static Date getEndYear(){
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.MONTH, cl.getMaximum(Calendar.MONTH));
        cl.set(Calendar.DAY_OF_MONTH, 31);
        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 59);
        cl.set(Calendar.SECOND, 59);
        return  cl.getTime();
    }

    /**
     * @param date  yyyy-MM-dd HH:mm:ss
     * @return java.util.Date
     */
    public static Date getDateFromSqlite(String date) {
        if (date == null) {
            return null;
        }
        return getDateFromString(date);
    }

    /**
     * @param date Parameter type java.util.Date
     * @return Date as string with format as yyyy-MM-dd HH:mm:ss
     */
    public static String getDateToSqlite(Date date) {
        if (date == null) {
            return "";
        }
        return formatComplete.format(date);
    }

    /**
     * @param date type string Caution when using this method the format has to be exact
     * @return if string date is null or empty return *null* if not parse as yyyy-MM-dd HH:mm:ss
     */
    private static Date getDateFromString(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        try {
            // sometimes error parse to dates only simpleformat, using to Fixes dates
            Calendar cl = Calendar.getInstance();
            // yyyy-MM-dd
            Integer y = Integer.valueOf(date.substring(0, 4));
            Integer MM = Integer.valueOf(date.substring(5, 7));
            Integer d = Integer.valueOf(date.substring(8, 10));

            cl.set(Calendar.YEAR, y);
            cl.set(Calendar.MONTH, MM - 1);
            cl.set(Calendar.DAY_OF_MONTH, d);

            if (date.length() == 10) {
                return cl.getTime();
            }

            // HH:mm:ss
            Integer h = Integer.valueOf(date.substring(11, 13));
            Integer m = Integer.valueOf(date.substring(14, 16));
            Integer s = Integer.valueOf(date.substring(17, 19));

            cl.set(Calendar.HOUR_OF_DAY, h);
            cl.set(Calendar.MINUTE, m);
            cl.set(Calendar.SECOND, s);

            return cl.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
            //ServiceLog.senTraceFabric("Formats", "getDateFromString", "Error parse " + date + "\n" + ex.getMessage());
            return null;
        }
    }
}
