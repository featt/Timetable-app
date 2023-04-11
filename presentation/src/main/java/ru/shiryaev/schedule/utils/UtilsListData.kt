package ru.shiryaev.schedule.utils

import android.content.Context
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.UtilsConvert
import ru.shiryaev.schedule.R
import ru.shiryaev.data.common.models.ItemDialog
import java.util.ArrayList

object UtilsListData {

    fun getListScheduleDialog(context: Context) = arrayListOf(
        ItemDialog(icon = R.drawable.ic_pencil_outline, text = context.resources.getStringArray(R.array.list_dialog)[0]),
        ItemDialog(icon = R.drawable.ic_trash_can_outline, text = context.resources.getStringArray(R.array.list_dialog)[1])
    )

    fun getListTimeDialog(list: List<Int>): ArrayList<ItemDialog> {
        val listDialog = ArrayList<ItemDialog>()
        for (i in list) {
            listDialog.add(ItemDialog(text = UtilsConvert.convertTimeIntToString(i)))
        }
        return listDialog
    }

    fun getListDialog(list: List<String>): ArrayList<ItemDialog> {
        val listDialog = ArrayList<ItemDialog>()
        list.forEach { itemText ->
            listDialog.add(ItemDialog(text = itemText))
        }
        return listDialog
    }

    fun getListNameWeekDialog(listWeek: List<Week>, context: Context): ArrayList<ItemDialog> {
        val listDialog = ArrayList<ItemDialog>()
        listWeek.forEach { week ->
            listDialog.add(ItemDialog(text = week.mName))
        }
        listDialog.add(ItemDialog(text = context.resources.getString(R.string.every_week)))
        return listDialog
    }

    fun getRadioListDialog(list: Array<String>, value: String): ArrayList<ItemDialog> {
        val listDialog = ArrayList<ItemDialog>()
        list.forEach { itemText ->
            listDialog.add(ItemDialog(text = itemText, isChecked = value == itemText))
        }
        return listDialog
    }
}