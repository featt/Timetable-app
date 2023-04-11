package ru.shiryaev.schedule.tools.adapters

import androidx.databinding.BindingAdapter
import ru.shiryaev.schedule.ui.views.TextField

object CustomBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:textField")
    fun textField(field: TextField, text: String?) {
        if (text != null) {
            field.setTextField(text)
        }
    }
}