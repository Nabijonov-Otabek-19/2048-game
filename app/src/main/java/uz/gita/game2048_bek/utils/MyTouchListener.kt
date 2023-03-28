package uz.gita.game2048_bek.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.game2048_bek.model.SideEnum
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private val myGestureDetector = GestureDetector(context, MyGestureListener())
    private var myMovementSideListener: ((SideEnum) -> Unit)? = null

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        myGestureDetector.onTouchEvent(event)
        return true
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (abs(e2.x - e1.x) > 100 || abs(e2.y - e1.y) > 100) {
                if (abs(e2.x - e1.x) < abs(e2.y - e1.y)) {  // vertical
                    if (e2.y > e1.y) {// down
                        myMovementSideListener?.invoke(SideEnum.DOWN)
                    } else {  // up
                        myMovementSideListener?.invoke(SideEnum.UP)
                    }
                } else { // horizontal
                    if (e2.x > e1.x) {// right
                        myMovementSideListener?.invoke(SideEnum.RIGHT)
                    } else {  // left
                        myMovementSideListener?.invoke(SideEnum.LEFT)
                    }
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    fun setMyMovementSideListener(block: (SideEnum) -> Unit) {
        myMovementSideListener = block
    }
}