package ru.shiryaev.data.di

import ru.shiryaev.data.database.repository.NoteRepository
import ru.shiryaev.data.database.repository.ScheduleRepository
import ru.shiryaev.data.database.repository.WeekRepository
import ru.shiryaev.data.di.modules.AppModule
import ru.shiryaev.data.viewmodels.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectPageScheduleViewModel(viewModel: PageScheduleViewModel)
    fun injectAddScheduleViewModel(viewModel: AddScheduleViewModel)
    fun injectAddNoteViewModel(viewModel: AddNoteViewModel)
    fun injectWeekSettingsViewModel(viewModel: WeekSettingsViewModel)
    fun injectNoteViewModel(viewModel: NoteViewModel)
    fun injectWeekRepository(repository: WeekRepository)
    fun injectScheduleRepository(repository: ScheduleRepository)
    fun injectNoteRepository(repository: NoteRepository)
}