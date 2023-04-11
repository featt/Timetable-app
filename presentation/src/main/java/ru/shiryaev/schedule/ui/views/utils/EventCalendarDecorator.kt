package ru.shiryaev.schedule.ui.views.utils

import android.app.Activity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import ru.shiryaev.domain.models.Note

class EventCalendarDecorator(
        context: Activity,
        private val month: Array<String>,
        private var listNote: List<Note>
) : DayViewDecorator {

    private val mDotSpan: DotSpan = DotSpan(6F)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        var flag = false
        var date: String = if (day.day < 10) "0${day.day}, " else "${day.day}, "
        date += month[day.month - 1]
        date += ", ${day.year}"
        for (i in listNote) {
            if (i.mDeadline == date) {
                flag = true
            }
        }
        return flag
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(mDotSpan)
    }
}