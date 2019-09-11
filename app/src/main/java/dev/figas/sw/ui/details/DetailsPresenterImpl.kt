package wiki.kotlin.starwars.ui.details

import android.content.Intent
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.disposables.Disposable
import wiki.kotlin.starwars.App
import wiki.kotlin.starwars.Constants
import wiki.kotlin.starwars.ext.format
import wiki.kotlin.starwars.ext.io
import wiki.kotlin.starwars.ext.zipPair
import wiki.kotlin.starwars.model.Person


class DetailsPresenterImpl(val application: App) : DetailsPresenterContract {

    lateinit var view: DetailsActivityView

    lateinit var disposable: Disposable

    override fun onCreate() {
        parseIntent(view.getIntent());
    }

    override fun onDestroy() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun parseIntent(intent: Intent) {
        intent.getParcelableExtra<Person>(Constants.IK_PERSON)?.let {
            view.injectGender(it.gender)
            view.injectName(it.name)
            view.injectSkin(it.skin)
            completePerson(it)
        }

    }

    private fun completePerson(person: Person) {
        //getting color and complete profile
        disposable = application.apiContract.loadColor(person.skin)
        { url: String, target: Target ->
            view.runOnUiThread(Runnable { Picasso.get().load(url).into(target) })
        }.zipPair(
            application.apiContract.completePerson(person)
        ).doOnSubscribe {
            view.injectProgressing(true)
        }
            .doFinally {
                view.injectProgressing(false)

            }.io().subscribe(
                {
                    val color = it.first
                    val person = it.second

                    view.injectCardInformationColor(color)

                    if (person.vehicles.isEmpty()) {
                        view.injectNoVehicles()
                    } else {
                        view.injectVehicles(
                            StringBuilder()
                                .format(person.vehicles, {
                                    it.name
                                }).toString()
                        )
                    }

                    view.injectWorldName(person.homeWorld.name)
                    view.injectImages(person.images)

                    view.injectAppendDataVisibility(true);

                }
                , {
                    it.printStackTrace()
                    view.showErrorMessage()
                }
            )

    }

    override fun injectView(view: DetailsActivityView) {
        this.view = view
    }

}