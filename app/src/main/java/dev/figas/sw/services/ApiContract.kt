package wiki.kotlin.starwars.services

import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import retrofit2.http.Url
import wiki.kotlin.starwars.model.Person
import wiki.kotlin.starwars.model.Planet
import wiki.kotlin.starwars.model.Specie
import wiki.kotlin.starwars.model.Vehicle

interface ApiContract {

    var nextPersonId: Int
    fun resetNextPersonId()
    fun provideVehicle(@Url url : String) : Single<Vehicle>
    fun providePlanet(@Url url : String) : Single<Planet>
    fun completePerson(person : Person) : Single<Person>
    fun providePersons(begin: Int, length: Int): Observable<Person>
    fun provideSpecie(url: String): Observable<Specie>
    fun loadColor(colorName: String, callback: (String, Target) -> Unit): Single<Int>
}