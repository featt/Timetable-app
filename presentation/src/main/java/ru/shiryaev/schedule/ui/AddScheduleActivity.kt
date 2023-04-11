package ru.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.TimeAndWeek
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.*
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import ru.shiryaev.data.common.CustomFactory
import ru.shiryaev.domain.utils.UtilsChecks
import ru.shiryaev.data.viewmodels.AddScheduleViewModel
import ru.shiryaev.schedule.common.CallDialogs
import ru.shiryaev.schedule.common.navigation.ActivityClass
import ru.shiryaev.schedule.ui.dialogs.ListDialog
import ru.shiryaev.schedule.ui.views.TextField
import ru.shiryaev.schedule.utils.UtilsListData
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

    private var mSchedule = Schedule()

    private var mListWeek: List<Week> = listOf()
    private var mListTimeAndWeek: ArrayList<TimeAndWeek> = ArrayList()
    private var mListLessons: List<String> = listOf()
    private var mListTeachers: List<String> = listOf()
    private var mListAudits: List<String> = listOf()
    private var mListExams: List<String> = listOf()
    private var mListTime: List<Int> = listOf()

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mViewModel: AddScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем тему
        setTheme()

        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this, CustomFactory(AddScheduleViewModel())).get(AddScheduleViewModel::class.java)

        // Получение данных при краше активити или с intent
        if (savedInstanceState != null) { getData(savedInstanceState) }
        else {
            when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                ActivityClass.CREATE_SCHEDULE.name -> mSchedule.mDay = intent.getBundleExtra(UtilsKeys.BUNDLE.name)!!.getInt(UtilsKeys.POSITION_PAGE.name, 0)
                ActivityClass.EDIT_SCHEDULE.name -> mSchedule = intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getSerializable(UtilsTable.SCHEDULE) as Schedule
            }
        }

        initToolbar()

        setDataToView()

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@AddScheduleActivity
            schedule = mSchedule
        }

        // Устанавливаем слушатели на поля ввода
        with(binding.lessonField) {
            onTextChanged = { lesson ->
                mSchedule.mLesson = lesson
                mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
            }
            onCLickEndIcon = {
                ListDialog()
                        .setData(UtilsListData.getListDialog(mListLessons)) { positionItem ->
                            mSchedule.mLesson = mListLessons[positionItem]
                            setFieldText(mEditText, mSchedule.mLesson)
                        }
                        .show(supportFragmentManager, null)
            }
        }

        with(binding.teacherField) {
            onTextChanged = { teacher ->
                mSchedule.mTeacher = if (teacher != "") teacher else null
            }
            onCLickEndIcon = {
                ListDialog()
                        .setData(UtilsListData.getListDialog(mListTeachers)) { positionItem ->
                            mSchedule.mTeacher = mListTeachers[positionItem]
                            setFieldText(mEditText, mSchedule.mTeacher!!)
                        }
                        .show(supportFragmentManager, null)
            }
        }

        with(binding.auditField) {
            onTextChanged = { audit ->
                mSchedule.mAudit = if (audit != "") audit else null
            }
            onCLickEndIcon = {
                ListDialog()
                        .setData(UtilsListData.getListDialog(mListAudits)) { positionItem ->
                            mSchedule.mAudit = mListAudits[positionItem]
                            setFieldText(mEditText, mSchedule.mAudit!!)
                        }
                        .show(supportFragmentManager, null)
            }
        }

        with(binding.examField) {
            onTextChanged = { exam ->
                mSchedule.mExam = if (exam != "") exam else null
            }
            onCLickEndIcon = {
                ListDialog()
                        .setData(UtilsListData.getListDialog(mListExams)) { positionItem ->
                            mSchedule.mExam = mListExams[positionItem]
                            setFieldText(mEditText, mSchedule.mExam!!)
                        }
                        .show(supportFragmentManager, null)
            }
        }

        // Устанавливаем случашели на кнопки
        with(binding) {
            timeStartBtn.setOnClickListener(this@AddScheduleActivity)
            timeStartListBtn.setOnClickListener(this@AddScheduleActivity)
            timeEndBtn.setOnClickListener(this@AddScheduleActivity)
            timeEndListBtn.setOnClickListener(this@AddScheduleActivity)
            weekBtn.setOnClickListener(this@AddScheduleActivity)
            fab.setOnClickListener(this@AddScheduleActivity)
        }

        // Получаем списки для каждого поля из viewModel
        with(mViewModel) {
            // Получаем список недель
            getWeeks().observe(this@AddScheduleActivity) { listWeek ->
                mListWeek = listWeek
            }
            // Получаем список время+неделя (для текущего дня)
            getTimeStartByDay(mSchedule.mDay).observe(this@AddScheduleActivity) { listTimes ->
                mListTimeAndWeek = ArrayList(listTimes)
            }
            // Получаем список занятий
            getListLessons().observe(this@AddScheduleActivity) { listLessons ->
                mListLessons = setVisibleFieldEndIconBtn(binding.lessonField, listLessons)
            }
            // Получаем список занятий
            getListTeachers().observe(this@AddScheduleActivity) { listTeachers ->
                mListTeachers = setVisibleFieldEndIconBtn(binding.teacherField, listTeachers)
            }
            // Получаем список занятий
            getListAudits().observe(this@AddScheduleActivity) { listAudits ->
                mListAudits = setVisibleFieldEndIconBtn(binding.auditField, listAudits)
            }
            // Получаем список занятий
            getListExams().observe(this@AddScheduleActivity) { listExams ->
                mListExams = setVisibleFieldEndIconBtn(binding.examField, listExams)
            }
            // Получаем список времени
            getListTime().observe(this@AddScheduleActivity) { listTime ->
                mListTime = setVisibleBtn(binding.timeEndListBtn, listTime.toMutableList().apply {
                    remove(UtilsChecks.TIME_DISABLE)
                    sorted()
                })
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.time_start_btn -> {
                CallDialogs.callTimePicker(this@AddScheduleActivity, mSchedule.mWeek, mListTimeAndWeek) { hour, minute ->
                    mSchedule.mTimeStart = ("$hour" + UtilsConvert.convertToCorrectTime(minute)).toInt()
                    setSelectedTimeStart()
                }.show(supportFragmentManager, null)
            }
            R.id.time_start_list_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListTimeDialog(mListTime)) { positionItem ->
                        mSchedule.mTimeStart = mListTime[positionItem]
                        setSelectedTimeStart()
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.time_end_btn -> {
                CallDialogs.callTimePicker() { hour, minute ->
                    mSchedule.mTimeEnd = ("$hour" + UtilsConvert.convertToCorrectTime(minute)).toInt()
                    setSelectedTimeEnd()
                }.show(supportFragmentManager, null)
            }
            R.id.time_end_list_btn -> {
                ListDialog()
                        .setData(UtilsListData.getListTimeDialog(mListTime)) { positionItem ->
                            mSchedule.mTimeEnd = mListTime[positionItem]
                            setSelectedTimeEnd()
                        }
                        .show(supportFragmentManager, null)
            }
            R.id.week_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListNameWeekDialog(mListWeek, this)) { positionItem ->
                        if (positionItem == mListWeek.size) {
                            mSchedule.mWeek = ""
                        } else {
                            mSchedule.mWeek = mListWeek[positionItem].mName
                        }
                        setSelectedWeek()
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.fab -> {
                when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                    ActivityClass.CREATE_SCHEDULE.name -> mViewModel.insertSchedule(mSchedule)
                    ActivityClass.EDIT_SCHEDULE.name -> mViewModel.updateSchedule(mSchedule)
                }
                finishActivity()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putSerializable(UtilsTable.SCHEDULE, mSchedule)
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                ActivityClass.CREATE_SCHEDULE.name -> subtitle = this.resources.getString(R.string.create_lesson)
                ActivityClass.EDIT_SCHEDULE.name -> subtitle = this.resources.getString(R.string.edit_lesson)
            }
            title = this.resources.getStringArray(R.array.full_days_of_week)[mSchedule.mDay]
            setNavigationOnClickListener { finishActivity() }
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            mSchedule = getSerializable(UtilsTable.SCHEDULE) as Schedule
        }
    }

    private fun setDataToView() {
        if (mSchedule.mTimeStart != UtilsChecks.TIME_DISABLE) {
            binding.timeStartBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
        }
        if (mSchedule.mTimeEnd != UtilsChecks.TIME_DISABLE) {
            binding.timeEndBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeEnd)
        }
        binding.weekBtn.text = mSchedule.mWeek
    }

    private fun <T> setVisibleBtn(btn: AppCompatImageButton, newList: MutableList<T>): List<T> {
        val currentList = nonNullValues(newList.distinct())
        btn.isVisible = currentList.isNotEmpty()
        return currentList
    }

    private fun <T> setVisibleFieldEndIconBtn(field: TextField, newList: List<T>): List<T> {
        val currentList = nonNullValues(newList.distinct())
        field.endIconVisible(currentList.isNotEmpty())
        return currentList
    }

    private fun setFieldText(field: TextInputEditText, text: String) {
        with(field) {
            setText(text)
            setSelection(text.length)
        }
    }

    private fun setSelectedTimeStart() {
        binding.timeStartBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
        mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
    }

    private fun setSelectedTimeEnd() {
        binding.timeEndBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeEnd)
    }

    private fun setSelectedWeek() {
        binding.weekBtn.text = mSchedule.mWeek
    }

    private fun finishActivity() { finish() }

    private fun setTheme() {
        val listThemeMode = resources.getStringArray(R.array.theme_mode_entries)
        when (PreferenceManager.getDefaultSharedPreferences(this).getString(resources.getString(R.string.theme_key), listThemeMode.first())) {
            listThemeMode.first() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            listThemeMode[1] -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
            listThemeMode.last() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }
    }
}