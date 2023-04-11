package ru.shiryaev.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.shiryaev.domain.utils.UtilsChecks
import ru.shiryaev.domain.utils.UtilsTable
import java.io.Serializable

@Entity(tableName = UtilsTable.TABLE_SCHEDULE)
class Schedule : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_ID)
    var mId: Long = 0

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_LESSON)
    var mLesson: String = ""

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_TEACHER)
    var mTeacher: String? = null

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_AUDIT)
    var mAudit: String? = null

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_TIMESTART)
    var mTimeStart: Int = UtilsChecks.TIME_DISABLE

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_TIMEEND)
    var mTimeEnd: Int = UtilsChecks.TIME_DISABLE

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_WEEK)
    var mWeek: String = ""

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_DAY)
    var mDay: Int = 0

    @ColumnInfo(name = UtilsTable.SCHEDULE_COLUMN_EXAM)
    var mExam: String? = null
}