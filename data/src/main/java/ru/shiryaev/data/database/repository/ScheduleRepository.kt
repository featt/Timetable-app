package ru.shiryaev.data.database.repository

import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.Storage
import ru.shiryaev.data.database.dao.ScheduleDao
import ru.shiryaev.data.database.dao.WeekDao
import ru.shiryaev.domain.models.Schedule
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ScheduleRepository {

    private var mScheduleDao: ScheduleDao
    private var mWeekDao: WeekDao

    @Inject
    lateinit var mStorage: Storage

    init {
        AppDelegate.getAppComponent().injectScheduleRepository(this)
        mScheduleDao = mStorage.getScheduleDao()
        mWeekDao = mStorage.getWeekDao()
    }

    fun getWeeks() = mWeekDao.getAllWeek()

    fun getSchedules(day: Int) = mScheduleDao.getAllByDay(day)

    fun getSchedules(day: Int, week: String) = mScheduleDao.getAllByDayForWeek(day, week)

    fun getLessons() = mScheduleDao.getLessons()

    fun getTeachers() = mScheduleDao.getTeachers()

    fun getAudits() = mScheduleDao.getAudits()

    fun getExams() = mScheduleDao.getExams()

    fun getTime() = mScheduleDao.getTime()

    fun getTimeStartByDay(mDay: Int) = mScheduleDao.getTimeStartByDay(mDay)

    fun insertSchedule(schedule: Schedule) {
        Completable.fromRunnable { mScheduleDao.insertSchedule(schedule) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateSchedule(schedule: Schedule) {
        Completable.fromRunnable { mScheduleDao.updateSchedule(schedule) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun deleteSchedule(schedule: Schedule): Completable = Completable.fromRunnable { mScheduleDao.deleteSchedule(schedule) }
            .subscribeOn(Schedulers.io())
}