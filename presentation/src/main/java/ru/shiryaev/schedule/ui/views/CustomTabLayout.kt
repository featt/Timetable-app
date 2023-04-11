package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.tools.adapters.CustomTabAdapter
import ru.shiryaev.schedule.tools.interfaces.OnClickCustomTabListener

class CustomTabLayout(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs), OnClickCustomTabListener {

    var setSelectedPage: ((selectedTab: Int) -> Unit)? = null
    var getSelectedTab: ((selectedTab: Int) -> Unit)? = null

    private var selectedItem = 0
    private var countTab = 0
    private lateinit var adapter: CustomTabAdapter

    init {
        orientation = HORIZONTAL

        this.layoutParams = LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    fun setSelectedTab(positionPage: Int) {
        adapter.setSelectedTab(positionPage)
        getSelectedTab?.invoke(positionPage)
    }

    fun setCountTab(countTab: Int) {
        this.countTab = countTab
        initAdapter()
    }

    private fun initAdapter() {
        adapter = CustomTabAdapter(context, this, selectedItem).apply {
            setCountTab(countTab)
            setLayout(this@CustomTabLayout)
        }
    }

    override fun onClickCustomTab(positionTab: Int) {
        this.selectedItem = positionTab
        setSelectedPage?.invoke(positionTab)
        getSelectedTab?.invoke(positionTab)
    }
}