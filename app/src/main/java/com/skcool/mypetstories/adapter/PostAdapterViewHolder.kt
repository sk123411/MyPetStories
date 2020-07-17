package com.skcool.mypetstories.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skcool.mypetstories.model.Post
import com.squareup.picasso.Picasso

class PostAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){


    val content:TextView?=null
    val imageView:ImageView?=null
    val like:TextView?=null

    fun bind(post: Post){
        content?.text = post.content
        Picasso.get().load(post.uri).into(imageView)
        like?.text = post.like.toString()

    }






}