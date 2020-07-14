package com.skcool.mypetstories.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.skcool.mypetstories.R
import com.skcool.mypetstories.databinding.FragmentLoginBinding
import com.skcool.mypetstories.viewmodel.LoginFragmentViewModel


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding
    lateinit var loginFragmentViewModel: LoginFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)
        loginFragmentViewModel = ViewModelProvider(this).get(LoginFragmentViewModel::class.java)
        binding.loginViewModel = loginFragmentViewModel


        binding.buttonLogin.setOnClickListener {
            val bundle:Bundle= bundleOf("email" to binding.editEmail.text.toString())

            it.findNavController().navigate(R.id.homeFragment,bundle)
        }
        binding.buttonSignup.setOnClickListener {
            val bundle:Bundle= bundleOf("title" to "Signup")

            it.findNavController().navigate(R.id.signUpFragment,bundle)
        }



        return binding.root
    }



}