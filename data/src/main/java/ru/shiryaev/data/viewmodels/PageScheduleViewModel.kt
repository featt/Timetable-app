package ru.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.repository.ScheduleRepository
import ru.shiryaev.domain.models.Schedule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PageScheduleViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: ScheduleRepository

    private val mListSchedule = MutableLiveData<ArrayList<ArrayList<Schedule>>>()
    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsErrorVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectPageScheduleViewModel(this)
    }

    fun getIsLoading() = mIsLoading

    fun getIsErrorVisible() = mIsErrorVisible

    fun setIsErrorVisible(value: Boolean) { mIsErrorVisible.value = value }

    fun getSchedules(mDay: Int) = mRepository.getSchedules(mDay)

    fun getSchedules(mDay: Int, mWeek: String) = mRepository.getSchedules(mDay, mWeek)

    fun getCorrectListSchedule() = mListSchedule

    fun setListSchedule(list: List<Schedule>) {
        Observable.fromCallable {
            if (list.isNotEmpty()) {
                getListScheduleByDay(ArrayList(list))
            } else {
                ArrayList()
            }
        }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    mIsLoading.postValue(true)
                    mIsErrorVisible.postValue(false)
                }
                .doFinally { mIsLoading.postValue(false) }
                .subscribe { newList ->
                    mListSchedule.postValue(newList)
                }
    }

    fun getWeeks() = mRepository.getWeeks()

    fun deleteSchedule(schedule: Schedule) {
        mRepository.deleteSchedule(schedule)
            .doOnSubscribe { mIsLoading.postValue(true) }
            .doFinally { mIsLoading.postValue(false) }
            .subscribe {  }
    }

    private fun getListScheduleByDay(list: ArrayList<Schedule>): ArrayList<ArrayList<Schedule>> {
        val newListSchedules: java.util.ArrayList<java.util.ArrayList<Schedule>> = arrayListOf(arrayListOf())
        var timeTemp = list.first().mTimeStart
        for (item in list) {
            if (item.mTimeStart != timeTemp) {
                newListSchedules.add(arrayListOf())
                timeTemp = item.mTimeStart
            }
            newListSchedules[newListSchedules.size - 1].add(item)
        }
        return newListSchedules
    }
}