package com.example.anonymous_community_app.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.databinding.FragmentPostBinding
import com.example.anonymous_community_app.databinding.FragmentPostDetailBinding
import com.example.anonymous_community_app.server.Interface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostDetailFragment : Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        binding.postDetailReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}