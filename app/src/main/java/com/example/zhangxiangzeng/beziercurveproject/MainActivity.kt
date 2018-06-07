package com.example.zhangxiangzeng.beziercurveproject

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        findViewById<TextView>(R.id.toPointView).setOnClickListener {
            startActivity(buildIntent(PointViewActivity::class.java))
        }
        findViewById<TextView>(R.id.toWaveView).setOnClickListener {
            startActivity(buildIntent(WaveViewActivity::class.java))
        }
    }

    private fun buildIntent(clazz: Class<out Context>) : Intent{
        val intent = Intent()
        intent.setClass(this,clazz)
        return intent
    }
}
