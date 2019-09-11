package wiki.kotlin.starwars.ui.list

import dagger.Module
import dagger.Provides
import wiki.kotlin.starwars.App

@Module
class PersonsModule {

    @Provides
    fun providePersonsPresenter(app: App): PersonsPresenterContract = PersonsPresenterImpl(app)

}