package com.example.hexadecimalcolor.dagger.component

import com.example.hexadecimalcolor.common.network.WordClient
import com.example.hexadecimalcolor.dagger.module.NetworkModule
import com.example.hexadecimalcolor.dagger.module.RepositoryModule
import com.example.hexadecimalcolor.repositories.RemoteRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun getWordClient(): WordClient

    fun getRepository(): RemoteRepository
}