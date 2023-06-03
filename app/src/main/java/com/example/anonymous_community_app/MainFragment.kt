package com.example.anonymous_community_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import androidx.navigation.ui.setupWithNavController
import com.example.anonymous_community_app.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val navController = requireActivity().findNavController(R.id.nav_host_bottom)
        binding.navBottom.setOnNavigationItemSelectedListener {
            navController.navigate(
                when (it.itemId) {
                    R.id.navigation_post -> { R.id.navigation_post }
                    R.id.navigation_course -> { R.id.navigation_course }
                    R.id.navigation_message -> { R.id.navigation_message }
                    R.id.navigation_me -> {
                        if (viewModel.user.value != null) { R.id.navigation_me_login } else { R.id.navigation_me }
                    }
                    else -> { R.id.navigation_post}
                }
            )
            return@setOnNavigationItemSelectedListener true
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.navBottom.selectedItemId = binding.navBottom.selectedItemId
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}