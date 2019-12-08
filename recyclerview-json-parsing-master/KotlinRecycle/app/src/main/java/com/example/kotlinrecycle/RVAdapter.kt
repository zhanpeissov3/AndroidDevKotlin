package com.example.kotlinrecycle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*



class RVAdapter : RecyclerView.Adapter<RVAdapter.MyViewHolder>(){
    private var post = ArrayList<Post>()
    private var listener: MyClickListener? = null

    fun setPost(post: ArrayList<Post>){
        this.post = post
        this.notifyDataSetChanged()
    }
    fun setListener(link: MyClickListener){
        this.listener = link
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return post.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(post[position])
    }
    inner  class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Post){

            itemView.userId.text = item.userId.toString()
            itemView.iD.text = item.id.toString()
            itemView.title.text = item.title
            itemView.body.text = item.body
        }
        init {
            itemView.setOnClickListener{
                listener?.onClick(post[adapterPosition])
            }
        }
    }

    interface MyClickListener{
        fun onClick(item: Post)
    }


}

