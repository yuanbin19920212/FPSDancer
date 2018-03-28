package com.bin.yuan.dancer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bin.yuan.fpsdancer.annotation.Developer
import kotlinx.android.synthetic.main.activity_second.*

@Developer(id = "4343", name = "xiaoming", useFormatStr = false)
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomerAdapter()
    }


    open class CustomerAdapter() : RecyclerView.Adapter<CustomerHolder>() {
        override fun getItemCount(): Int {
            return 100
        }

        override fun onBindViewHolder(holder: CustomerHolder?, position: Int) {
            holder?.bindData("position"+position)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomerHolder {
            var view = LayoutInflater.from(parent?.context).inflate(R.layout.item, null)
            return CustomerHolder(view)
        }
    }

    open class CustomerHolder : RecyclerView.ViewHolder {

        var textView: TextView?

        constructor(view: View) : super(view) {
            textView = view.findViewById<TextView>(R.id.txt)
        }

        open fun bindData(data: String) {
            Thread.sleep(100)
            textView?.text = data
        }
    }
}
