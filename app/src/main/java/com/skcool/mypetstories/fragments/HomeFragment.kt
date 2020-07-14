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
import com.skcool.mypetstories.R
import com.skcool.mypetstories.databinding.FragmentHomeBinding
import com.skcool.mypetstories.model.Post
import com.skcool.mypetstories.repository.FirebaseRepository



class HomeFragment : Fragment() {
    companion object {
        val POST_IMAGE: Int = 1022

    }



    lateinit var binding: FragmentHomeBinding

    var imageUri:Uri? = null
    lateinit var repository: FirebaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {



        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        repository = FirebaseRepository()

        binding.lifecycleOwner = this

        binding.attachImage.setOnClickListener {

            getImage(POST_IMAGE)


        }


        binding.postButton.setOnClickListener {
            if (imageUri!=null) {
                val repository =
                    repository.getStorageReference("post_images").child("7Ut23mWHHV9dzvbEN9gtpet")
                val uploadPostTask = repository.putFile(imageUri!!)
                uploadPostTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    repository.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUserPostImageUrl = task.result.toString()
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                        submitPost();

                    } else {
                        // Handle failures
                        // ...
                    }

                }
            }

        }


        return binding.root
    }

    private fun submitPost() {

        val repo = repository.getReference("Posts")
        val post= Post(binding.postContent.text.toString(),imageUri.toString())
        
       // repo.child("sk123411").push().setValue(post)


    }

    private fun getImage(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    if (requestCode== POST_IMAGE&&resultCode== Activity.RESULT_OK&&data!=null)
       // binding.selectImageList
        imageUri = data.data!!
        binding.postImage.setImageURI(imageUri)


    }




}