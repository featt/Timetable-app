package ru.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import ru.shiryaev.domain.models.ColorPick
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.views.SquareImageView
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemColorPickerController(
        var onClickColor: (Int) -> Unit
) : BindableItemController<ColorPick, ItemColorPickerController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: ColorPick) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ColorPick>(parent, R.layout.item_color) {

        private val mColorIm: SquareImageView = itemView.findViewById(R.id.item_im)

        override fun bind(data: ColorPick) {
            if (data.mIsSelected) {
                mColorIm.setImageResource(R.drawable.ic_checkbox_unchecked)
            } else {
                mColorIm.setImageResource(R.drawable.ic_checkbox_blank_circle)
            }
            mColorIm.setColorFilter(data.mColor)
            mColorIm.setOnClickListener {
                onClickColor.invoke(adapterPosition)
            }
        }
    }
}