package wiki.kotlin.starwars.ui.list

import dagger.BindsInstance
import dagger.Component
import wiki.kotlin.starwars.App

@Component(modules = arrayOf(PersonsModule::class))
interface PersonsComponent {

    fun inject(activity: PersonsActivity)

    @Component.Builder
    interface Builder {

        fun build(): PersonsComponent
        @BindsInstance
        fun application(application: App): Builder
    }

}