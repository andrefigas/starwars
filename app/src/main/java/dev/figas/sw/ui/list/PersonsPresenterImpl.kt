package wiki.kotlin.starwars.ui.list

import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import wiki.kotlin.starwars.App
import wiki.kotlin.starwars.R
import wiki.kotlin.starwars.ext.io

class PersonsPresenterImpl(val application: App) : PersonsPresenterContract {

    companion object {
        const val ITEMS_PER_REQUEST = 10
        const val OFFSET_TRIGGER_REQUEST = 5
    }

    lateinit var view: PersonActivityView
    var disposable: Disposable? = null

    override val scrollListener: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val itemsCount = recyclerView.adapter?.itemCount ?: 0
                val distanceToEnd = itemsCount - lm.findLastCompletelyVisibleItemPosition()
                if (distanceToEnd <= OFFSET_TRIGGER_REQUEST) {
                    appendData()
                }
            }
        }
    }

    override fun onResume() {
        appendData()
    }

    override fun onPause() {
        //clean data and cancel pending requests
        application.apiContract.resetNextPersonId()
        view.clearList()
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    private fun appendData() {
        //break if the request is not ended
        disposable?.let {
            if (!it.isDisposed) return
        }

        val begin = application.apiContract.nextPersonId
        disposable = application.apiContract
            .providePersons(begin, ITEMS_PER_REQUEST)
            .io()
            .doOnSubscribe {
                view.injectProgressing(true)
            }
            .doOnComplete {
                view.injectProgressing(false)
            }
            .doOnNext {
                view.injectPerson(it)
            }.doOnError {
                it.printStackTrace()
                view.removeScrollObserver(scrollListener)
            }
            .subscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                view.goToAbout()
                return true
            }
            else -> {
                return false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu) {
        view.inflateMenu(menu)
    }

    override fun injectView(view: PersonActivityView) {
        this.view = view
    }

}