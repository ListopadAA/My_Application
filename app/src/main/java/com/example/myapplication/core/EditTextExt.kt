package com.example.myapplication.core

import android.widget.EditText

fun EditText.update(text: String) {
    setText(text)
    setSelection(text.length)
}