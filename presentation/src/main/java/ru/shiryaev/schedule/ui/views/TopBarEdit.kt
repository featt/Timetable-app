package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.material.card.MaterialCardView
import ru.shiryaev.schedule.R

class TopBarEdit @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onChangeCurrentItem: ((Int) -> Unit)? = null
    var onChangeHeight: ((Int) -> Unit)? = null

    private val countPage: Int = context.resources.getStringArray(R.array.days_of_week).size - 1

    private lateinit var mCardLayout: MaterialCardView
    private lateinit var mTabLayout: CustomTabLayout

    init {
        inflate(context, R.layout.custom_topbar_edit, this)

        initViews()

        initTabLayout()

        onChangeHeight?.invoke(mCardLayout.height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        onChangeHeight?.invoke(h)
    }

    fun setSelectedTab(position: Int) {
        mTabLayout.setSelectedTab(position)
    }

    private fun initViews() {
        mCardLayout = findViewById(R.id.top_bar_layout)
        mTabLayout = findViewById(R.id.custom_tab_layout)
    }

    private fun initTabLayout() {
        with(mTabLayout) {
            setCountTab(countPage)
            setSelectedPage = { selectedTab ->
                onChangeCurrentItem?.invoke(selectedTab)
            }
        }
    }
}