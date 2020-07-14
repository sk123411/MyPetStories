package com.skcool.mypetstories.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.skcool.mypetstories.R
import com.skcool.mypetstories.databinding.FragmentSignUpBinding
import com.skcool.mypetstories.model.User
import com.skcool.mypetstories.repository.FirebaseRepository
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var repository: FirebaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)



        //  (requireActivity() as MainActivity).title = "Sign up"
        repository = FirebaseRepository()
        val usersReference = repository.getReference("Users")
        binding.buttonSignSignup.setOnClickListener {
            Toast.makeText(context,"success",Toast.LENGTH_SHORT).show()


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding.editSignEmail.text.toString(),
                binding.editSignPass.text.toString()

            ).addOnSuccessListener {

                val user = User(editSignEmail.text.toString(),editSignName.text.toString(),editSignUpPet.text.toString(),
                editSignBio.text.toString())
                usersReference.child(editSignEmail.text.toString()).setValue(user).addOnSuccessListener {
                    Toast.makeText(context,"success",Toast.LENGTH_SHORT).show()
                    val bundle = bundleOf("userEmail" to editSignEmail.text.toString())

                    findNavController().navigate(R.id.imageUploadFragment,bundle)
                }
            }


        }

        return binding.root

    }




}