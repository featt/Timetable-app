package ru.shiryaev.schedule.common.controllers

import android.content.res.TypedArray
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.shiryaev.domain.models.Week
import ru.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemWeekController : BindableItemController<Week, ItemWeekController.Holder>() {

    var onClickLayout: ((Week) -> Unit)? = null
    var onLongClickLayout: ((Week) -> Unit)? = null
    var onCLickIndicatorBtn: ((Week) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Week) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Week>(parent, R.layout.item_week) {

        private val mLayout: MaterialCardView = itemView.findViewById(R.id.item_layout)
        private val mNameWeek: MaterialTextView = itemView.findViewById(R.id.item_tv)
        private val mIndicatorWeek: AppCompatImageButton = itemView.findViewById(R.id.item_ib)

        override fun bind(data: Week) {
            mNameWeek.text = data.mName

            mLayout.apply {
                setOnClickListener {
                    onClickLayout?.invoke(data)
                }
                setOnLongClickListener {
                    onLongClickLayout?.invoke(data)
                    true
                }
            }

            mIndicatorWeek.apply {
                if (data.mColor == "") {
                    val typedValue = TypedValue()
                    val typedArray: TypedArray = itemView.context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
                    val color = typedArray.getColor(0, 0)
                    typedArray.recycle()
                    setColorFilter(color)
                } else {
                    setColorFilter(itemView.context.resources.getIntArray(R.array.color_pick)[data.mColor.toInt()])
                }
                setOnClickListener {
                    onCLickIndicatorBtn?.invoke(data)
                }
            }
        }
    }
}