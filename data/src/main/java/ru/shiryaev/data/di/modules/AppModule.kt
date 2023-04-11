package ru.shiryaev.data.di.modules

import androidx.room.Room
import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.repository.WeekRepository
import ru.shiryaev.data.database.repository.ScheduleRepository
import ru.shiryaev.data.database.Storage
import ru.shiryaev.data.database.repository.NoteRepository
import ru.shiryaev.domain.utils.UtilsDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mApp: AppDelegate) {

    @Provides
    @Singleton
    fun provideApp() = mApp

    @Provides
    @Singleton
    fun provideStorage() = Room.databaseBuilder(mApp, Storage::class.java, UtilsDb.APP_DATABASE.name)
            .addMigrations(Storage.MIGRATION_1_2)
            .build()

    @Provides
    @Singleton
    fun provideScheduleRepository() = ScheduleRepository()

    @Provides
    @Singleton
    fun provideWeekRepository() = WeekRepository()

    @Provides
    @Singleton
    fun provideNoteRepository() = NoteRepository()
}