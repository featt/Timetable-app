package ru.shiryaev.schedule.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.UtilsChecks
import ru.shiryaev.domain.utils.UtilsConvert
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.fragments.EditScheduleFragment
import ru.shiryaev.schedule.ui.fragments.ScheduleFragment

@SuppressLint("ViewConstructor")
class CustomItemSchedule(
        context: Context,
        private val listWeek: List<Week>,
        private val screen: String
) : TableRow(context) {

    var onClickListener: ((Schedule) -> Unit)? = null
    var onLongClickListener: ((Schedule) -> Unit)? = null

    private val mCardLayout: MaterialCardView
    private val lessonTv: AppCompatTextView
    private val teacherTv: AppCompatTextView
    private val auditTv: AppCompatTextView
    private val weekTv: AppCompatTextView
    private val examTv: AppCompatTextView
    private val mIndicatorCardWeek: MaterialCardView
    private val mIndicatorWeek: View
    private val timeEnd: AppCompatTextView
    private val mTimeEndContainer: LinearLayoutCompat
    private val mDivider: MaterialCardView

    init {
        inflate(context, R.layout.custom_card_schedule, this)

        mCardLayout = this.findViewById(R.id.item_card)
        lessonTv = this.findViewById(R.id.lesson_schedule_tv)
        teacherTv = this.findViewById(R.id.teacher_schedule_tv)
        auditTv = this.findViewById(R.id.audit_schedule_tv)
        weekTv = this.findViewById(R.id.week_schedule_tv)
        examTv = this.findViewById(R.id.exam_schedule_tv)
        mIndicatorCardWeek = this.findViewById(R.id.indicator_card_week)
        mIndicatorWeek = this.findViewById(R.id.indicator_week)
        timeEnd = this.findViewById(R.id.time_end_schedule_tv)
        mTimeEndContainer = this.findViewById(R.id.time_end_container)
        mDivider = this.findViewById(R.id.divider)
    }

    fun setItemSchedule(data: Schedule) {
        with(data) {
            lessonTv.text = mLesson
            teacherTv.text = mTeacher
            auditTv.text = mAudit
            weekTv.text = mWeek
            examTv.text = mExam
            timeEnd.text = UtilsConvert.convertTimeIntToString(mTimeEnd)
        }

        teacherTv.isVisible = data.mTeacher != null
        auditTv.isVisible = data.mAudit != null
        examTv.isVisible = data.mExam != null
        mTimeEndContainer.isVisible = data.mTimeEnd != UtilsChecks.TIME_DISABLE

        mIndicatorCardWeek.isVisible = data.mWeek != "" || data.mTimeEnd != UtilsChecks.TIME_DISABLE
        mDivider.isVisible = data.mWeek != ""

        if (data.mWeek == "") {
            mIndicatorWeek.setBackgroundColor(Color.TRANSPARENT)
            mIndicatorCardWeek.setCardBackgroundColor(Color.TRANSPARENT)
        } else {
            listWeek.forEach { week ->
                if (data.mWeek == week.mName) {
                    if (week.mColor == "") {
                        val typedValue = TypedValue()
                        val typedArray: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
                        val color = typedArray.getColor(0, 0)
                        typedArray.recycle()
                        mIndicatorCardWeek.background.setTint(color)
                        mIndicatorWeek.setBackgroundColor(color)
                    } else {
                        mIndicatorCardWeek.background.setTint(context.resources.getIntArray(R.array.color_pick)[week.mColor.toInt()])
                        mIndicatorWeek.setBackgroundColor(context.resources.getIntArray(R.array.color_pick)[week.mColor.toInt()])
                    }
                    mIndicatorCardWeek.isVisible = true
                }
            }
        }

        mCardLayout.setOnClickListener { onClickListener?.invoke(data) }

        when(screen) {
            EditScheduleFragment.TAG -> {
                mCardLayout.setOnLongClickListener {
                    onLongClickListener?.invoke(data)
                    true
                }
            }
            ScheduleFragment.TAG -> {
                mIndicatorWeek.isVisible = false
                mIndicatorCardWeek.isVisible = false
            }
        }
    }
}