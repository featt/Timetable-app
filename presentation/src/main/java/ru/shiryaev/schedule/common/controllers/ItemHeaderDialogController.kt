package ru.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import com.google.android.material.textview.MaterialTextView
import ru.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemHeaderDialogController : BindableItemController<String, ItemHeaderDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: String) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<String>(parent, R.layout.custom_header_dialog) {

        private val headerTv: MaterialTextView = itemView.findViewById(R.id.header_tv)

        override fun bind(data: String) {
            headerTv.text = data
        }
    }
}