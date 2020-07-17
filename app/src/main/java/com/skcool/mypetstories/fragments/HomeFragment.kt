package com.skcool.mypetstories.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseListOptions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.skcool.mypetstories.R
import com.skcool.mypetstories.adapter.PostAdapter
import com.skcool.mypetstories.adapter.PostAdapterViewHolder
import com.skcool.mypetstories.databinding.FragmentHomeBinding
import com.skcool.mypetstories.model.Post
import com.skcool.mypetstories.model.User
import com.skcool.mypetstories.repository.FirebaseRepository
import com.skcool.mypetstories.utils.Common
import io.paperdb.Paper
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    companion object {
        val POST_IMAGE: Int = 1022

    }


    lateinit var binding: FragmentHomeBinding


    var imageUri: Uri? = null
    val postlist = ArrayList<Post>()
    lateinit var repository: FirebaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        repository = FirebaseRepository()
        binding.lifecycleOwner = this

        Paper.init(context)
        val query: Query = FirebaseDatabase.getInstance().getReference("Posts")

        query.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                //              TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//                TODO("Not yet implemented")


            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                //              TODO("Not yet implemented")


                val post: Post? = p0.getValue(Post::class.java)

                postlist.add(post!!)
                setUpAdapter(postlist)

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                //            TODO("Not yet implemented")
            }

        })










        binding.attachImage.setOnClickListener {

            getImage(POST_IMAGE)


        }



        binding.postButton.setOnClickListener {
            if (imageUri != null) {
                val date = Date()
                val getUniqueDate = date.time

                val read: User = Paper.book().read(Common.USER_UID_SAVED_KEY)

                val repository =
                    repository.getStorageReference("post_images").child("${read.uid}").child(
                        "${getUniqueDate}"
                    )
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
                        submitPost(downloadUserPostImageUrl)

                    } else {
                        // Handle failures
                        // ...
                    }

                }
            }

        }


        return binding.root
    }

    private fun submitPost(srt: String) {

        val repo = repository.getReference("Posts")
        val post = Post()
        post.content = binding.postContent.text.toString()
        post.uri = srt
        repo.child(Common.user!!.uid).push().setValue(post)

    }

    private fun getImage(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == POST_IMAGE && resultCode == Activity.RESULT_OK && data != null)
        // binding.selectImageList
            imageUri = data.data!!
        binding.postImage.setImageURI(imageUri)


    }


    fun setUpAdapter(list: List<Post>) {

        Log.d("INFOOO", list.toString())
        val postAdapter = PostAdapter(list)
        binding.homeRecyclerView.adapter = postAdapter
        postAdapter.notifyDataSetChanged()


    }


}