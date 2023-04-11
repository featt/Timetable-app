package ru.shiryaev.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.shiryaev.domain.utils.UtilsTable
import java.io.Serializable

@Entity(tableName = UtilsTable.TABLE_NOTE)
class Note : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UtilsTable.NOTE_ID)
    var mId: Long = 0

    @ColumnInfo(name = UtilsTable.NOTE_TITLE)
    var mTitle: String? = null

    @ColumnInfo(name = UtilsTable.NOTE_TEXT)
    var mText: String = ""

    @ColumnInfo(name = UtilsTable.NOTE_COLOR)
    var mColor: String = ""

    @ColumnInfo(name = UtilsTable.NOTE_DEADLINE)
    var mDeadline: String? = null

    @ColumnInfo(name = UtilsTable.NOTE_IMAGE_PATH)
    var mImagePath: String? = null
}