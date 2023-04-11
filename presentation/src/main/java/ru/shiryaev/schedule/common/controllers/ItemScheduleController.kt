package ru.shiryaev.schedule.common.controllers

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.UtilsConvert
import ru.shiryaev.domain.utils.sortWeeks
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.fragments.EditScheduleFragment
import ru.shiryaev.schedule.ui.fragments.HomeFragment
import ru.shiryaev.schedule.ui.views.CustomItemSchedule
import ru.shiryaev.schedule.ui.views.CustomTimeLine
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemScheduleController(
        private val listWeek: List<Week>,
        private val screen: String,
        private val onClickListener: (Schedule) -> Unit
) : BindableItemController<ArrayList<Schedule>, ItemScheduleController.Holder>() {

    var onLongClickListener: ((Schedule) -> Unit)? = null

    private var countItem: Int = 0

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: ArrayList<Schedule>) = data.hashCode().toString()

    fun setCountItem(count: Int) {
        this.countItem = count
    }

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ArrayList<Schedule>>(parent, R.layout.item_schedule) {

        private val mTimeContainer = itemView.findViewById<CustomTimeLine>(R.id.item_time_container)
        private val mContainer = itemView.findViewById<LinearLayoutCompat>(R.id.item_container)
        private val mLineTop = itemView.findViewById<FrameLayout>(R.id.line_top)
        private val mLineBottom = itemView.findViewById<FrameLayout>(R.id.line_bottom)

        override fun bind(data: ArrayList<Schedule>) {
            mTimeContainer.apply {
                setTimeStart(UtilsConvert.convertTimeIntToString(data.first().mTimeStart))
                setTimeEnd(UtilsConvert.convertTimeIntToString(data.first().mTimeEnd))
                setTimeEndVisible(screen)
            }

            when {
                countItem == 1 -> {
                    mTimeContainer.apply {
                        mLineTop.visibility = View.INVISIBLE
                        mLineBottom.visibility = View.INVISIBLE
                    }
                }
                adapterPosition == 0 -> {
                    mTimeContainer.apply {
                        mLineTop.visibility = View.INVISIBLE
                        mLineBottom.visibility = View.VISIBLE
                    }
                }
                adapterPosition == countItem - 1 -> {
                    mTimeContainer.apply {
                        mLineTop.visibility = View.VISIBLE
                        mLineBottom.visibility = View.INVISIBLE
                    }
                }
                else -> {
                    mTimeContainer.apply {
                        mLineTop.visibility = View.VISIBLE
                        mLineBottom.visibility = View.VISIBLE
                    }
                }
            }

            val newListSchedule = if (data.size == 1 && data.first().mWeek == "") {
                data
            } else {
                sortWeeks(data, listWeek)
            }

            with(mContainer) {
                removeAllViews()
                for (item in newListSchedule) {
                    addView(CustomItemSchedule(context, listWeek, screen).apply {
                        onClickListener = { schedule -> this@ItemScheduleController.onClickListener.invoke(schedule) }
                        onLongClickListener = { schedule -> this@ItemScheduleController.onLongClickListener?.invoke(schedule) }
                        setItemSchedule(item)
                    })
                }
            }
        }
    }
}