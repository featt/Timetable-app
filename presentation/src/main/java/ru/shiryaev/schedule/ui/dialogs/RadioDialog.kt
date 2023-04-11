package ru.shiryaev.schedule.ui.dialogs

import ru.shiryaev.data.common.models.ItemDialog
import ru.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import ru.shiryaev.schedule.common.controllers.ItemRadioDialogController
import ru.surfstudio.android.easyadapter.ItemList

class RadioDialog : CustomDialog() {

    private var mHeader: String? = null

    fun setHeader(header: String): RadioDialog {
        mHeader = header
        return this
    }

    fun setData(listItem: List<ItemDialog>, onCLickItemDialog: (Int) -> Unit): RadioDialog {
        val itemRadio = ItemRadioDialogController { positionItem ->
            onCLickItemDialog.invoke(if(mHeader != null) positionItem - 1 else positionItem)
            dismiss()
        }
        val mDialogList = ItemList.create().apply {
            addIf(mHeader != null, mHeader, ItemHeaderDialogController())
            addAll(listItem, itemRadio)
        }
        setListToAdapter(mDialogList)
        return this
    }
}