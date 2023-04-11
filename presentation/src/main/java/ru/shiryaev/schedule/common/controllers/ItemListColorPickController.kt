package ru.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shiryaev.domain.models.ColorPick
import ru.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemListColorPickController(
        var onColorPick: (Int) -> Unit
) : BindableItemController<Any, ItemListColorPickController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Any) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Any>(parent, R.layout.layout_recycler_view) {

        private val mRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
        private val mEasyAdapter = EasyAdapter()
        private val mListColorPick: ArrayList<ColorPick> = arrayListOf()
        private val itemColor = ItemColorPickerController { position ->
            for (i in mListColorPick.indices) {
                mListColorPick[i].mIsSelected = i == position
            }
//            setListToAdapter()
            mEasyAdapter.notifyDataSetChanged()
            onColorPick.invoke(position)
        }

        init {
            with(mRecyclerView) {
                adapter = mEasyAdapter
                layoutManager = GridLayoutManager(itemView.context, 6)
            }
            itemView.context.resources.getIntArray(R.array.color_pick).forEach { color ->
                mListColorPick.add(ColorPick(mColor = color))
            }
            setListToAdapter()
        }

        override fun bind(data: Any) {}

        private fun setListToAdapter() {
            val listItem = ItemList.create().apply {
                addAll(mListColorPick, itemColor)
            }
            mEasyAdapter.setItems(listItem)
        }
    }
}