package ru.shiryaev.domain.utils

import ru.shiryaev.domain.models.TimeAndWeek

object UtilsChecks {

    const val TIME_DISABLE = -1

    fun checkTime(week: String, selectedTime: Int, listTime: ArrayList<TimeAndWeek>) : Boolean {
        var flag = true
        for (i in listTime.indices) {
            if (listTime[i].mTimeStart == selectedTime) {
                flag = when {
                    listTime[i].mWeek == "" -> false
                    listTime[i].mWeek == week -> false
                    listTime[i].mWeek != "" && week == "" -> false
                    else -> true
                }
            }
        }
        return flag
    }

    fun checkAddSchedule(lesson: String, time: Int) =  (lesson.isNotBlank() || lesson != "") && time != TIME_DISABLE

    fun checkAddNote(note: String) =  (note.isNotBlank() || note != "")
}