package wiki.kotlin.starwars.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.figas.sw.ui.AboutActivity
import kotlinx.android.synthetic.main.activity_persons.*
import wiki.kotlin.starwars.App
import wiki.kotlin.starwars.Constants
import wiki.kotlin.starwars.R
import wiki.kotlin.starwars.model.Person
import wiki.kotlin.starwars.ui.details.DetailsActivity
import javax.inject.Inject


class PersonsActivity : AppCompatActivity(), PersonActivityView {

    @Inject
    lateinit var presenter: PersonsPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)

        DaggerPersonsComponent.builder().application(application as App).build().inject(this)
        presenter.injectView(this)
        lifecycle.addObserver(presenter)
    }

    override fun removeScrollObserver(scrollListener: RecyclerView.OnScrollListener) {
        persons_list.removeOnScrollListener(scrollListener)
    }

    override fun clearList() {
        persons_list.adapter = null
    }

    override fun injectProgressing(progressing: Boolean) {
        provideAdapter().progressing = progressing
    }

    override fun injectPerson(person: Person) {
        provideAdapter().injectItem(person)
    }

    private fun provideAdapter(): PersonAdapter {
        if (persons_list.adapter == null) {
            persons_list.layoutManager = LinearLayoutManager(this)
            persons_list.adapter = PersonAdapter(this)
            persons_list.clearOnScrollListeners()
            persons_list.addOnScrollListener(presenter.scrollListener)
        }

        return persons_list.adapter as PersonAdapter
    }

    override fun goToDetails(view: View, person: Person) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.IK_PERSON, person)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        presenter.onCreateOptionsMenu(menu)
        return true
    }

    override fun inflateMenu(menu: Menu) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //call super if event is not handled by presenter
        return !presenter.onOptionsItemSelected(item) &&
                super.onOptionsItemSelected(item)
    }

    override fun goToAbout() {
        startActivity(Intent(this, AboutActivity::class.java))
    }
}
