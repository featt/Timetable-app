package ru.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.repository.WeekRepository
import ru.shiryaev.domain.models.Week
import javax.inject.Inject

class WeekSettingsViewModel : ViewModel() {

    @Inject
    lateinit var mWeekRepository: WeekRepository

    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsErrorVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectWeekSettingsViewModel(this)
    }

    fun getIsLoading() = mIsLoading

    fun getIsErrorVisible() = mIsErrorVisible

    fun setIsErrorVisible(value: Boolean) { mIsErrorVisible.value = value }

    fun setIsLoading(value: Boolean) { mIsLoading.postValue(value) }

    fun getWeeks() = mWeekRepository.getWeeks()

    fun getCountWeek() = mWeekRepository.getCountWeek()

    fun insertWeek(week: Week) {
        mWeekRepository.insertWeek(week)
                .doOnSubscribe { mIsLoading.postValue(true) }
                .doFinally { mIsLoading.postValue(false) }
                .doOnError { mIsErrorVisible.postValue(true) }
                .doOnComplete { mIsErrorVisible.postValue(false) }
                .subscribe()
    }

    fun updateWeek(week: Week) {
        mWeekRepository.updateWeek(week)
                .doOnSubscribe { mIsLoading.postValue(true) }
                .doFinally { mIsLoading.postValue(false) }
                .doOnError { mIsErrorVisible.postValue(true) }
                .doOnComplete { mIsErrorVisible.postValue(false) }
                .subscribe()
    }

    fun deleteWeek(week: Week) {
        mWeekRepository.deleteWeek(week)
                .doOnSubscribe { mIsLoading.postValue(true) }
                .doFinally { mIsLoading.postValue(false) }
                .doOnError { mIsErrorVisible.postValue(true) }
                .doOnComplete { mIsErrorVisible.postValue(false) }
                .subscribe()
    }

    fun updateWeekSchedule(oldNameWeek: String, newNameWeek: String) {
        mWeekRepository.updateWeekSchedule(oldNameWeek, newNameWeek)
                .doOnSubscribe { mIsLoading.postValue(true) }
                .doFinally { mIsLoading.postValue(false) }
                .doOnError { mIsErrorVisible.postValue(true) }
                .doOnComplete { mIsErrorVisible.postValue(false) }
                .subscribe()
    }

    fun deleteWeekSchedule(nameWeek: String) {
        mWeekRepository.deleteWeekSchedule(nameWeek)
                .doOnSubscribe { mIsLoading.postValue(true) }
                .doFinally { mIsLoading.postValue(false) }
                .doOnError { mIsErrorVisible.postValue(true) }
                .doOnComplete { mIsErrorVisible.postValue(false) }
                .subscribe()
    }
}