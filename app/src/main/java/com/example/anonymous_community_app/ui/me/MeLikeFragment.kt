package com.example.anonymous_community_app.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentMeLikeBinding
import com.example.anonymous_community_app.databinding.FragmentMeModifyBinding

class MeLikeFragment : Fragment() {
    private var _binding: FragmentMeLikeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeLikeBinding.inflate(inflater, container, false)

        binding.meLikeReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}