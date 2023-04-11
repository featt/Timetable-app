package ru.shiryaev.schedule.ui.dialogs

import android.content.Context
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.schedule.common.controllers.ItemButtonDialogController
import ru.shiryaev.schedule.common.controllers.ItemFieldDialogController
import ru.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import ru.surfstudio.android.easyadapter.ItemList

class FieldDialog : CustomDialog() {

    private var mWeek: Week? = null
    private var mHint = ""
    private var mText = ""
    private var mOldWeek = ""
    private var mHeader: String? = null
    private var mButton: List<String>? = null
    private lateinit var mItemButtonController: ItemButtonDialogController
    private lateinit var mOnClickListener: OnClickButtonDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnClickListener = parentFragment as OnClickButtonDialogListener
    }

    fun setHeader(header: String): FieldDialog {
        mHeader = header
        return this
    }

    fun setHint(hint: String): FieldDialog {
        mHint = hint
        return this
    }

    fun setButton(button: List<String>): FieldDialog {
        mButton = button
        mItemButtonController = ItemButtonDialogController { textBtn ->
            clickBtn(textBtn)
        }
        return this
    }

    fun setData(week: Week? = null): FieldDialog {
        mWeek = week
        mText = mWeek?.mName ?: ""
        mOldWeek = mText
        val itemField = ItemFieldDialogController(mHint) { text ->
            mText = text
            mWeek?.mName = text
        }
        val mDialogList = ItemList.create().apply {
            addIf(mHeader != null, mHeader, ItemHeaderDialogController())
            addAll(listOf(mText), itemField)
            addAllIf(mButton != null, listOf(mButton), mItemButtonController)
        }
        setListToAdapter(mDialogList)
        return this
    }

    private fun clickBtn(text: String) {
        when(text) {
            mButton?.first() -> { dismiss() }
            mButton?.last() -> {
                mOnClickListener.onClick(mText, mOldWeek, mWeek, dialog = UtilsKeys.FIELD_DIALOG.name)
                dismiss()
            }
        }
    }
}