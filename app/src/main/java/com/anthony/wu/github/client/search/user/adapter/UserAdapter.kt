package com.anthony.wu.github.client.search.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.wu.github.client.search.user.R
import com.anthony.wu.my.git.dto.response.Item
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


class UserAdapter(private val context: Context) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var userList = mutableListOf<Item>()


    fun update(userList: MutableList<Item>) {

        if(this.userList.isNotEmpty()){

            this.userList.addAll(userList)

        }else{

            this.userList = userList

        }

        notifyDataSetChanged()

    }

    fun clearAll(){

        this.userList.clear()

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return userList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = userList[position]

        Glide.with(context).load(data.avatar_url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .placeholder(R.drawable.git_icon)
            .into(holder.userIcon)

        holder.userName.text = data.login

    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userIcon = view.findViewById<ImageView>(R.id.userIcon)
        val userName = view.findViewById<TextView>(R.id.userName)


    }


}