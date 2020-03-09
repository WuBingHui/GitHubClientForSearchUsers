package com.anthony.wu.github.client.search.user.koin


import com.anthony.wu.github.client.search.user.viewmodel.GitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { GitViewModel(get()) }

}