package ru.shiryaev.data.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue

fun fetchColorPrimary(mContext: Context) : Int {
    val typedValue = TypedValue()
    val a: TypedArray = mContext.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.colorPrimary))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun fetchColorText(mContext: Context) : Int {
    val typedValue = TypedValue()
    val a: TypedArray = mContext.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.textColorSecondary))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}