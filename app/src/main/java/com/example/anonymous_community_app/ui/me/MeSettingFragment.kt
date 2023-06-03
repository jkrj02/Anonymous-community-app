package com.example.anonymous_community_app.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentMeModifyBinding
import com.example.anonymous_community_app.databinding.FragmentMeSettingBinding

class MeSettingFragment : Fragment() {
    private var _binding: FragmentMeSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeSettingBinding.inflate(inflater, container, false)

        binding.meSettingReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.meSettingSignOut.setOnClickListener {
            viewModel.user.value = null
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}