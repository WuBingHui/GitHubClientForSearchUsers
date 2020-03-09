package com.anthony.wu.github.client.search.user.service


import com.anthony.wu.my.git.dto.response.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface GitService {


    /**
     * 取得搜尋的用戶列表
     */
    @GET("search/users")
    fun getUserList(@Query("q") userName:String,@Query("page") page:Int): Single<UserDto>



}


