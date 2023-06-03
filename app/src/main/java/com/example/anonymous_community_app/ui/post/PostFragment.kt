package com.example.anonymous_community_app.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.databinding.FragmentPostBinding
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.server.NetworkSettings
import com.example.anonymous_community_app.server.ServerUtils
import com.example.anonymous_community_app.ui.Utils
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            val data = isEditTextOrNull(binding.postSearchText.text.toString())
            getPostList(data)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {
            val data = isEditTextOrNull(binding.postSearchText.text.toString())
            getPostList(data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.postPublish.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fullscreen).navigate(R.id.navigation_post_publish)
        }

        binding.postSearchButton.setOnClickListener {
            val data = isEditTextOrNull(binding.postSearchText.text.toString())
            getPostList(data)
        }

        viewModel.postList.observe(viewLifecycleOwner) {
            val type = binding.postType.selectedTabPosition
            val sortType = binding.postSortType.selectedTabPosition
            val postList = Utils.sortPost(it, type, sortType)
            binding.postList.adapter = PostAdapter(postList)
        }

        binding.postType.addOnTabSelectedListener(onTabSelectedListener)

        binding.postSortType.addOnTabSelectedListener(onTabSelectedListener)

        getPostList()
    }

    private fun getPostList(data: String? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            val restResponse = if (data != null) {
                Interface.getPostList(NetworkSettings.POST_SEARCH, data)
            } else {
                Interface.getPostList(NetworkSettings.POST_GET)
            }
            if (restResponse.code == 1) {
                viewModel.postList.postValue(restResponse.data as List<Post>?)
            }
        }
    }

    private fun isEditTextOrNull(data: String): String? {
        return if (data == "") {
            null
        } else {
            data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}