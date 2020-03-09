package com.anthony.wu.github.client.search.user.model

import com.anthony.wu.github.client.search.user.extension.ioToUi
import com.anthony.wu.github.client.search.user.service.GitService

class GitModel(val service: GitService) {


    fun getUserList(userName:String,page:Int) = service.getUserList(userName,page).ioToUi()


}