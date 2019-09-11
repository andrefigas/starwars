package wiki.kotlin.starwars.services

import dev.figas.sw.model.google.GoogleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val SEARCH_TYPE_IMAGE = "image"
const val SEARCH_IMAGES_BY_GALLERY = 9
const val SEARCH_FILE_TYPE_JPG = "jpg"

interface GoogleSearch {

    @GET("/customsearch/v1")
    fun provideGoogleImages(
        @Query("key") key: String,
        @Query("cx") cx: String,
        @Query("searchType") searchType: String,
        @Query("num") results: Int,
        @Query("q") query: String
    ): Single<GoogleResponse>

    @GET("/customsearch/v1")
    fun provideGoogleImages(
        @Query("key") key: String,
        @Query("cx") cx: String,
        @Query("searchType") searchType: String,
        @Query("num") results: Int,
        @Query("fileType") fileType: String,
        @Query("q") query: String
    ): Single<GoogleResponse>

}