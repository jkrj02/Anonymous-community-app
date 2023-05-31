package com.example.anonymous_community_app.ui.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentMeBinding
import com.example.anonymous_community_app.databinding.FragmentPostBinding
import com.example.anonymous_community_app.ui.me.MeViewModel

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        binding.postPublish.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fullscreen).navigate(R.id.navigation_post_publish)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}