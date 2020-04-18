package com.example.hexadecimalcolor

import android.app.Application
import com.example.hexadecimalcolor.dagger.component.ApplicationComponent
import com.example.hexadecimalcolor.dagger.component.DaggerApplicationComponent
import com.example.hexadecimalcolor.dagger.module.RepositoryModule

class WordApplication: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .repositoryModule(RepositoryModule(applicationContext))
            .build()
    }
}