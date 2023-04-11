package ru.shiryaev.schedule.ui.dialogs

import ru.shiryaev.data.common.models.ItemDialog
import ru.shiryaev.schedule.common.controllers.ItemListDialogController
import ru.surfstudio.android.easyadapter.ItemList

class ListDialog : CustomDialog() {

    fun setData(listItem: List<ItemDialog>, onClickItemDialog: (positionItemDialog: Int) -> Unit): ListDialog {
        val itemDialog = ItemListDialogController { positionItem ->
            onClickItemDialog.invoke(positionItem)
            dismiss()
        }
        val mDialogList = ItemList.create().apply {
            addAll(listItem, itemDialog)
        }
        setListToAdapter(mDialogList)
        return this
    }
}