package com.example.anonymous_community_app.ui.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentPostBinding
import com.example.anonymous_community_app.databinding.FragmentPostPublishBinding

class PostPublishFragment : Fragment() {

    private var _binding: FragmentPostPublishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostPublishBinding.inflate(inflater, container, false)

        binding.postPublishReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}