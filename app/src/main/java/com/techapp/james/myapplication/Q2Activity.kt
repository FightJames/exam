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
    var margin = 10
    var flag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q2)
        x = intent.getIntExtra(MainActivity.X, 0)
        y = intent.getIntExtra(MainActivity.Y, 0)
        var handler = Handler()
        Log.d("xxxxxxxxxxx ", " Create $gridLayout")
        gridLayout.setBackgroundResource(R.color.background_material_light)
//        gridLayout.addOnLayoutChangeListener(this)
        Log.d("xxxxxxxxxxx ", "after add")
//        var total = x * y
//        for (i in 0..100) {
//            var textView = TextView(this)
//            textView.text = "Hello $i"
//            tableLayout.addView(textView)
//        }

        handler.postDelayed(object : Runnable {
            override fun run() {
                var randomX = (1..x).random()
                var randomY = (1..(y - 1)).random()
                Log.d("test ran ", "hello")
                select(randomX, randomY)
                if (flag) {
                    handler.postDelayed(this, 1000)
                }
            }

        }, 1000)
    }

    var isDo = false
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!isDo) {
            setData()
            isDo = true
        }
    }

    var lastTargetView: View? = null
    var lastButtonView: View? = null
    fun select(randomX: Int, randomY: Int) {
        gridLayout.requestFocus()
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
        var topIndex = (randomX - 1)
        Log.d("index ", "$targetIndex  $buttonIndex")
        var targetView = gridLayout.getChildAt(targetIndex)
        var buttonView = gridLayout.getChildAt(buttonIndex)
        var topView = gridLayout.getChildAt(topIndex)

        selectTextView.y = root.y - root.translationY
//        - selectTextView.translationY
        selectTextView.x =
            (topView.x + topView.translationX) + margin
        Log.d("x", " ${selectTextView.x} ${selectTextView.y} ${selectTextView.translationY}")
        selectTextView.height = gridLayout.bottom
        // add stoke width
        var stokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, //轉換dp值
            5f, //dp值
            resources.getDisplayMetrics()
        )
        selectTextView.width = topView.measuredWidth + margin * 2 + stokeWidth.toInt()
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
                        if (j == (x - 1)) {
                            setItemView(i, j, eachWidth, eachHeigh, item, true)
                        } else {
                            setItemView(i, j, eachWidth, eachHeigh, item, false)
                        }
                        gridLayout.addView(item)
                    } else {
                        var item = LayoutInflater.from(this).inflate(R.layout.green_item, null)
//                    Log.d("xxxxxxxxx", "draw ${params.width}")
//                    Log.d("xxxxxxxxx", "draw ${params.height}")
//                    item.layoutParams = params
                        if (j == (x - 1)) {
                            setItemView(i, j, eachWidth, eachHeigh, item, true)
                        } else {
                            setItemView(i, j, eachWidth, eachHeigh, item, false)
                        }
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

    // randomX-1 randomY-1  (randomY-1)* (row size)+randomX-1 = index
    //(y-1)* (row size)+randomX-1 = buttom index
    fun setItemView(
        i: Int,
        j: Int,
        eachWidth: Int,
        eachHeigh: Int,
        item: View,
        isRightest: Boolean
    ) {
        val rowSpec = GridLayout.spec(i, 1.0f)
        val columnSpec = GridLayout.spec(j, 1.0f)
        val params = GridLayout.LayoutParams(rowSpec, columnSpec)
        params.bottomMargin = 10
        params.rightMargin = margin
        if (isRightest) {
            params.rightMargin = 0
        }
        params.width = eachWidth - params.rightMargin
        params.height = eachHeigh - params.bottomMargin

        Log.d("xxxxxxxxx", "draw ${params.width}")
        Log.d("xxxxxxxxx", "draw ${params.height}")
        item.layoutParams = params
    }

    override fun onDestroy() {
        super.onDestroy()
//        gridLayout.removeOnLayoutChangeListener(this)
        Log.d("xxxxxxx", " onDestroy")
        flag = false
    }
}
