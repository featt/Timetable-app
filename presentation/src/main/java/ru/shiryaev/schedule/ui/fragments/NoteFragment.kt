package ru.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.shiryaev.data.common.CustomFactory
import ru.shiryaev.data.viewmodels.NoteViewModel
import ru.shiryaev.domain.models.Note
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.domain.utils.UtilsTable
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.common.controllers.ItemNoteController
import ru.shiryaev.schedule.common.navigation.ActivityClass
import ru.shiryaev.schedule.common.navigation.ActivityRouteCreateNote
import ru.shiryaev.schedule.common.navigation.NavigationActivity
import ru.shiryaev.schedule.databinding.FrNoteBinding
import ru.shiryaev.schedule.ui.dialogs.ListDialog
import ru.shiryaev.schedule.utils.UtilsListData
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class NoteFragment : Fragment(), View.OnClickListener {

    private var _binding: FrNoteBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()
    private lateinit var mViewModel: NoteViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this, CustomFactory(NoteViewModel())).get(NoteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FrNoteBinding.inflate(inflater, container, false)

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@NoteFragment
        }

        with (mViewModel) {
            // Получаем список заметок
            getNotes().observe(viewLifecycleOwner) { notes ->
                val itemNote = ItemNoteController().apply {
                    onClickNote = { note ->
                        // TODO
                    }
                    onLongClickNote = { note ->
                        showListDialog(note)
                    }
                }
                val itemList = ItemList.create().apply {
                    addAll(notes, itemNote)
                }
                mEasyAdapter.setItems(itemList)
                mViewModel.setIsErrorVisible(mEasyAdapter.itemCount == 0)
            }
        }

        initRecyclerView()

        binding.fab.setOnClickListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> {
                val bundle = Bundle().apply {
                    putString(UtilsKeys.REQUEST_CODE.name, ActivityClass.CREATE_NOTE.name)
                }
                NavigationActivity(requireContext()).navigate(ActivityRouteCreateNote(ActivityClass.CREATE_NOTE), bundle)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = mEasyAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun showListDialog(note: Note) {
        ListDialog()
                .setData(UtilsListData.getListScheduleDialog(requireContext())) { positionItem ->
                    actionSchedule(note, positionItem)
                }
                .show(childFragmentManager, null)
    }

    private fun actionSchedule(note: Note, action: Int) {
        val arrayAction = requireContext().resources.getStringArray(R.array.list_dialog)
        when(arrayAction[action]) {
            arrayAction.first() -> {
                val bundle = Bundle().apply {
                    putString(UtilsKeys.REQUEST_CODE.name, ActivityClass.EDIT_NOTE.name)
                    putSerializable(UtilsTable.NOTE, note)
                }
                NavigationActivity(requireContext()).navigate(ActivityRouteCreateNote(ActivityClass.EDIT_NOTE), bundle)
            }
            // Удаление занятия
            arrayAction.last() -> mViewModel.deleteNote(note)
        }
    }
}