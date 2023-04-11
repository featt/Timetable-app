package ru.shiryaev.data

import android.app.Application
import ru.shiryaev.data.di.AppComponent
import ru.shiryaev.data.di.DaggerAppComponent
import ru.shiryaev.data.di.modules.AppModule

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()

        sAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        private lateinit var sAppComponent: AppComponent

        fun getAppComponent() = sAppComponent
    }
}