package wiki.kotlin.starwars.ui.details

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

interface DetailsPresenterContract : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    fun injectView(view: DetailsActivityView)

}