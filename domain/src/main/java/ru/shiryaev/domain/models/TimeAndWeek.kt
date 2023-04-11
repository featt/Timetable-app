package ru.shiryaev.domain.models

import androidx.room.ColumnInfo
import ru.shiryaev.domain.utils.UtilsTable

class TimeAndWeek(

        @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_TIMESTART)
        var mTimeStart: Int,

        @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_WEEK)
        var mWeek: String
)