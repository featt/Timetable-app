package ru.shiryaev.schedule.common.controllers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableRow
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.ui.views.CustomButtonDialog
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemButtonDialogController(
        private val onClickListener: (String) -> Unit
) : BindableItemController<List<String>, ItemButtonDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: List<String>) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<List<String>>(parent, R.layout.custom_layout_button_dialog) {

        private var mData = listOf<String>()

        private val mLayoutBtn: TableRow = itemView.findViewById(R.id.layout_button)

        override fun bind(data: List<String>) {
            mData = data
            data.forEachIndexed { index, textBtn ->
                if (index > 0) {
                    mLayoutBtn.addView(getDivider())
                }
                mLayoutBtn.addView(
                        CustomButtonDialog(itemView.context)
                                .setText(textBtn) { text -> onClickListener.invoke(text) }
                )
            }
        }

        @SuppressLint("InflateParams")
        private fun getDivider() = LayoutInflater.from(itemView.context).inflate(R.layout.divider_button_dialog, null).apply {
            layoutParams = TableRow.LayoutParams(
                    1,
                    TableRow.LayoutParams.MATCH_PARENT
            ).apply {
                topMargin = itemView.context.resources.getDimension(R.dimen.margin_vertical).toInt()
                bottomMargin = itemView.context.resources.getDimension(R.dimen.margin_vertical).toInt()
            }
        }
    }
}