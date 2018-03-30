package com.bin.yuan.dancer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bin.yuan.fpsdancer.Calculation
import com.bin.yuan.fpsdancer.FPSDancer
import com.bin.yuan.fpsdancer.annotation.Developer
import com.bin.yuan.fpsdancer.data.FPSStatistics
import com.bin.yuan.fpsdancer.ui.CoachAdapter
import kotlinx.android.synthetic.main.activity_main.*

@Developer(id = "12",name = "yuanbin",useFormatStr = false)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        FPSDancer.create(this.application)
                .setStatistics(FPSStatistics())
                .setSampleSize(20)
                .setSavePath("fpsdancer")
                .setCoach(true)
                .setCoachAdapter(object : CoachAdapter() {
                    override fun getView(): View {
                        return LayoutInflater.from(this@MainActivity).inflate(R.layout.meter_view, null)
                    }
                    override fun updateData(view: View?, metric: Calculation.Metric?, fsp: Long?) {
                        when {
                            metric === Calculation.Metric.BAD -> (view as TextView).setBackgroundResource(R.drawable.fpsmeterring_bad)
                            metric=== Calculation.Metric.MEDIUM -> (view as TextView).setBackgroundResource(R.drawable.fpsmeterring_medium)
                            else -> (view as TextView).setBackgroundResource(R.drawable.fpsmeterring_good)
                        }
                        view.text = fsp.toString()
                    }
                })
                .build()
        fab.setOnClickListener { view ->
            var intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }

}
