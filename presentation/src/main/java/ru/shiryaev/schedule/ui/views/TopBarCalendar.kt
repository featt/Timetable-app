package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.textview.MaterialTextView
import ru.shiryaev.schedule.R

class TopBarCalendar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var getHeight: ((Int) -> Unit)? = null
    var onShowSchedule: (() -> Unit)? = null

    private var mArrayMonth = arrayOf<String>()

    private lateinit var mYear: MaterialTextView
    private lateinit var mMonth: MaterialTextView
    private lateinit var mScheduleBtn: AppCompatImageButton

    init {
        inflate(context, R.layout.custom_topbar_calendar, this)

        mArrayMonth = context.resources.getStringArray(R.array.month_full)

        initViews()

        setClickListener()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        getHeight?.invoke(h)
    }

    fun setYear(year: Int) {
        mYear.text = year.toString()
    }

    fun setMonth(month: Int) {
        mMonth.text = mArrayMonth[month]
    }

    private fun initViews() {
        mYear = findViewById(R.id.top_bar_year)
        mMonth = findViewById(R.id.top_bar_month)
        mScheduleBtn = findViewById(R.id.show_schedule)
    }

    private fun setClickListener() {
        mScheduleBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.show_schedule -> {
                onShowSchedule?.invoke()
            }
        }
    }
}