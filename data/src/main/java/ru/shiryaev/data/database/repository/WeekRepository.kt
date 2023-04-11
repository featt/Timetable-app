package ru.shiryaev.data.database.repository

import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.Storage
import ru.shiryaev.data.database.dao.ScheduleDao
import ru.shiryaev.data.database.dao.WeekDao
import ru.shiryaev.domain.models.Week
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeekRepository {

    @Inject
    lateinit var mStorage: Storage

    private val mScheduleDao: ScheduleDao
    private val mWeekDao: WeekDao

    init {
        AppDelegate.getAppComponent().injectWeekRepository(this)
        mScheduleDao = mStorage.getScheduleDao()
        mWeekDao = mStorage.getWeekDao()
    }

    fun getWeeks() = mWeekDao.getAllWeek()

    fun getCountWeek() = mWeekDao.getCountWeek()

    fun insertWeek(week: Week): Completable = Completable.fromRunnable { mWeekDao.insertWeek(week) }
                .subscribeOn(Schedulers.io())

    fun updateWeek(week: Week): Completable = Completable.fromRunnable { mWeekDao.updateWeek(week) }
                .subscribeOn(Schedulers.io())

    fun deleteWeek(week: Week): Completable = Completable.fromRunnable { mWeekDao.deleteWeek(week) }
                .subscribeOn(Schedulers.io())

    fun updateWeekSchedule(oldNameWeek: String, newNameWeek: String): Completable = Completable.fromRunnable { mScheduleDao.updateWeekSchedule(oldNameWeek, newNameWeek) }
                .subscribeOn(Schedulers.io())

    fun deleteWeekSchedule(nameWeek: String): Completable = Completable.fromRunnable { mScheduleDao.deleteWeekSchedule(nameWeek) }
            .subscribeOn(Schedulers.io())
}