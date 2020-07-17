package com.skcool.mypetstories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skcool.mypetstories.R
import com.skcool.mypetstories.model.Post

class PostAdapter(val list:List<Post>):RecyclerView.Adapter<PostAdapterViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)

        return PostAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {

        val post = list.get(position);
        holder.bind(post)

    }


}