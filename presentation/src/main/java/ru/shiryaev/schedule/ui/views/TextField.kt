package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.shiryaev.schedule.R

class TextField @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.style.TextInputStyle
) : TextInputLayout(ContextThemeWrapper(context, R.style.TextInputStyle), attrs, defStyleAttr), TextWatcher {

    var onTextChanged: ((String) -> Unit)? = null
    var onCLickEndIcon: (() -> Unit)? = null

    var mEditText: TextInputEditText = TextInputEditText(context)
        private set

    init {
        this.isHintEnabled = false
        this.setEndIconOnClickListener { onCLickEndIcon?.invoke() }

        attrs?.let {
            val a = context.theme.obtainStyledAttributes(
                    attrs, R.styleable.TextField, 0, 0
            )
            with (mEditText) {
                hint = a.getString(R.styleable.TextField_hint)
            }
            a.recycle()
        }

        addView(mEditText)

        mEditText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        TODO("Not yet implemented")
    }

    override fun afterTextChanged(s: Editable?) {
        onTextChanged?.invoke(s.toString())
    }

    fun endIconVisible(value: Boolean) {
        this.isEndIconVisible = value
    }

    fun setTextField(text: String) {
        mEditText.setText(text)
    }
}