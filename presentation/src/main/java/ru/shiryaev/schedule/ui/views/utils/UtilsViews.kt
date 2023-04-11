package ru.shiryaev.schedule.ui.views.utils

import android.content.Context
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import ru.shiryaev.schedule.R

fun showTabs(context: Context, iconBtn: ImageButton, show: Boolean) : Boolean {
    if (show) {
        iconBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_up))
    } else {
        iconBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_down))
    }
    return show
}