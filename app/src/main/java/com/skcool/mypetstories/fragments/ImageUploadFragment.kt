package com.skcool.mypetstories.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Continuation
import com.google.firebase.storage.UploadTask
import com.skcool.mypetstories.R
import com.skcool.mypetstories.databinding.FragmentImageUploadBinding
import com.skcool.mypetstories.repository.FirebaseRepository
import com.skcool.mypetstories.utils.Common

class ImageUploadFragment : Fragment() {

    lateinit var binding: FragmentImageUploadBinding
    var userImageUri: Uri? = null
    var userPetImageUri: Uri? = null
    lateinit var repository: FirebaseRepository
    var userEmail:String? = null
    companion object {
        val USER_IMAGE: Int = 1022
        val PET_IMAGE: Int = 222

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_upload, container, false)

        userEmail = arguments?.getString("userName")
        repository = FirebaseRepository()
        binding.uploadImage.setOnClickListener {
            getImage(USER_IMAGE)

        }
        binding.uploadPetImage.setOnClickListener {
            getImage(PET_IMAGE)

        }

        binding.buttonDone.setOnClickListener {
            if (userImageUri != null && userPetImageUri != null) {

                val repo = repository.getStorageReference("profile_images").child(Common.user!!.email)
                val repoPet = repository.getStorageReference("pet_images").child("${Common.user!!}pet")

                val uploadUserTask = repo.putFile(userImageUri!!)
                val uploadPetTask = repoPet.putFile(userPetImageUri!!)
                var downloadUserImageUrl: String
                var downloadUserPetImageUrl: String

                uploadUserTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    repo.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userImageUri = Uri.parse(task.result.toString())

                        uploadPetTask.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            repoPet.downloadUrl
                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                userPetImageUri = Uri.parse(task.result.toString())
                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                                updateUserWithImage(userImageUri.toString(),
                                    userPetImageUri.toString())

                            } else {
                                // Handle failures
                                // ...
                            }


                        }
                    }

                }
            }

        }


        return binding.root


    }

    private fun updateUserWithImage(downloadUserImageUrl: String, downloadUserPetImageUrl: String) {

        val map = mapOf<String,String>("profile_image" to downloadUserImageUrl,"pet_image" to downloadUserPetImageUrl)

        val database = repository.getReference("Users").child(Common.user!!.uid).updateChildren(map)
            .addOnCompleteListener {
                findNavController().navigate(R.id.homeFragment)

            }


    }

    private fun getImage(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if (requestCode == USER_IMAGE && resultCode == Activity.RESULT_OK && data != null)
            userImageUri = data.data!!
        else if (userImageUri != null && requestCode == PET_IMAGE && resultCode == Activity.RESULT_OK && data != null)
            userPetImageUri = data.data!!
        else if (requestCode == USER_IMAGE && resultCode == Activity.RESULT_OK && data != null)
            userImageUri = data.data!!

        binding.uploadImage.setImageURI(userImageUri)
        binding.uploadPetImage.setImageURI(userPetImageUri)




    }


}