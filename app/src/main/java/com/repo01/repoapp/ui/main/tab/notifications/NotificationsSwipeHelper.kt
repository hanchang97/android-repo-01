package com.repo01.repoapp.ui.main.tab.notifications

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class NotificationsSwipeHelper(private val listener: ItemTouchHelperListener, private val deleteIcon: Drawable) :
    ItemTouchHelper.Callback() {

    private val swipeBackground = ColorDrawable(Color.parseColor("#448CF6"))

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) // 좌우 양방향 스와이프 가능
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // 드래그 시 호출
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.itemSwipe(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

        if (dX > 0) {
            swipeBackground.setBounds(
                itemView.left,
                itemView.top,
                dX.toInt(),
                itemView.bottom
            )
            deleteIcon.setBounds(
                itemView.left + iconMargin,
                itemView.top + iconMargin,
                itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                itemView.bottom - iconMargin
            )
        } else {
            swipeBackground.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            deleteIcon.setBounds(
                itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                itemView.top + iconMargin,
                itemView.right - iconMargin,
                itemView.bottom - iconMargin
            )
        }

        swipeBackground.draw(c)

        // icon이 아이템뷰 위에 남아 있으면 안됨
        if(dX > 0)
            c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
        else
            c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}