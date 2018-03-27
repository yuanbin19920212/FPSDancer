package com.bin.yuan.dancer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bin.yuan.fpsdancer.annotation.Developer
import kotlinx.android.synthetic.main.activity_main.*

@Developer(id = "12",name = "yuanbin",useFormatStr = false)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }

}
