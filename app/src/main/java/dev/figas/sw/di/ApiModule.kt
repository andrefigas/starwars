package wiki.kotlin.starwars.di

import android.content.Context
import dagger.Module
import dagger.Provides
import wiki.kotlin.starwars.services.ApiContract
import wiki.kotlin.starwars.services.ApiImpl
import javax.inject.Singleton

@Module
class ApiModule{

    @Provides
    @Singleton
    fun provideApi(context : Context): ApiContract = ApiImpl(context)

}