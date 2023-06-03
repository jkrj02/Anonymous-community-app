package com.example.anonymous_community_app.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import com.example.anonymous_community_app.MainViewModel
import com.example.anonymous_community_app.R
import com.example.anonymous_community_app.databinding.FragmentMeModifyBinding
import com.example.anonymous_community_app.server.Interface
import com.example.anonymous_community_app.server.ServerUtils
import com.example.anonymous_community_app.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeModifyFragment : Fragment() {
    private var _binding: FragmentMeModifyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeModifyBinding.inflate(inflater, container, false)

        binding.meModifyReturn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.meModifyConfirm.setOnClickListener {
            modifyMe()
        }

        return binding.root
    }

    private fun modifyMe() {
        CoroutineScope(Dispatchers.Main).launch {
            val user = viewModel.user.value!!
            val userName = binding.meModifyUserName.text.toString()
            if (userName != "") user.userName = userName
            val password = binding.meModifyPassword.text.toString()
            val password2 = binding.meModifyPassword2.text.toString()
            if (password == password2 && password != "") user.password = password
            val radioButton: RadioButton = requireView().findViewById(binding.meModifyGender.checkedRadioButtonId)
            user.gender = (radioButton.text.toString() == "男")
            val restResponse = Interface.updateMe(user)
            if (restResponse.code == 1) {
                viewModel.user.value = user
                Utils.showShortToast(requireContext(), "修改成功")
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}