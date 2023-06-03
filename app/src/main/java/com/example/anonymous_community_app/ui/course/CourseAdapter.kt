package com.example.anonymous_community_app.ui.course

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.Course

class CourseAdapter(p0: Context, p1: List<Course>) : BaseAdapter() {
    private var context = p0
    private var courseList = p1

    override fun getCount(): Int {
        return courseList.size
    }

    override fun getItem(p0: Int): Any {
        return courseList.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.course_gridview_item, null)
        val courseName = view.findViewById<TextView>(R.id.course_name)
        val teacherName = view.findViewById<TextView>(R.id.teacher_name)
        courseName.text = courseList[p0].courseName
        teacherName.text = courseList[p0].teacherName
        return view
    }
}