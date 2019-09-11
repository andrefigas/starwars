package wiki.kotlin.starwars

import android.app.Application
import wiki.kotlin.starwars.di.AppComponent
import wiki.kotlin.starwars.di.DaggerAppComponent
import wiki.kotlin.starwars.services.ApiContract
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var apiContract: ApiContract

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}