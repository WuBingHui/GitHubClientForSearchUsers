package com.anthony.wu.github.client.search.user.koin



import com.anthony.wu.github.client.search.user.service.GitService
import org.koin.dsl.module

val serviceModule = module {

    factory<GitService> { createService(get()) }

}