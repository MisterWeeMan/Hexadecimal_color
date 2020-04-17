package com.example.hexadecimalcolor

import android.app.Application
import com.example.hexadecimalcolor.dagger.component.ApplicationComponent
import com.example.hexadecimalcolor.dagger.component.DaggerApplicationComponent

class WordApplication: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }
}