package com.example.baseadapterii

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(private val list:ArrayList<Student>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(postion: Int): Long {
        return postion.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null) view = LayoutInflater.from(parent?.context).inflate(R.layout.itemview,parent,false)
        view?.findViewById<TextView>(R.id.Name)?.text = list[position].name
        view?.findViewById<TextView>(R.id.RollNo)?.text = list[position].roll
        view?.findViewById<TextView>(R.id.Subject)?.text = list[position].subject
        return view!!
    }
}