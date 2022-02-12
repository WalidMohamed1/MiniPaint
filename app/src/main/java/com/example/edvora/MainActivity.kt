package com.example.edvora

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var mDrawingView: DrawingView? = null
    private var colorLayout: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mDrawingView = findViewById<View>(R.id.drawingView) as DrawingView
        colorLayout = findViewById<View>(R.id.colors_menu) as LinearLayout
        val pencil = findViewById<ImageButton>(R.id.pencil_btn)
        val arrow = findViewById<ImageButton>(R.id.arrow_btn)
        val rectangle = findViewById<ImageButton>(R.id.rectangle_btn)
        val circle = findViewById<ImageButton>(R.id.circle_btn)


        pencil.setOnClickListener {
            Toast.makeText(this, "Pencil selected !", Toast.LENGTH_SHORT).show()
            mDrawingView!!.mCurrentShape = DrawingView.PENCIL
            mDrawingView!!.reset()
        }

        arrow.setOnClickListener {
            Toast.makeText(this, "Arrow selected !", Toast.LENGTH_SHORT).show()
            mDrawingView!!.mCurrentShape = DrawingView.LINE
            mDrawingView!!.reset()
        }

        rectangle.setOnClickListener {
            Toast.makeText(this, "Rectangle selected !", Toast.LENGTH_SHORT).show()
            mDrawingView!!.mCurrentShape = DrawingView.RECTANGLE
            mDrawingView!!.reset()
        }

        circle.setOnClickListener {
            Toast.makeText(this, "Circle selected !", Toast.LENGTH_SHORT).show()
            mDrawingView!!.mCurrentShape = DrawingView.CIRCLE
            mDrawingView!!.reset()
        }

    }

    fun colorMenu(view: View?) {
        if (colorLayout!!.visibility == View.VISIBLE) {
            colorLayout!!.visibility = View.INVISIBLE
        } else {
            colorLayout!!.visibility = View.VISIBLE
        }
    }

}