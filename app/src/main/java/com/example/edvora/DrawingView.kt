package com.example.edvora

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var mCurrentShape = 0
    private var mPath: Path? = null
    private var mPaint: Paint? = null
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var currentColor = Color.BLACK


    private var isDrawing = false
    private var mStartX = 0f
    private var mStartY = 0f
    private var mx = 0f
    private var my = 0f

    companion object {
        const val PENCIL = 1
        const val LINE = 2
        const val RECTANGLE = 3
        const val CIRCLE = 4
        const val TOUCH_TOLERANCE = 4f
        const val TOUCH_STROKE_WIDTH = 10f
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mPaint)
        if (isDrawing) {
            when (mCurrentShape) {
                LINE -> onDrawLine(canvas)
                RECTANGLE -> onDrawRectangle(canvas)
                CIRCLE -> onDrawCircle(canvas)
            }
        }
    }

    init {
        mPath = Path()
        mPaint = Paint(Paint.DITHER_FLAG)
        mPaint!!.isAntiAlias = true
        mPaint!!.isDither = true
        mPaint!!.color = currentColor
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeJoin = Paint.Join.ROUND
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.strokeWidth = TOUCH_STROKE_WIDTH
    }

    fun reset() {
        mPath = Path()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mx = event.x
        my = event.y
        when (mCurrentShape) {
            LINE -> onTouchEventLine(event)
            PENCIL -> onTouchEventPencil(event)
            RECTANGLE -> onTouchEventRectangle(event)
            CIRCLE -> onTouchEventCircle(event)
        }
        return true
    }

    //-------------------------------Pencil-----------------------------------

    private fun onTouchEventPencil(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                mPath!!.reset()
                mPath!!.moveTo(mx, my)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(mx - mStartX)
                val dy = abs(my - mStartY)
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath!!.quadTo(mStartX, mStartY, (mx + mStartX) / 2, (my + mStartY) / 2)
                    mStartX = mx
                    mStartY = my
                }
                mCanvas!!.drawPath(mPath!!, mPaint!!)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mPath!!.lineTo(mStartX, mStartY)
                mCanvas!!.drawPath(mPath!!, mPaint!!)
                mPath!!.reset()
                invalidate()
            }
        }
    }

    //-------------------------------Arrow-----------------------------------

    private fun onDrawLine(canvas: Canvas) {
        val dx = abs(mx - mStartX)
        val dy = abs(my - mStartY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(mStartX, mStartY, mx, my, mPaint!!)
        }
    }

    private fun onTouchEventLine(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mCanvas!!.drawLine(mStartX, mStartY, mx, my, mPaint!!)
                invalidate()
            }
        }
    }

    //-------------------------------Circle-----------------------------------

    private fun onDrawCircle(canvas: Canvas) {
        canvas.drawCircle(mStartX, mStartY, calculateRadius(mStartX, mStartY, mx, my), mPaint!!)
    }

    private fun onTouchEventCircle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mCanvas!!.drawCircle(
                    mStartX, mStartY, calculateRadius(mStartX, mStartY, mx, my),
                    mPaint!!
                )
                invalidate()
            }
        }
    }

    private fun calculateRadius(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(
            (x1 - x2).toDouble().pow(2.0) +
                    (y1 - y2).toDouble().pow(2.0)
        ).toFloat()
    }

    //-------------------------------Rectangle-----------------------------------

    private fun onDrawRectangle(canvas: Canvas) {
        drawRectangle(canvas, mPaint)
    }

    private fun onTouchEventRectangle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mx
                mStartY = my
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawRectangle(mCanvas, mPaint)
                invalidate()
            }
        }
    }

    private fun drawRectangle(canvas: Canvas?, paint: Paint?) {
        val right = if (mStartX > mx) mStartX else mx
        val left = if (mStartX > mx) mx else mStartX
        val bottom = if (mStartY > my) mStartY else my
        val top = if (mStartY > my) my else mStartY
        canvas!!.drawRect(left, top, right, bottom, paint!!)
    }

}