package wiki.kotlin.starwars.ui.list

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import wiki.kotlin.starwars.model.Person

interface PersonActivityView {

    fun injectPerson(person: Person)
    fun clearList()
    fun injectProgressing(progressing: Boolean)
    fun goToDetails(view: View, person: Person)
    fun removeScrollObserver(scrollListener: RecyclerView.OnScrollListener)
    fun goToAbout()
    fun inflateMenu(menu: Menu)

}