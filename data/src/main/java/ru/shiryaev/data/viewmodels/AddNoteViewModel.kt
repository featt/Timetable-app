package ru.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.shiryaev.data.AppDelegate
import ru.shiryaev.data.database.repository.NoteRepository
import ru.shiryaev.domain.models.Note
import javax.inject.Inject

class AddNoteViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: NoteRepository

    private val mFabIsVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectAddNoteViewModel(this)
    }

    fun getListLessons() = mRepository.getLessons()

    fun setFabIsVisible(value: Boolean) { mFabIsVisible.value = value }

    fun getFabIsVisible() = mFabIsVisible

    fun insertNote(note: Note) { mRepository.insertNote(note) }

    fun updateNote(note: Note) { mRepository.updateNote(note) }
}