package com.example.anonymous_community_app.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.databinding.FragmentMeModifyBinding
import com.example.anonymous_community_app.databinding.FragmentMePublicationBinding
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.server.ServerUtils
import com.example.anonymous_community_app.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MePublicationFragment : Fragment() {
    private var _binding: FragmentMePublicationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMePublicationBinding.inflate(inflater, container, false)

        binding.mePublicationReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        getPostList()

        viewModel.myPostList.observe(viewLifecycleOwner) {
            val adapter = MePublicationAdapter(requireContext(), viewModel, it)
            binding.mePublicationList.adapter = adapter
        }

        return binding.root
    }

    private fun getPostList() {
        CoroutineScope(Dispatchers.Main).launch {
            val restResponse = Interface.getMyPost(viewModel.user.value?.userId)
            if (restResponse.code == 1) {
                viewModel.myPostList.postValue(restResponse.data as List<Post>?)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}