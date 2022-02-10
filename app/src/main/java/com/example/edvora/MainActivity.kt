package com.example.edvora

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var drawBtn: ImageButton? = null
    private var erase: ImageButton? = null
    private var drawView: DrawingView? = null
    private var paintLayout: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawView = findViewById<View>(R.id.drawing) as DrawingView
        drawBtn = findViewById<View>(R.id.pencil_btn) as ImageButton
        erase = findViewById<View>(R.id.erase_btn) as ImageButton
        paintLayout = findViewById<View>(R.id.colors_menu) as LinearLayout
        drawBtn!!.setOnClickListener(this)
        erase!!.setOnClickListener(this)

    }

    fun colorMenu(view: View?) {
        if (paintLayout!!.visibility == View.VISIBLE) {
            paintLayout!!.visibility = View.INVISIBLE
        } else {
            paintLayout!!.visibility = View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.`menu_main`, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.pencil_btn) {
            drawView!!.setupDrawing()
        }
        if (v.id == R.id.erase_btn) {
            drawView!!.setErase(true)
        }
    }
}