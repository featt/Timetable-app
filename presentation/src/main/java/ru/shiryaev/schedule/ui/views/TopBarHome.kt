package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.views.utils.showTabs


class TopBarHome @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var onChangeCurrentItem: ((Int) -> Unit)? = null
    var onChangeHeight: ((Int) -> Unit)? = null
    var onShowCalendar: (() -> Unit)? = null

    private var showTabs = false

    private val countPage: Int = context.resources.getStringArray(R.array.days_of_week).size

    private lateinit var mDay: MaterialTextView
    private lateinit var mShowTabLayoutBtn: AppCompatImageButton
    private lateinit var mCalendarBtn: AppCompatImageButton
    private lateinit var mTabLayout: CustomTabLayout

    init {
        inflate(context, R.layout.custom_topbar_home, this)

        initViews()

        initTabLayout()

        setClickListener()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        onChangeHeight?.invoke(h)
    }

    fun setSelectedTab(position: Int) {
        mTabLayout.setSelectedTab(position)
    }

    private fun initViews() {
        mDay = findViewById(R.id.top_bar_title)
        mShowTabLayoutBtn = findViewById(R.id.top_bar_show_tabs_btn)
        mCalendarBtn = findViewById(R.id.show_calendar)
        mTabLayout = findViewById(R.id.custom_tab_layout)
    }

    private fun initTabLayout() {
        with(mTabLayout) {
            setCountTab(countPage)
            setSelectedPage = { selectedTab ->
                onChangeCurrentItem?.invoke(selectedTab)
            }
            getSelectedTab = { selectedTab ->
                setTextTitle(selectedTab)
            }
        }
    }

    private fun setTextTitle(position: Int) {
        val arrayTextTitle = context.resources.getStringArray(R.array.full_days_of_week)
        mDay.text = arrayTextTitle[position]
    }

    private fun setClickListener() {
        mShowTabLayoutBtn.setOnClickListener(this)
        mDay.setOnClickListener(this)
        mCalendarBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.top_bar_show_tabs_btn, R.id.top_bar_title -> {
                showTabs = showTabs(context, mShowTabLayoutBtn, !showTabs)
                mTabLayout.isVisible = showTabs
            }
            R.id.show_calendar -> {
                onShowCalendar?.invoke()
            }
        }
    }
}