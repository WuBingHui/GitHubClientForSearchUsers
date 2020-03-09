package com.anthony.wu.github.client.search.user.koin



import com.anthony.wu.github.client.search.user.model.GitModel
import org.koin.dsl.module

val repositoryModule = module {

    factory { GitModel(get()) }

}