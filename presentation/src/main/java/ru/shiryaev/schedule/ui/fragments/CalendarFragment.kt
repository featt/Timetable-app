package ru.shiryaev.schedule.ui.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import ru.shiryaev.data.common.CustomFactory
import ru.shiryaev.data.viewmodels.NoteViewModel
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.FrHomeCalendarBinding
import ru.shiryaev.schedule.ui.views.utils.CurrentDateDecorator
import ru.shiryaev.schedule.ui.views.utils.EventCalendarDecorator
import android.view.ViewTreeObserver.OnGlobalLayoutListener as OnGlobalLayoutListener1

class CalendarFragment : Fragment() {

    private var _binding: FrHomeCalendarBinding? = null
    private val binding get() = _binding!!
    private var mTopBarHeight: Int = 0

    private lateinit var mNavController: NavController
    private lateinit var mViewModel: NoteViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this, CustomFactory(NoteViewModel())).get(NoteViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FrHomeCalendarBinding.inflate(inflater, container, false)

        mNavController = NavHostFragment.findNavController(this)

        initView()

        with(mViewModel) {
            // Получаем список заметок для Event
            getNotes().observe(viewLifecycleOwner) { notes ->
                binding.calendar.addDecorators(EventCalendarDecorator(requireActivity(), requireActivity().resources.getStringArray(R.array.month), notes))
            }
            // Проверяем progress
            getIsLoading().observe(viewLifecycleOwner) { value ->
                binding.noteBottomSheet.setLoading(value)
            }
            // Проверяем список на пустоту
            getIsErrorVisible().observe(viewLifecycleOwner) { value ->
                binding.noteBottomSheet.setLoading(false)
                binding.noteBottomSheet.setInfoError(value)
            }
        }

        binding.topBarCalendar.getHeight = { heightTopBar ->
            mTopBarHeight = heightTopBar
            val params = CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin =  heightTopBar
            }
            binding.calendar.layoutParams = params
        }

        binding.noteBottomSheet.onChangeState = { state ->
            when (state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    changeCalendarMode(CalendarMode.MONTHS)
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    changeCalendarMode(CalendarMode.WEEKS)
                }
            }
        }

        with(binding.calendar) {
            addDecorator(CurrentDateDecorator(requireActivity(), CalendarDay.today()))
            setOnMonthChangedListener { widget, date ->
                setDateToTopBar(date.year, date.month - 1)
            }
            setOnDateChangedListener { widget, date, selected ->
                getNotes(date.year, date.month - 1, date.day)
            }
        }

        binding.topBarCalendar.onShowSchedule = { mNavController.popBackStack() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем высоту Fragment
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener1 {
            override fun onGlobalLayout() {
                if (binding.root.height > 0 && binding.root.width > 0) {
                    binding.noteBottomSheet.apply {
                        setPeekHeight(binding.root.height - (mTopBarHeight + binding.calendar.height))
                        setExpandedHeight(binding.root.height - (mTopBarHeight + resources.getDimension(R.dimen.default_dimen).toInt() + 2 * (binding.root.width / 7)))
                    }
                    binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        with(binding.calendar) {
            topbarVisible = false
            selectedDate = CalendarDay.today()
            setDateToTopBar(currentDate.year, currentDate.month - 1)
            getNotes(selectedDate!!.year, selectedDate!!.month - 1, selectedDate!!.day)
        }
    }

    private fun getNotes(year: Int, month: Int, day: Int) {
        var selectedDate: String = if (day < 10) "0$day, " else "$day, "
        selectedDate += requireContext().resources.getStringArray(R.array.month)[month]
        selectedDate += ", $year"
        mViewModel.getNotesByDate(selectedDate).observe(viewLifecycleOwner) { notes ->
            mViewModel.setIsErrorVisible(binding.noteBottomSheet.setNoteList(notes))
        }
    }

    private fun setDateToTopBar(year: Int, month: Int) {
        binding.topBarCalendar.apply {
            setYear(year)
            setMonth(month)
        }
    }

    private fun changeCalendarMode(mode: CalendarMode) {
        binding.calendar.newState()
                .setShowWeekDays(true)
                .setCalendarDisplayMode(mode)
                .commit()
    }
}