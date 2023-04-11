package ru.shiryaev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.shiryaev.data.database.dao.NoteDao
import ru.shiryaev.data.database.dao.ScheduleDao
import ru.shiryaev.data.database.dao.WeekDao
import ru.shiryaev.domain.models.Note
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.UtilsTable

@Database(version = 2, entities = [Schedule::class, Week::class, Note::class])
abstract class Storage : RoomDatabase() {
    abstract fun getScheduleDao(): ScheduleDao
    abstract fun getWeekDao(): WeekDao
    abstract fun getNoteDao(): NoteDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ${UtilsTable.TABLE_SCHEDULE} ADD COLUMN ${UtilsTable.SCHEDULE_COLUMN_TIMEEND} INTEGER DEFAULT -1 NOT NULL")
            }
        }
    }
}