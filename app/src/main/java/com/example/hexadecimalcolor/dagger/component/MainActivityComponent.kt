package com.example.hexadecimalcolor.dagger.component

import com.example.hexadecimalcolor.views.MainActivity
import com.example.hexadecimalcolor.dagger.module.WordViewModelModule
import com.example.hexadecimalcolor.dagger.scope.MainActivityScope
import dagger.Component

@MainActivityScope
@Component(modules = [WordViewModelModule::class], dependencies = [ApplicationComponent::class])
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)
}