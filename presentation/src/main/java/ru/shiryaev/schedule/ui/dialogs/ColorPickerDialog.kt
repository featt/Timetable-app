package ru.shiryaev.schedule.ui.dialogs

import android.content.Context
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.schedule.common.controllers.ItemButtonDialogController
import ru.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import ru.shiryaev.schedule.common.controllers.ItemListColorPickController
import ru.shiryaev.schedule.ui.fragments.WeeksSettingsFragment
import ru.surfstudio.android.easyadapter.ItemList

class ColorPickerDialog : CustomDialog() {

    private var mHeader: String? = null
    private var mButton: List<String>? = null
    private var mColor = ""
    private lateinit var mItemButtonController: ItemButtonDialogController
    private lateinit var mOnClickListener: OnClickButtonDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnClickListener = if (parentFragment is WeeksSettingsFragment) {
            parentFragment as OnClickButtonDialogListener
        } else {
            context as OnClickButtonDialogListener
        }
    }

    fun setButton(button: List<String>): ColorPickerDialog {
        mButton = button
        mItemButtonController = ItemButtonDialogController { textBtn ->
            clickBtn(textBtn)
        }
        return this
    }

    fun setHeader(header: String): ColorPickerDialog {
        mHeader = header
        return this
    }

    fun setData(): ColorPickerDialog {
        val colorPick = ItemListColorPickController { color -> mColor = color.toString() }
        val mDialogList = ItemList.create().apply {
            addIf(mHeader != null, mHeader, ItemHeaderDialogController())
            addIf(true,  0, colorPick)
            addAllIf(mButton != null, listOf(mButton), mItemButtonController)
        }
        setListToAdapter(mDialogList)
        return this
    }

    private fun clickBtn(text: String) {
        when(text) {
            mButton?.first() -> {
                mColor = ""
                mOnClickListener.onClick(text = mColor, dialog = UtilsKeys.COLOR_PICK_DIALOG.name)
                dismiss() }
            mButton?.get(1) -> { dismiss() }
            mButton?.last() -> {
                mOnClickListener.onClick(text = mColor, dialog = UtilsKeys.COLOR_PICK_DIALOG.name)
                dismiss()
            }
        }
    }
}