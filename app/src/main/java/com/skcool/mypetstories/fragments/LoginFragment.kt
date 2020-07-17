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
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.skcool.mypetstories.R
import com.skcool.mypetstories.databinding.FragmentLoginBinding
import com.skcool.mypetstories.model.User
import com.skcool.mypetstories.repository.FirebaseRepository
import com.skcool.mypetstories.utils.Common
import com.skcool.mypetstories.viewmodel.LoginFragmentViewModel
import io.paperdb.Paper


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding
    lateinit var loginFragmentViewModel: LoginFragmentViewModel
    lateinit var repository: FirebaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)
        Paper.init(context)

        loginFragmentViewModel = ViewModelProvider(this).get(LoginFragmentViewModel::class.java)
        binding.loginViewModel = loginFragmentViewModel
        repository = FirebaseRepository()

            binding.buttonLogin.setOnClickListener {
            val bundle:Bundle= bundleOf("email" to binding.editEmail.text.toString())


            val read: User = Paper.book().read<User>(Common.USER_UID_SAVED_KEY)
            val usersRef = repository.getReference("Users")
                .child("${read.uid}")
            usersRef.addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
              //      TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {

                    if (p0!=null){
                        findNavController().navigate(R.id.homeFragment)

                    }



                }


            })

            it.findNavController().navigate(R.id.homeFragment,bundle)


        }
        binding.buttonSignup.setOnClickListener {
            val bundle:Bundle= bundleOf("title" to "Signup")

            it.findNavController().navigate(R.id.signUpFragment,bundle)
        }



        return binding.root
    }



}