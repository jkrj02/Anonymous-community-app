package com.example.anonymous_community_app.ui.me

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentMeBinding
import com.example.anonymous_community_app.databinding.FragmentMeLoginBinding

class MeLoginFragment : Fragment() {
    private var _binding: FragmentMeLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeLoginBinding.inflate(inflater, container, false)

        val navController = requireActivity().findNavController(R.id.nav_host_fullscreen)

        binding.meLoginModify.setOnClickListener {
            navController.navigate(R.id.navigation_me_modify)
        }
        binding.meLoginPublication.setOnClickListener {
            navController.navigate(R.id.navigation_me_publication)
        }
        binding.meLoginComment.setOnClickListener {
            navController.navigate(R.id.navigation_me_comment)
        }
        binding.meLoginLike.setOnClickListener {
            navController.navigate(R.id.navigation_me_like)
        }
        binding.meLoginSetting.setOnClickListener {
            navController.navigate(R.id.navigation_me_setting)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            binding.meLoginUsername.text = it.userName
            binding.meLoginGender.setImageResource(if (it.gender) { R.drawable.male } else {R.drawable.female})
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}