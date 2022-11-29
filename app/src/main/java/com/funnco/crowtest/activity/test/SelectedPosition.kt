package com.funnco.crowtest.activity.test

import android.util.Log

class SelectedPosition(
    private val changeListener: ChangeListener
) {
    var position: Int = 0
        set(value) {
            field = value
            Log.i(this.javaClass.simpleName, "Value changed to $value")
            changeListener.onChange(value)
        }

    interface ChangeListener {
        fun onChange(pos: Int)
    }
}