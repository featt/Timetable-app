package ru.shiryaev.domain.utils

import java.text.SimpleDateFormat
import java.util.*

object UtilsConvert {
    fun format(time: Long): String {
        val date = Date(time + 1000L)
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    fun convertToCorrectTime(time: Int) = if(time % 100 < 10) "0${time % 100}" else "${time % 100}"

    fun convertTimeIntToString(time: Int) = if (time != UtilsChecks.TIME_DISABLE) convertToCorrectTime(time / 100) + ":" + convertToCorrectTime(time % 100) else ""
}