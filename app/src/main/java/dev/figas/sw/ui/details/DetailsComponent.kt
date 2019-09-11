package wiki.kotlin.starwars.ui.details

import dagger.BindsInstance
import dagger.Component
import wiki.kotlin.starwars.App

@Component(modules = arrayOf(DetailsModule::class))
interface DetailsComponent {

    fun inject(activity: DetailsActivity)

    @Component.Builder
    interface Builder {

        fun build(): DetailsComponent
        @BindsInstance
        fun application(application: App): Builder
    }

}