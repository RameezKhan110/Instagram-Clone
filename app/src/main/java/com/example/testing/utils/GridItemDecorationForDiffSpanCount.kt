package com.example.testing.utils

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GridItemDecorationForDiffSpanCount(
    val spacing: Int,
    private val spanCount: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    /**
     * Applies padding to all sides of the [Rect], which is the container for the view
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val gridLayoutManager: GridLayoutManager = parent.layoutManager as GridLayoutManager
        val spanCount: Int = gridLayoutManager.spanCount

        val params: GridLayoutManager.LayoutParams =
            view.layoutParams as GridLayoutManager.LayoutParams

        val spanIndex = params.spanIndex
        val spanSize = params.spanSize

        // If it is in column 0 you apply the full offset on the start side, else only half
        if (spanIndex == 0) {
            outRect.left = spacing
        } else {
            outRect.left = spacing / 2
        }

        // If spanIndex + spanSize equals spanCount (it occupies the last column) you apply the full offset on the end, else only half.
        if (spanIndex + spanSize == spanCount) {
            outRect.right = spacing
        } else {
            outRect.right = spacing / 2
        }

        // just add some vertical padding as well
        outRect.top = spacing / 2
        outRect.bottom = spacing / 2
    }

    private fun isLayoutRTL(parent: RecyclerView): Boolean {
        return parent.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL
    }
}