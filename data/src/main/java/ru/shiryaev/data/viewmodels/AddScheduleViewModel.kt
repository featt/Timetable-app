package ru.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.repository.ScheduleRepository
import ru.shiryaev.domain.models.Schedule
import javax.inject.Inject

class AddScheduleViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: ScheduleRepository

    private val mFabIsVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectAddScheduleViewModel(this)
    }

    fun getWeeks() = mRepository.getWeeks()

    fun getListLessons() = mRepository.getLessons()

    fun getListTeachers() = mRepository.getTeachers()

    fun getListAudits() = mRepository.getAudits()

    fun getListExams() = mRepository.getExams()

    fun setFabIsVisible(value: Boolean) { mFabIsVisible.value = value }

    fun getFabIsVisible() = mFabIsVisible

    fun getListTime() = mRepository.getTime()

    fun getTimeStartByDay(mDay: Int) = mRepository.getTimeStartByDay(mDay)

    fun insertSchedule(schedule: Schedule) { mRepository.insertSchedule(schedule) }

    fun updateSchedule(schedule: Schedule) { mRepository.updateSchedule(schedule) }
}