package com.feyzaurkut.movieapp.util

import android.os.SystemClock.elapsedRealtime
import android.view.View

abstract class DoubleClickListener(
    private val doubleClickQualificationTime: Long = 200
) : View.OnClickListener {

    private var timestampLastClick = 0L

    /***/
    override fun onClick(v: View) {
        if ((elapsedRealtime() - timestampLastClick) < doubleClickQualificationTime)
            onDoubleClick(v)
        timestampLastClick = elapsedRealtime();
    }

    /** When the view is double clicked */
    abstract fun onDoubleClick(v: View)
}