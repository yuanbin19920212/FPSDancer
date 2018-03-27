package com.bin.yuan.dancer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bin.yuan.fpsdancer.annotation.Developer

@Developer(id = "4343",name = "xiaoming",useFormatStr = false)
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}
