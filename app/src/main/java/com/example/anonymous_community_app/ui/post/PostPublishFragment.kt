package com.example.anonymous_community_app.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.data.Post
import com.example.anonymous_community_app.databinding.FragmentPostPublishBinding
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostPublishFragment : Fragment() {

    private var _binding: FragmentPostPublishBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostPublishBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.postPublishReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.postPublishConfirm.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (viewModel.user.value == null) {
                    Utils.showShortToast(requireContext(), "登陆后才能发布帖子")
                    return@launch
                }
                val type = binding.postPublishType.selectedItem.toString()
                val content = binding.postPublishContent.text.toString()
                if (content == "") {
                    Utils.showShortToast(requireContext(), "内容不能为空")
                    return@launch
                }
                var userName = binding.postPublishUsername.text.toString()
                if (userName == "") {
                    userName = viewModel.user.value!!.userName
                }
                val post =
                    Post(userId = viewModel.user.value!!.userId, typeId = Utils.getPostTypeId(type), content = content, userName = userName,
                        time = (System.currentTimeMillis() / 1000).toInt())
                val restResponse = Interface.postPublish(post)
                if (restResponse.code == 1) {
                    Utils.showShortToast(requireContext(), "发布成功")
                    requireActivity().onBackPressed()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}