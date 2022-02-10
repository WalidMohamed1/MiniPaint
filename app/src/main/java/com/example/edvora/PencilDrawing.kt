package com.example.edvora

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class DrawingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //drawing path
    private var drawPath: Path? = null
    private var erase = false

    //drawing and canvas paint
    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null

    //initial color
    private var paintColor = Color.BLACK

    //canvas
    private var drawCanvas: Canvas? = null




    fun setErase(isErase: Boolean) {
        erase = isErase
        if (erase) drawPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) else drawPaint!!.xfermode =
            null
    }




    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(drawPath!!, drawPaint!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath!!.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath!!.lineTo(touchX, touchY)
            else -> return false
        }
        invalidate()
        return true
    }

    fun setColor(newColor: String?) {
        invalidate()
        paintColor = Color.parseColor(newColor)
        drawPaint!!.color = paintColor
    }

    fun setupDrawing() {
        drawPath = Path()
        drawPaint = Paint()
        drawPaint!!.color = paintColor
        drawPaint!!.isAntiAlias = true
        drawPaint!!.strokeWidth = 10F
        drawPaint!!.style = Paint.Style.STROKE

    }

    init {
        setupDrawing()
    }
}