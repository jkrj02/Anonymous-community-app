package com.example.anonymous_community_app.ui.me

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anonymous_community_app.R

class MeLoginFragment : Fragment() {

    companion object {
        fun newInstance() = MeLoginFragment()
    }

    private lateinit var viewModel: MeLoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MeLoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}