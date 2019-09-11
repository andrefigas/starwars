package wiki.kotlin.starwars.ui.details

import dagger.Module
import dagger.Provides
import wiki.kotlin.starwars.App

@Module
class DetailsModule {

    @Provides
    fun providePersonsPresenter(app: App): DetailsPresenterContract = DetailsPresenterImpl(app)

}