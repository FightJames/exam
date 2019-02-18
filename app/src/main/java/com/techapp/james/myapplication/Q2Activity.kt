package com.techapp.james.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayout
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_q2.*
import kotlinx.android.synthetic.main.red_item.view.*
import kotlinx.android.synthetic.main.sure_text.view.*

class Q2Activity : AppCompatActivity() {
    var x: Int = 0
    var y: Int = 0
    var w: Int = 0
    var h: Int = 0
    var margin = 5
    var flag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q2)
        x = intent.getIntExtra(MainActivity.X, 0)
        y = intent.getIntExtra(MainActivity.Y, 0)
    }

    var isDo = false
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!isDo) {
            setData()
            isDo = true
            var handler = Handler()
            handler.post(object : Runnable {
                override fun run() {
                    var randomX = (1..x).random()
                    var randomY = (1..(y - 1)).random()
                    select(randomX, randomY)
                    if (flag) {
                        handler.postDelayed(this, 10000)
                    }
                }

            })
        }
    }

    var lastTargetView: View? = null
    var lastButtonView: View? = null
    fun select(randomX: Int, randomY: Int) {
//         randomX-1 randomY-1  (randomY-1)* (row size)+randomX-1 = index
//        (y-1)* (row size)+randomX-1 = buttom index
        lastTargetView?.let {
            it.randomTextView.visibility = View.INVISIBLE
        }
        lastButtonView?.let {
            it.sureTextView.setBackgroundResource(R.drawable.rectangle_btn)
            it.sureTextView.setOnClickListener { }
        }

        selectTextView.visibility = View.INVISIBLE
        Log.d("index ", "$randomX  $randomY")
        var targetIndex = (randomY - 1) * x + (randomX - 1)
        var buttonIndex = (y - 1) * x + (randomX - 1)
        Log.d("index ", "$targetIndex  $buttonIndex")
        var targetView = gridLayout.getChildAt(targetIndex)
        var buttonView = gridLayout.getChildAt(buttonIndex)

        selectTextView.y = root.y - root.translationY

        var xP = gridLayout.left + targetView.left
        selectTextView.x = xP.toFloat() - (margin * 2)

        Log.d("x", " ${selectTextView.x} ${selectTextView.y} ${selectTextView.translationY}")
        selectTextView.height = gridLayout.bottom - margin
        selectTextView.width = targetView.width + (margin * 4)
        selectTextView.visibility = View.VISIBLE
//        Log.d("x"," ${selectTextView.height} ${selectTextView.width} ${selectTextView.translationY}" )
        targetView.randomTextView.visibility = View.VISIBLE
        Log.d("target ", targetView.randomTextView.text.toString())
        buttonView.sureTextView.setBackgroundResource(R.drawable.rectangle_btn_blue)
        buttonView.sureTextView.setOnClickListener {
            targetView.randomTextView.visibility = View.INVISIBLE
            it.sureTextView.setBackgroundResource(R.drawable.rectangle_btn)
            selectTextView.visibility = View.INVISIBLE
        }
        lastButtonView = buttonView
        lastTargetView = targetView
    }

    fun setData() {
        w = gridLayout.measuredWidth
        h = gridLayout.measuredHeight
        Log.d("xxxxxxxxx", x.toString() + "  " + gridLayout.visibility + "  " + View.VISIBLE)
        gridLayout.columnCount = x
        gridLayout.rowCount = y
        Log.d("xxxxxxxxxxxx width", w.toString())
        Log.d("xxxxxxxxxxxx heigh", h.toString())
        var eachWidth = w / x
        var eachHeigh = h / y
        for (i in 0..(y - 1)) {
            var currentRow = i
//            Log.d("currentRow ", currentRow.toString())
            for (j in 0..(x - 1)) {
                Log.d("xxxxxxxxx", "draw $j")
                if (i != (y - 1)) {
                    if (currentRow % 2 == 0) {
                        var item = LayoutInflater.from(this).inflate(R.layout.red_item, null)
                        setItemView(i, j, eachWidth, eachHeigh, item, false)
                        gridLayout.addView(item)
                    } else {
                        var item = LayoutInflater.from(this).inflate(R.layout.green_item, null)
//                    Log.d("xxxxxxxxx", "draw ${params.width}")
//                    Log.d("xxxxxxxxx", "draw ${params.height}")
//                    item.layoutParams = params
                        setItemView(i, j, eachWidth, eachHeigh, item, false)
                        gridLayout.addView(item)
                    }
                } else {
                    var item = LayoutInflater.from(this).inflate(R.layout.sure_text, null)
                    setItemView(i, j, eachWidth, eachHeigh, item, true)
//                    item.textView.setBackgroundResource(R.drawable.rectangle_btn_blue)
                    gridLayout.addView(item)
                }
            }
        }

    }

    fun setItemView(
        i: Int,
        j: Int,
        eachWidth: Int,
        eachHeigh: Int,
        item: View,
        isButton: Boolean
    ) {
        val rowSpec = GridLayout.spec(i, 1.0f)
        val columnSpec = GridLayout.spec(j, 1.0f)
        val params = GridLayout.LayoutParams(rowSpec, columnSpec)
        params.bottomMargin = margin
        if (!isButton) {
            params.rightMargin = margin
            params.leftMargin = margin
            params.width = eachWidth - params.rightMargin - params.leftMargin
        } else {
            params.width = eachWidth
        }

        params.height = eachHeigh - params.bottomMargin

        Log.d("xxxxxxxxx", "draw ${params.width}")
        Log.d("xxxxxxxxx", "draw ${params.height}")
        item.layoutParams = params
    }


    override fun onDestroy() {
        super.onDestroy()
        flag = false
    }
}
