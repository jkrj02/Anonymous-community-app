package com.example.anonymous_community_app.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentLoginBinding
import com.example.anonymous_community_app.databinding.FragmentMeBinding
import com.example.anonymous_community_app.ui.me.MeViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MeViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.loginReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }
//        binding.loginButton.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(p0: View?) {
//                if (binding.loginAccount.equals("123456") && binding.loginPassword.equals("123456")) {
//                    mainViewModel.login = true
//                    Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show()
//                    requireActivity().onBackPressed()
//                } else {
//                    Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
        binding.loginButton.setOnClickListener {
            if (binding.loginAccount.text.toString() == "123456" && binding.loginPassword.text.toString() == "123456") {
                mainViewModel.login = true
                Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}