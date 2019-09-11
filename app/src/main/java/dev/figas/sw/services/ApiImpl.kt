package wiki.kotlin.starwars.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.SparseArray
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.Single
import wiki.kotlin.starwars.BuildConfig
import wiki.kotlin.starwars.R
import wiki.kotlin.starwars.ext.io
import wiki.kotlin.starwars.ext.zip
import wiki.kotlin.starwars.model.*


class ApiImpl(val context: Context) : ApiContract {

    val TAG = "ApiImpl"

    val persons = SparseArray<Person>()

    //Palette have a problem with white detection
    val colorsMap = mutableMapOf("white" to Color.WHITE)

    val endPoints: EndPoints by lazy {
        EndPoints.Builder(context).url(BuildConfig.API).build {
            it.create(EndPoints::class.java)
        }
    }

    val googleSearch: GoogleSearch by lazy {
        EndPoints.Builder(context).url(BuildConfig.GOOGLE_API).build {
            it.create(GoogleSearch::class.java)
        }
    }

    override var nextPersonId = 1

    override fun resetNextPersonId() {
        nextPersonId = 0
    }

    override fun provideVehicle(url: String): Single<Vehicle> {
        return endPoints.provideVehicle(url)
    }

    override fun provideSpecie(url: String): Observable<Specie> {
        return endPoints.provideSpecie(url)
    }

    override fun providePlanet(url: String): Single<Planet> {
        return endPoints.providePlanet(url)
    }

    fun providePerson(code: Int) =
        if (persons.indexOfKey(code) >= 0) {
            //get from memory
            Observable.just(persons.get(code))
        } else {
            //retrieve from api
            endPoints.providePeople(code).loadSpecies()
        }

    override fun providePersons(begin: Int, length: Int): Observable<Person> {
        Log.d(TAG, "providePersons begin=$begin , end=${length + begin}");
        return Observable.create<Person> {
            val emitter = it
            Observable.zip(
                (begin until begin + length).map {
                    val index = it
                    providePerson(it)
                        .onErrorResumeNext(Observable.just(Person.ERROR))
                        .map {
                            it.id = index
                            return@map it
                        }
                }) {
                it.map {
                    var person = it as Person
                    //store on memory
                    persons.put(person.id as Int?, person)
                    nextPersonId = Math.max(person.id, nextPersonId)
                    if (person != Person.ERROR) {
                        emitter.onNext(person)
                    } else {
                        println("person ${person.id} was not fount")
                    }

                    return@map person
                }
            }.doOnComplete {
                nextPersonId++
                emitter.onComplete()
            }.io().subscribe()
        }

    }

    override fun completePerson(person: Person): Single<Person> {
        return Single.just(person)
            .loadVehicles()
            .loadHomeWorld()
            .loadImages()
    }

    private fun loadVehicles(vehicles: VehiclesArray): Single<VehiclesArray> {
        return vehicles.map {
            if (it.isCompleted()) Single.just(it)
            else provideVehicle(it.url)
        }.zip {
            VehiclesArray(it)
        };
    }

    //appending loadVehicles request
    private fun Single<Person>.loadVehicles(): Single<Person> {
        return flatMap {
            val person = it;

            if (person.vehicles.isEmpty()) {
                return@flatMap Single.just(person)
            }

            loadVehicles(person.vehicles).map {
                person.vehicles = it
                persons.put(null, person)
                return@map person
            }
        }
    }

    private fun loadSpecies(species: SpeciesArray): Observable<SpeciesArray> {
        return species.map {
            //if we have just url, we will request another information about the specie
            if (it.isCompleted()) Observable.just(it)
            else provideSpecie(it.url)
        }.zip {
            SpeciesArray(it)
        };
    }

    //appending loadSpecies request
    private fun Observable<Person>.loadSpecies(): Observable<Person> {
        return flatMap {
            val person = it;
            loadSpecies(person.species).map {
                person.species = it
                return@map person
            }
        }
    }

    //appending loadPlanet request
    private fun Single<Person>.loadHomeWorld(): Single<Person> {
        return flatMap {
            val person = it;
            //if we have just url, we will request another information about the planet
            return@flatMap if (person.homeWorld.isCompleted()) {
                Single.just(person)
            } else {
                endPoints.providePlanet(person.homeWorld.url).map {
                    person.homeWorld = it
                    persons.put(null, person)
                    return@map person
                }
            }
        }
    }

    private fun provideSearchResults(person: Person): Single<StringArray> {
        if (person.hasImages()) {
            return Single.just(person.images)
        }

        return googleSearch.provideGoogleImages(
            context.getString(R.string.google_key),
            context.getString(R.string.google_ctx),
            SEARCH_TYPE_IMAGE,
            SEARCH_IMAGES_BY_GALLERY,
            person.name
        ).io().map {
            person.images = StringArray(it.items.map { it.image.thumbnailLink })
            persons.put(person.id as Int?, person)
            return@map person.images

        }
    }

    //appending load images
    private fun Single<Person>.loadImages(): Single<Person> {
        return flatMap {
            val person = it;
            provideSearchResults(person).map {
                person.images = it
                return@map person
            }
        }
    }

    override fun loadColor(colorName: String, callback: (String, Target) -> Unit): Single<Int> {
        //checking if we have the color on memory
        if (colorsMap.containsKey(colorName)) {
            return Single.just(colorsMap.get(colorName))
        }

        //getting url image searching "color * <coloName>"
        return googleSearch.provideGoogleImages(
            context.getString(R.string.google_key),
            context.getString(R.string.google_ctx),
            SEARCH_TYPE_IMAGE,
            1,
            SEARCH_FILE_TYPE_JPG,
            "color $colorName"
        ).flatMap {
            //url of the first result from google
            val url = it.items.first().link
            Single.create<Int> {
                val emitter = it
                val target: Target = object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        emitter.onError(Throwable(e))
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                        Palette.Builder(bitmap).generate {
                            val color = it?.getDominantColor(Color.BLACK) as Int
                            //save color on memory
                            colorsMap.put(colorName, color)
                            emitter.onSuccess(color)
                        }
                    }

                }

                //extract the main color of the image using palette
                callback.invoke(url, target)
            }
        }.io()
    }

    //utilities

    private fun SparseArray<Person>.put(key: Int? = null, item: Person): Boolean {
        if (key != null) {
            put(key, item)
            return true;
        }

        val index = indexOfKey(item.id)
        if (index >= 0) {
            setValueAt(index, item)
            return true
        }

        return false
    }
}


