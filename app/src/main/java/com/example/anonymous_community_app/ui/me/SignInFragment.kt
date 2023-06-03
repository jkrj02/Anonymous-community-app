package com.example.anonymous_community_app.ui.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.data.User
import com.example.anonymous_community_app.databinding.FragmentSignInBinding
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.server.ResponseCode
import com.example.anonymous_community_app.server.ServerUtils
import com.example.anonymous_community_app.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.loginReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        // 登陆
        binding.loginButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val userName = binding.loginAccount.text.toString()
                val password = binding.loginPassword.text.toString()
                val restResponse = Interface.signIn(userName, password)
                if (restResponse.code == 1) {
                    mainViewModel.user.value = restResponse.data as User
                    requireActivity().onBackPressed()
                    Utils.showShortToast(requireContext(), "登陆成功")
                } else if (restResponse.code == 2) {
                    Utils.showShortToast(requireContext(), "用户名或密码错误")
                }
            }
        }
        // 注册
        binding.signupButton.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fullscreen).navigate(R.id.navigation_sign_up)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
