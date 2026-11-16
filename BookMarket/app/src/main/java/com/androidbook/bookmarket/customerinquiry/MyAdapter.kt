package com.androidbook.bookmarket.customerinquiry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.androidbook.bookmarket.R

class MyAdapter(private val itemList: ArrayList<MyItem>) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): Any = itemList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = itemList[position]
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_board_item, parent, false)

        val tvTitle: TextView = view.findViewById(R.id.textView_Title)
        val tvDate: TextView = view.findViewById(R.id.textView_Date)
        val tvId: TextView = view.findViewById(R.id.textView_UserID)

        tvTitle.text = item.title
        tvDate.text = item.date
        tvId.text = item.id

        return view
    }
}