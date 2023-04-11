package ru.shiryaev.schedule.common.navigation

import ru.shiryaev.schedule.ui.AddNoteActivity
import ru.shiryaev.schedule.ui.AddScheduleActivity

enum class ActivityClass(val activity: Class<*>) {
    CREATE_SCHEDULE(AddScheduleActivity::class.java),
    EDIT_SCHEDULE(AddScheduleActivity::class.java),
    CREATE_NOTE(AddNoteActivity::class.java),
    EDIT_NOTE(AddNoteActivity::class.java)
}