package ru.shiryaev.schedule.ui.dialogs

import ru.shiryaev.domain.models.Week

interface OnClickButtonDialogListener {
    fun onClick(text: String = "", oldText: String = "", week: Week? = null, dialog: String = "")
}