package ru.shiryaev.schedule.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shiryaev.schedule.databinding.CustomLayoutDialogBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

open class CustomDialog : DialogFragment() {

    private var _binding: CustomLayoutDialogBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = CustomLayoutDialogBinding.inflate(inflater, container, false)

        // Устанавливаем прозрачность в 80%
//        binding.layoutDialog.background.alpha = 204

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initLayout()
        initList()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun setListToAdapter(listItem: ItemList) { mEasyAdapter.setItems(listItem) }

    private fun initList() {
        with(binding.dialogRv) {
            adapter = mEasyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initLayout() {
        // Устанавливаем диалог в низ экрана
        val p = dialog!!.window!!.attributes
        p.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.apply {
            attributes = p
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}