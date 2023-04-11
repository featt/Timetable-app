package ru.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.shiryaev.data.common.models.ItemDialog
import ru.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemRadioDialogController(
    private val onClickItemListener: (positionItem: Int) -> Unit
) : BindableItemController<ItemDialog, ItemRadioDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: ItemDialog) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ItemDialog>(parent, R.layout.custom_radio_dialog) {

        private val mItemBtn: MaterialCardView = itemView.findViewById(R.id.layout_item)
        private val mRadioBtn: MaterialRadioButton = itemView.findViewById(R.id.radio_item_btn)

        override fun bind(data: ItemDialog) {
            mRadioBtn.apply {
                text = data.text
                isChecked = data.isChecked
            }
            mItemBtn.setOnClickListener { onClickItemListener.invoke(adapterPosition) }
        }
    }
}