package com.example.anonymous_community_app.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentCourseDetailBinding
import com.example.anonymous_community_app.databinding.FragmentPostBinding
import com.example.anonymous_community_app.databinding.FragmentPostDetailBinding
import com.google.android.material.tabs.TabLayout

class CourseDetailFragment : Fragment() {
    private var _binding: FragmentCourseDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseDetailBinding.inflate(inflater, container, false)

        binding.courseDetailReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}