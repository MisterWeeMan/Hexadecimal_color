package com.example.hexadecimalcolor.dagger.component

import com.example.hexadecimalcolor.MainActivity
import com.example.hexadecimalcolor.dagger.scope.MainActivityScope
import dagger.Component

@MainActivityScope
@Component(dependencies = [ApplicationComponent::class])
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)
}