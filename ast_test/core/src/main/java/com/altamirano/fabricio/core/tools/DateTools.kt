package com.altamirano.fabricio.core.tools

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @autor Created by Fabricio Altamirano on 7/3/18.
 */
object DateTools {
    private val formatComplete: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")

    /**
     * @return Return calendar as yyyy-MM-dd 23:59:59
     */
    val lastHourOfDay: Calendar
        get() {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 23
            calendar[Calendar.MINUTE] = 59
            calendar[Calendar.SECOND] = 59
            return calendar
        }

    /**
     * @return Return calendar as yyyy-MM-dd 23:59:59
     */
    val startHourOfDay: Calendar
        get() {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            return calendar
        }

    /**
     * @param dayOfMonth Dia del mes
     * @param monthOfYear Mes del año
     * @param year Año
     * @return Return calendar as yyyy-MM-dd 00:00:00
     */
    fun createCalendar(dayOfMonth: Int, monthOfYear: Int, year: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        calendar[Calendar.MONTH] = monthOfYear
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        calendar[Calendar.YEAR] = year
        return calendar
    }

    /**
     * @return Current Calendar as java.util.Date
     */
    val currentDate: Date
        get() = Calendar.getInstance().time

    /**
     * @return date with format utc example SPAIN Summer -2 Winter -1
     */
    @get:Throws(ParseException::class)
    val dateCurrentUTC: String
        get() {
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return formatter.format(Date())
        }

    /**
     * @return The Date in 01/01/yyyy 00:00:00
     */
    val startYear: Date
        get() {
            val cl = Calendar.getInstance()
            cl[Calendar.MONTH] = 0
            cl[Calendar.DAY_OF_MONTH] = cl.getMinimum(Calendar.DAY_OF_MONTH)
            cl[Calendar.HOUR] = 0
            cl[Calendar.MINUTE] = 0
            cl[Calendar.SECOND] = 0
            return cl.time
        }

    /**
     * @return The Date in 12/31/yyyy 00:00:00
     */
    val endYear: Date
        get() {
            val cl = Calendar.getInstance()
            cl[Calendar.MONTH] = cl.getMaximum(Calendar.MONTH)
            cl[Calendar.DAY_OF_MONTH] = 31
            cl[Calendar.HOUR_OF_DAY] = 23
            cl[Calendar.MINUTE] = 59
            cl[Calendar.SECOND] = 59
            return cl.time
        }

    /**
     * @param date  yyyy-MM-dd HH:mm:ss
     * @return java.util.Date
     */
    fun getDateFromSqlite(date: String?): Date? {
        return if (date == null) {
            null
        } else getDateFromString(date)
    }

    /**
     * @param date Parameter type java.util.Date
     * @return Date as string with format as yyyy-MM-dd HH:mm:ss
     */
    fun getDateToSqlite(date: Date?): String {
        return if (date == null) {
            ""
        } else formatComplete.format(date)
    }

    /**
     * @param date type string Caution when using this method the format has to be exact
     * @return if string date is null or empty return *null* if not parse as yyyy-MM-dd HH:mm:ss
     */
    private fun getDateFromString(date: String?): Date? {
        return if (date == null || date == "") {
            null
        } else try {
            // sometimes error parse to dates only simpleformat, using to Fixes dates
            val cl = Calendar.getInstance()
            // yyyy-MM-dd
            val y = Integer.valueOf(date.substring(0, 4))
            val MM = Integer.valueOf(date.substring(5, 7))
            val d = Integer.valueOf(date.substring(8, 10))
            cl[Calendar.YEAR] = y
            cl[Calendar.MONTH] = MM - 1
            cl[Calendar.DAY_OF_MONTH] = d
            if (date.length == 10) {
                return cl.time
            }

            // HH:mm:ss
            val h = Integer.valueOf(date.substring(11, 13))
            val m = Integer.valueOf(date.substring(14, 16))
            val s = Integer.valueOf(date.substring(17, 19))
            cl[Calendar.HOUR_OF_DAY] = h
            cl[Calendar.MINUTE] = m
            cl[Calendar.SECOND] = s
            cl.time
        } catch (ex: Exception) {
            ex.printStackTrace()
            //ServiceLog.senTraceFabric("Formats", "getDateFromString", "Error parse " + date + "\n" + ex.getMessage());
            null
        }
    }
}