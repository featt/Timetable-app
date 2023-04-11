package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import ru.shiryaev.schedule.R
import ru.shiryaev.data.utils.fetchColorText

class DayTab(context: Context) : CustomTab(context) {

    private val textDayTab: TextView = TextView(context)

    init {
        this.findViewById<LinearLayoutCompat>(R.id.tab_root_layout).addView(textDayTab)
    }

    override fun getPosition(position: Int) {
        setText(context.resources.getStringArray(R.array.days_of_week)[position])
    }

    override fun getSelectedColor() {
        val invertedRed = 255 - Color.red(textDayTab.textColors.defaultColor)
        val invertedGreen = 255 - Color.green(textDayTab.textColors.defaultColor)
        val invertedBlue = 255 - Color.blue(textDayTab.textColors.defaultColor)
        textDayTab.setTextColor(Color.rgb(invertedRed, invertedGreen, invertedBlue))
    }

    override fun getUnselectedColor() {
        textDayTab.setTextColor(fetchColorText(context))
    }

    private fun setText(text: String) { textDayTab.text = text }
}