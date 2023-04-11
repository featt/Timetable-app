package ru.shiryaev.domain.utils

import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.Week

fun <T: Any?> nonNullValues(values: List<T?>): List<T> = values.filterNotNull()

fun sortWeeks(schedules: ArrayList<Schedule>, weeks: List<Week>): ArrayList<Schedule> {
    val newListSchedules: ArrayList<Schedule> = arrayListOf()
    weeks.forEach { week ->
        schedules.forEach { schedule ->
            if (schedule.mWeek == week.mName) {
                newListSchedules.add(schedule)
            }
        }
    }
    schedules.forEach { schedule ->
        if (schedule.mWeek == "") {
            newListSchedules.add(schedule)
        }
    }
    return newListSchedules
}