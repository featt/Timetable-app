package ru.shiryaev.data.database.repository

import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.Storage
import ru.shiryaev.data.database.dao.NoteDao
import ru.shiryaev.data.database.dao.ScheduleDao
import ru.shiryaev.domain.models.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NoteRepository {

    private var mNoteDao: NoteDao
    private var mScheduleDao: ScheduleDao

    @Inject
    lateinit var mStorage: Storage

    init {
        AppDelegate.getAppComponent().injectNoteRepository(this)
        mNoteDao = mStorage.getNoteDao()
        mScheduleDao = mStorage.getScheduleDao()
    }

    fun getNotes() = mNoteDao.getAllNote()

    fun getNotesByDate(date: String) = mNoteDao.getAllNoteByDate(date)

    fun getLessons() = mScheduleDao.getLessons()

    fun insertNote(note: Note) {
        Completable.fromRunnable { mNoteDao.insertNote(note) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateNote(note: Note) {
        Completable.fromRunnable { mNoteDao.updateNote(note) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun deleteNote(note: Note) {
        Completable.fromRunnable { mNoteDao.deleteNote(note) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}