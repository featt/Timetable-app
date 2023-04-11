package ru.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shiryaev.data.common.CustomFactory
import ru.shiryaev.data.viewmodels.PageScheduleViewModel
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.Week
import ru.shiryaev.schedule.databinding.FrPageScheduleBinding
import ru.shiryaev.schedule.common.controllers.ItemScheduleController
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.domain.utils.UtilsTable
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.common.navigation.ActivityClass
import ru.shiryaev.schedule.common.navigation.ActivityRouteCreateNote
import ru.shiryaev.schedule.common.navigation.NavigationActivity
import ru.shiryaev.schedule.ui.dialogs.ListDialog
import ru.shiryaev.schedule.ui.views.utils.SpaceFirstItemDecoration
import ru.shiryaev.schedule.utils.UtilsListData
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class PageScheduleFragment : Fragment() {

    private var mPositionPage = 0
    private var mListWeek: List<Week> = listOf()

    private var mDecorator = SpaceFirstItemDecoration()

    private var _binding: FrPageScheduleBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()

    private lateinit var mViewModel: PageScheduleViewModel
    private lateinit var mScreen: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this, CustomFactory(PageScheduleViewModel())).get(PageScheduleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPositionPage = it.getInt(UtilsKeys.POSITION_PAGE.name)
            mScreen = it.getString(UtilsKeys.SCREEN.name).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrPageScheduleBinding.inflate(inflater, container, false)

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@PageScheduleFragment
        }

        mViewModel.getWeeks().observe(viewLifecycleOwner) { listWeek ->
            mListWeek = listWeek
        }

        mViewModel.getCorrectListSchedule().observe(viewLifecycleOwner) { schedules ->
            val mItemSchedule = ItemScheduleController(mListWeek, mScreen) { schedule ->
                // TODO: Детальный показ занятия при нажатии на карточку
            }
            if (parentFragment is EditScheduleFragment) {
                mItemSchedule.onLongClickListener = { schedule -> showListDialog(schedule) }
            }
            val listSchedule = ItemList.create().apply {
                addAll(schedules, mItemSchedule.apply { setCountItem(schedules.size) })
            }
            mEasyAdapter.setItems(listSchedule)
            mViewModel.setIsErrorVisible(mEasyAdapter.itemCount == 0)
        }

        initRecyclerView()

        when (parentFragment) {
            is ScheduleFragment -> {
                (parentFragment as ScheduleFragment).getViewModel().getHeightTopBar().observe(viewLifecycleOwner) { height ->
                    setHeightDecoration(height)
                }
                PreferenceManager.getDefaultSharedPreferences(requireContext()).getString(requireContext().resources.getString(R.string.current_week_key), "")?.let {
                    mViewModel.getSchedules(mPositionPage, it).observe(viewLifecycleOwner) { listSchedules ->
                        mViewModel.setListSchedule(listSchedules)
                    }
                }
            }
            is EditScheduleFragment -> {
                (parentFragment as EditScheduleFragment).getViewModel().getHeightTopBar().observe(viewLifecycleOwner) { height ->
                    setHeightDecoration(height)
                }
                mViewModel.getSchedules(mPositionPage).observe(viewLifecycleOwner, { listSchedules ->
                    mViewModel.setListSchedule(listSchedules)
                })
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = mEasyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setHeightDecoration(height: Int) {
        binding.recyclerView.apply {
            removeItemDecoration(mDecorator)
            mDecorator = SpaceFirstItemDecoration(height)
            addItemDecoration(mDecorator)
        }
    }

    private fun actionSchedule(schedule: Schedule, action: Int) {
        val arrayAction = requireContext().resources.getStringArray(R.array.list_dialog)
        when(arrayAction[action]) {
            arrayAction.first() -> {
                val bundle = Bundle().apply {
                    putString(UtilsKeys.REQUEST_CODE.name, ActivityClass.EDIT_SCHEDULE.name)
                    putInt(UtilsKeys.POSITION_PAGE.name, mPositionPage)
                    putSerializable(UtilsTable.SCHEDULE, schedule)
                }
                NavigationActivity(requireContext()).navigate(ActivityRouteCreateNote(ActivityClass.EDIT_SCHEDULE), bundle)
            }
            // Удаление занятия
            arrayAction.last() -> mViewModel.deleteSchedule(schedule)
        }
    }

    private fun showListDialog(schedule: Schedule) {
        ListDialog()
            .setData(UtilsListData.getListScheduleDialog(requireContext())) { positionItem ->
                actionSchedule(schedule, positionItem)
            }
            .show(childFragmentManager, null)
    }
}