package wiki.kotlin.starwars.services

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import wiki.kotlin.starwars.BuildConfig
import wiki.kotlin.starwars.model.*
import java.io.IOException
import java.util.concurrent.TimeUnit

interface EndPoints {

    @GET("people/{code}/")
    fun providePeople(@Path("code") code: Int): Observable<Person>

    @GET
    fun provideSpecie(@Url url: String): Observable<Specie>

    @GET
    fun providePlanet(@Url url: String): Single<Planet>

    @GET
    fun provideVehicle(@Url url: String): Single<Vehicle>

    class Builder(var context: Context) {

        var url = ""

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun <T> build(callback: (Retrofit) -> T): T {
            val clientBuilder = OkHttpClient().newBuilder().addNetworkInterceptor(StethoInterceptor())

            if (BuildConfig.DEBUG) {
                val httpInterceptor = HttpLoggingInterceptor()
                httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                clientBuilder.addInterceptor(httpInterceptor)
                    .addInterceptor(
                        object : Interceptor {
                            @Throws(IOException::class)
                            override fun intercept(chain: Interceptor.Chain): Response {
                                val original = chain.request()
                                val request = original.newBuilder()
                                    .method(original.method(), original.body())
                                    .build()
                                return chain.proceed(request)
                            }
                        }
                    )
            }

            val cacheSize = 10L * 1024L * 1024L // 10 MB
            val cache = Cache(context.cacheDir, cacheSize)

            val networkCacheInterceptor = Interceptor { chain ->
                val response = chain.proceed(chain.request())

                var cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.HOURS)
                    .build()

                response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
            }

            return callback.invoke(
                Retrofit.Builder()
                    .addConverterFactory(createGsonConverter())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(url)
                    .client(
                        //Cache
                        clientBuilder.cache(cache).addNetworkInterceptor(networkCacheInterceptor).build()
                    )
                    .build()
            )
        }

        private fun createGsonConverter(): Converter.Factory {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(SpeciesArray::class.java, SpeciesDeserializer())
            gsonBuilder.registerTypeAdapter(VehiclesArray::class.java, VehiclesDeserializer())
            gsonBuilder.registerTypeAdapter(Planet::class.java, PlanetDeserializer())
            val gson = gsonBuilder.create()
            return GsonConverterFactory.create(gson)
        }

    }

}