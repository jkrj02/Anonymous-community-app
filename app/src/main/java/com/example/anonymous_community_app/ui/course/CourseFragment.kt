package com.example.anonymous_community_app.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.data.Course
import com.example.anonymous_community_app.databinding.FragmentCourseBinding
import com.example.anonymous_community_app.server.Interface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseFragment : Fragment() {
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)

        // 获取课程列表
        CoroutineScope(Dispatchers.Default).launch {
            val restResponse = Interface.getCourseList()
            if (restResponse.code == 1) {
                viewModel.courseList.postValue(restResponse.data as ArrayList<Course>?)
            }
        }

        viewModel.courseList.observe(viewLifecycleOwner) {
            binding.courseList.adapter = CourseAdapter(requireContext(), it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}