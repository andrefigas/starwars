package wiki.kotlin.starwars.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_details.*
import wiki.kotlin.starwars.App
import wiki.kotlin.starwars.R
import wiki.kotlin.starwars.model.StringArray
import wiki.kotlin.starwars.ui.list.ImageAdapter
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), DetailsActivityView {

    @Inject
    lateinit var presenter: DetailsPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        DaggerDetailsComponent.builder().application(application as App).build().inject(this)
        presenter.injectView(this)
        lifecycle.addObserver(presenter)
    }

    override fun injectWorldName(world: String) {
        detail_homeworld.setText(getString(R.string.homeworld_details,world))
    }

    override fun injectGender(gender: String) {
        detail_gender.setText(getString(R.string.gender_details,gender))
    }

    override fun injectName(name: String) {
        detail_name.setText(getString(R.string.name_details,name))
    }

    override fun injectSkin(skin: String) {
        detail_skin.setText(getString(R.string.skin_color_details, skin))
    }

    override fun injectCardInformationColor(@ColorInt color: Int) {
        details_image_grid_container.setCardBackgroundColor(color)
    }

    override fun injectVehicles(vehicles: String) {
        detail_vehicles.setText(getString(R.string.vehicles_details, vehicles))
    }

    override fun injectNoVehicles() {
        detail_vehicles.setText(getString(R.string.vehicles_details, getString(R.string.none)))
    }

    override fun injectProgressing(progressing: Boolean) {
        detail_progress.visibility = if (progressing) View.VISIBLE else View.GONE
    }

    override fun injectAppendDataVisibility(visible: Boolean){
        details_image_grid_container.visibility = if (visible) View.VISIBLE else View.GONE
        details_information.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun injectImages(images: StringArray) {
        details_image_grid.layoutManager = GridLayoutManager(this, 3)
        details_image_grid.adapter = ImageAdapter(images)
    }

    override fun showErrorMessage(){
        Toast.makeText(this,R.string.api_generic_error, Toast.LENGTH_LONG).show()
    }

}
