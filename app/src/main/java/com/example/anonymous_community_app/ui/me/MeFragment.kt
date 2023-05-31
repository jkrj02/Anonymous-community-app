package com.example.anonymous_community_app.ui.me

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentMainBinding
import com.example.anonymous_community_app.databinding.FragmentMeBinding

class MeFragment : Fragment() {
    private var _binding: FragmentMeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MeViewModel::class.java]

        binding.login.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fullscreen).navigate(R.id.navigation_login)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}