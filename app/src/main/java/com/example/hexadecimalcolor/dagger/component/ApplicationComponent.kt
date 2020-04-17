package com.example.hexadecimalcolor.dagger.component

import com.example.hexadecimalcolor.common.network.WordClient
import com.example.hexadecimalcolor.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun getWordClient(): WordClient
}