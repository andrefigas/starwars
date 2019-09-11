package wiki.kotlin.starwars.ui.list

import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView

interface PersonsPresenterContract : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    fun injectView(view: PersonActivityView)

    val scrollListener: RecyclerView.OnScrollListener

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    fun onOptionsItemSelected(item: MenuItem): Boolean

    fun onCreateOptionsMenu(menu: Menu)
}