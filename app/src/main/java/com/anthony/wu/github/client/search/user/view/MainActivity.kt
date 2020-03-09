package com.anthony.wu.github.client.search.user.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.wu.github.client.search.user.R
import com.anthony.wu.github.client.search.user.adapter.UserAdapter
import com.anthony.wu.github.client.search.user.base.BaseActivity
import com.anthony.wu.github.client.search.user.base.Status
import com.anthony.wu.github.client.search.user.viewmodel.GitViewModel
import com.anthony.wu.github.client.search.user.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<GitViewModel>()

    private var userAdapter:UserAdapter? = null

    private var customLoadingDialog: CustomLoadingDialog? = null

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initScrollListener()

        initViewModel()

    }

    private fun initView() {

        customLoadingDialog = CustomLoadingDialog.newInstance()

        userAdapter = UserAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        usersRecyclerView.layoutManager = linearLayoutManager
        usersRecyclerView.adapter = userAdapter

        searchImg.setOnClickListener {

            if(searchUserEditText.text.toString().isNotEmpty()){

                page = 1

                userAdapter?.clearAll()

                customLoadingDialog?.show(supportFragmentManager, customLoadingDialog!!.tag)

                viewModel.getUserList(searchUserEditText.text.toString(),page)

            }else{

                Toast.makeText(this,getString(R.string.not_empty), Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun initScrollListener(){

        usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1)){

                    page++

                    viewModel.getUserList(searchUserEditText.text.toString(),page)

                }

            }
        })

    }

    @SuppressLint("ShowToast")
    private fun initViewModel() {
        viewModel.onUserList.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {

                    dto.data?.let {

                        if(it.items.isNotEmpty()){

                            userAdapter?.update(it.items)
                        }

                    }

                }
                Status.ERROR -> {
                    Toast.makeText(this,dto.message,Toast.LENGTH_SHORT).show()
                }
            }
            customLoadingDialog?.dismissAllowingStateLoss()
        })
    }

}
