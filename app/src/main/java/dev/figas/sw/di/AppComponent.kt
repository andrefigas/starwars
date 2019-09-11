package wiki.kotlin.starwars.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import wiki.kotlin.starwars.App
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApiModule::class))
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun context(context: Context): Builder
    }

}