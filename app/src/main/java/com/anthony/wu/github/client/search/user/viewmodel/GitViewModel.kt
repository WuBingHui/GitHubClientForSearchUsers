package com.anthony.wu.github.client.search.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anthony.wu.github.client.search.user.base.BaseViewModel
import com.anthony.wu.github.client.search.user.base.Resource
import com.anthony.wu.my.git.dto.response.*
import com.anthony.wu.github.client.search.user.extension.addTo
import com.anthony.wu.github.client.search.user.extension.ioToUi
import com.anthony.wu.github.client.search.user.model.GitModel

class GitViewModel( private val gitModel: GitModel) : BaseViewModel() {


    val onUserList: MutableLiveData<Resource<UserDto>> by lazy { MutableLiveData<Resource<UserDto>>() }


    fun getUserList(nameName:String,page:Int) {

        gitModel.getUserList(nameName,page).ioToUi().subscribe(
            { dto ->

                onUserList.value = Resource.success(dto)

            }, { t: Throwable? -> onUserList.value = Resource.error(t?.message, null) }

        ).addTo(compositeDisposable)

    }



}
