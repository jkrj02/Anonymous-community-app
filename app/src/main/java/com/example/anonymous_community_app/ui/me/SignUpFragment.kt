package com.example.anonymous_community_app.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.anonymous_community_app.data.User
import com.example.anonymous_community_app.databinding.FragmentSignUpBinding
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.signUpReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.signUpRegister.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        CoroutineScope(Dispatchers.Main).launch {
            val userName = binding.signUpUserName.text.toString()
            val password = binding.signUpPassword.text.toString()
            val password2 = binding.signUpPassword2.text.toString()
            val radioButton: RadioButton =
                requireView().findViewById(binding.signUpGender.checkedRadioButtonId)
            val gender = (radioButton.text.toString() == "男")
            if (userName != "" && password == password2 && password != "") {
                val user = User(userName = userName, password = password, gender = gender)
                val restResponse = Interface.signUp(user)
                if (restResponse.code == 1) {
                    Utils.showShortToast(requireContext(), "注册成功")
                    requireActivity().onBackPressed()
                } else if (restResponse.code == 2) {
                    Utils.showShortToast(requireContext(), "用户名已存在")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}