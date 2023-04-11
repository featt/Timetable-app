package ru.shiryaev.schedule.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import ru.shiryaev.data.utils.fetchColorPrimary
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.tools.adapters.CustomTabAdapter
import ru.shiryaev.schedule.tools.interfaces.OnClickCustomTabListener

abstract class CustomTab @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var positionItem = 0
    private var selectedItem = 0
    private val cardTab: MaterialCardView

    private lateinit var onClickTab: OnClickCustomTabListener

    init {
        inflate(context, R.layout.custom_tab, this)

        cardTab = this.findViewById(R.id.tab_card)

        cardTab.setOnClickListener { onClickTab.onClickCustomTab(positionItem) }

        this.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
        )
    }

    abstract fun getPosition(position: Int)
    abstract fun getSelectedColor()
    abstract fun getUnselectedColor()

    fun setPosition(position: Int) {
        this.positionItem = position
        getPosition(position)
    }

    fun setSelectedItem(selected: Int) {
        this.selectedItem = selected
        if (this.selectedItem == positionItem) {
            setSelectedColor()
        } else {
            setUnselectedColor()
        }
    }

    fun setAdapter(adapter: CustomTabAdapter) {
        onClickTab = adapter
    }

    @SuppressLint("ResourceType")
    private fun setSelectedColor() {
        cardTab.setCardBackgroundColor(fetchColorPrimary(context))
        getSelectedColor()
    }

    @SuppressLint("ResourceType")
    private fun setUnselectedColor() {
        cardTab.setCardBackgroundColor(Color.TRANSPARENT)
        getUnselectedColor()
    }
}