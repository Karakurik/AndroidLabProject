package com.itis.androidlabproject.decorator

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorator(
    context: Context,
    spacing: Float = 16f,
) : RecyclerView.ItemDecoration() {

    private val spacingPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacing,
        context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spacingMiddle: Int = spacingPx / 2
        when (position) {
            0 -> {
                outRect.top = spacingPx
                outRect.bottom = spacingMiddle
            }
            state.itemCount - 1 -> {
                outRect.top = spacingMiddle
                outRect.bottom = spacingPx
            }
            else -> {
                outRect.top = spacingMiddle
                outRect.bottom = spacingMiddle
            }
        }
        outRect.left = spacingPx
        outRect.right = spacingPx
    }
}
