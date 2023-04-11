package ru.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import ru.shiryaev.schedule.R
import ru.shiryaev.data.common.models.ItemDialog
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemListDialogController(
    private val onCLickListener: (position: Int) -> Unit
) : BindableItemController<ItemDialog, ItemListDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: ItemDialog) = data.text.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ItemDialog>(parent, R.layout.custom_item_dialog) {

        private lateinit var data: ItemDialog
        private val textTv: AppCompatTextView = itemView.findViewById(R.id.text_item_dialog)
        private val iconIv: AppCompatImageView = itemView.findViewById(R.id.icon_item_dialog)

        init {
            itemView.setOnClickListener {
                onCLickListener.invoke(adapterPosition) }
        }

        override fun bind(data: ItemDialog) {
            this.data = data
            textTv.text = data.text
            if (data.icon != null) {
                iconIv.apply {
                    setImageResource(data.icon!!)
                    isVisible = true
                }
            } else {
                iconIv.isVisible = false
            }
        }
    }
}