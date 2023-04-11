package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.fragments.EditScheduleFragment
import ru.shiryaev.schedule.ui.fragments.HomeFragment
import ru.shiryaev.schedule.ui.fragments.ScheduleFragment

class CustomTimeLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mTimeStart: AppCompatTextView
    private val mTimeEnd: AppCompatTextView
    private val mTimeEndContainer: FrameLayout

    init {
        inflate(context, R.layout.custom_timeline, this)

        mTimeStart = findViewById(R.id.item_time_start_tv)
        mTimeEnd = findViewById(R.id.item_time_end_tv)
        mTimeEndContainer = findViewById(R.id.item_time_end_container)
    }

    fun setTimeStart(text: String) { mTimeStart.text = text }

    fun setTimeEnd(text: String) {
        mTimeEnd.text = text
    }

    fun setTimeEndVisible(screen: String) {
        when (screen) {
            ScheduleFragment.TAG -> {
                mTimeEndContainer.isVisible = mTimeEnd.text != ""
            }
            EditScheduleFragment.TAG -> { mTimeEndContainer.isVisible = false }
        }
    }
}