package ru.shiryaev.schedule.common.controllers

import android.graphics.Color
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.shiryaev.domain.models.Note
import ru.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemNoteController : BindableItemController<Note, ItemNoteController.Holder>() {

    var onClickNote: ((Note) -> Unit)? = null
    var onLongClickNote: ((Note) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Note) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Note>(parent, R.layout.item_note) {

        private val mNoteCard: MaterialCardView = itemView.findViewById(R.id.note_card)
        private val mNoteContainer: LinearLayoutCompat = itemView.findViewById(R.id.card_container)
        private val mTitle: MaterialTextView = itemView.findViewById(R.id.title_tv)
        private val mText: MaterialTextView = itemView.findViewById(R.id.text_tv)
        private val mDeadline: MaterialTextView = itemView.findViewById(R.id.deadline_tv)

        override fun bind(data: Note) {
            if (data.mTitle != null || data.mTitle != "") {
                mTitle.text = data.mTitle
            }
            mTitle.isVisible = mTitle.text != ""

            mText.text = data.mText

            if (data.mDeadline != null || data.mDeadline != "") {
                mDeadline.text = data.mDeadline
            }
            mDeadline.isVisible = mDeadline.text != ""

            // Меняем цвет заметки
            mNoteContainer.setBackgroundColor(setColor(data))

            with (mNoteCard) {
                // Слушатели нажатий
                setOnClickListener { onClickNote?.invoke(data) }
                setOnLongClickListener {
                    onLongClickNote?.invoke(data)
                    true
                }
            }
        }

        private fun setColor(note: Note): Int {
            return if (note.mColor == "") {
                Color.TRANSPARENT
            } else {
                itemView.context.resources.getIntArray(R.array.color_card)[note.mColor.toInt()]
            }
        }
    }
}