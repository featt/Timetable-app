package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.widget.TableRow
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.shiryaev.schedule.R

class CustomButtonDialog(context: Context) : TableRow(context) {

    private val mButton: MaterialCardView
    private val mTextBtn: MaterialTextView

    init {
        this.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1F
        )

        inflate(context, R.layout.custom_button_dialog, this)

        mButton = this.findViewById(R.id.item_btn)
        mTextBtn = this.findViewById(R.id.text_btn)
    }

    fun setText(text: String, onClickBtn: (String) -> Unit): CustomButtonDialog {
        mTextBtn.text = text
        mButton.setOnClickListener { onClickBtn.invoke(text) }
        return this
    }
}