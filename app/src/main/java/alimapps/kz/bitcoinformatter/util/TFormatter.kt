package alimapps.kz.bitcoinformatter.util

import alimapps.kz.bitcoinformatter.classes.Consts
import alimapps.kz.bitcoinformatter.classes.DatePeriod
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TFormatter {

    const val TYPE_WEEK = 0
    const val TYPE_MONTH = 1
    const val TYPE_YEAR = 2

    @JvmStatic
    fun formatPrice(value: String, code: String): String {
        return "$value ${formatSymbol(code)}"
    }

    @JvmStatic
    fun formatSymbol(code: String): String {
        return when (code) {
            Consts.CODE_KZT -> Consts.SYMBOL_KZT
            Consts.CODE_USD -> Consts.SYMBOL_USD
            Consts.CODE_EUR -> Consts.SYMBOL_EUR
            else -> ""
        }
    }

    @JvmStatic
    fun getDatePeriod(currentDateAsLong: Long, dateType: Int): DatePeriod {
        var dayIndicator = 0
        var amountOfDay = 0

        when (dateType) {
            TYPE_WEEK -> {
                dayIndicator = Calendar.DAY_OF_YEAR
                amountOfDay = -7
            }
            TYPE_MONTH -> {
                dayIndicator = Calendar.MONTH
                amountOfDay = -1
            }
            TYPE_YEAR -> {
                dayIndicator = Calendar.YEAR
                amountOfDay = -1
            }
        }

        val endDate = Date(currentDateAsLong)
        val c = Calendar.getInstance()
        c.timeInMillis = currentDateAsLong
        c.roll(dayIndicator, amountOfDay)
        val startDate = c.time

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedStartDate = formatter.format(startDate)
        val formattedEndDate = formatter.format(endDate)

        return DatePeriod(formattedStartDate, formattedEndDate)
    }

    @JvmStatic
    fun getDateAsDay(day: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = formatter.parse(day)
        val formattedDate = DateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale("ru")).format(date)
        return formattedDate.removeRange(formattedDate.length - 8, formattedDate.length)
    }

    @JvmStatic
    fun asDate(currentDate: Long): String {
        val date = Date(currentDate)
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
        return formatter.format(date)
    }

}