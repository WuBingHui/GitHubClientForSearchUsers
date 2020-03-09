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

    private val userCallBack = BehaviorSubject.create<String>()

     fun getUserCallBack(): Observable<String> = userCallBack

    companion object {
        private const val USER_NAME = "userName"


    }

    private var userList = listOf<Item>()

    fun update(userList: List<Item>) {

        this.userList = userList

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

        holder.itemView.setOnClickListener {

            userCallBack.onNext(data.login)

        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userIcon = view.findViewById<ImageView>(R.id.userIcon)
        val userName = view.findViewById<TextView>(R.id.userName)


    }


}