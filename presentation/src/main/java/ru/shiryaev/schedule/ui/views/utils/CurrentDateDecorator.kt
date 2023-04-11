package ru.shiryaev.schedule.ui.views.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import ru.shiryaev.schedule.R

class CurrentDateDecorator(
        context: Activity,
        private val mDay: CalendarDay
) : DayViewDecorator {

    private val mDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.today_decorator)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == mDay
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(mDrawable!!)
    }
}