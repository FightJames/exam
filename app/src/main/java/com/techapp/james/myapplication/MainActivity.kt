package com.techapp.james.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val X = "X"
        val Y = "Y"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterBtn.setOnClickListener {

            var xString = xEditText.text.toString()
            var yString = yEditText.text.toString()
            if ((!xString.equals("")) && (!yString.equals(""))) {
                var x = xString.toInt()
                var y = yString.toInt()
                var flag = true
                if (x < 0) {
                    xEditText.hint = " X must more than 0"
                    flag = false
                }
                if (y < 0) {
                    yEditText.hint = " Y must more than 0"
                    flag = false
                }
                if (flag) {
                    var i = Intent(this@MainActivity, Q2Activity::class.java)
                    i.putExtra(X, x)
                    i.putExtra(Y, y + 1)
                    startActivity(i)
                }
            }
        }
    }
}
