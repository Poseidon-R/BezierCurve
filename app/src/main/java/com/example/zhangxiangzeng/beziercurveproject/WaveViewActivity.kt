package com.example.zhangxiangzeng.beziercurveproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_wave.view.*


/**
 * <pre>
 *     e-mail : majingze@shuangke.net.cn
 *     time   : 2018/6/7
 *     desc   :
 *     version: 1.0
 *     Copyright: Copyright（c）2017
 *     Company:爽客智能设备有限公司
 * </pre>
 * @author majingze
 */
class WaveViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        initView()
    }

    private fun initView() {
        val waveView = findViewById<WaveView>(R.id.waveView)
        findViewById<TextView>(R.id.play).setOnClickListener {
            waveView.startAnim()
        }
        findViewById<TextView>(R.id.pause).setOnClickListener {
            waveView.stop()
        }
        findViewById<TextView>(R.id.up).setOnClickListener {
            waveView.up()
        }
        findViewById<TextView>(R.id.down).setOnClickListener {
            waveView.down()
        }
    }

}