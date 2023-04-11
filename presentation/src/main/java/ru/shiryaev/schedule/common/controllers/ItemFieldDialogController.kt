package ru.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.views.TextField
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemFieldDialogController(
        private val mHint: String = "",
        private val setTextChanged: (String) -> Unit
) : BindableItemController<String, ItemFieldDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: String) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<String>(parent, R.layout.custom_field_dialog) {

        private val mField: TextField = itemView.findViewById(R.id.field)

        override fun bind(data: String) {
            with (mField) {
                with (mEditText) {
                    hint = mHint
                    setText(data)
                }
                onTextChanged = { text ->
                    setTextChanged.invoke(text)
                }
            }
        }
    }
}