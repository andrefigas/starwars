package wiki.kotlin.starwars.ui.details

import android.content.Intent
import android.text.SpannableString
import androidx.annotation.ColorInt
import wiki.kotlin.starwars.model.StringArray

interface DetailsActivityView {

    fun injectWorldName(world: String)

    fun injectGender(gender: String)

    fun injectName(name: String)

    fun injectSkin(skin: String)

    fun injectVehicles(vehicles: String)

    fun getIntent() : Intent

    fun injectProgressing(progressing: Boolean)

    fun injectImages(images: StringArray)

    fun runOnUiThread(action : Runnable)
    fun injectCardInformationColor(@ColorInt color: Int)
    fun injectNoVehicles()
    fun injectAppendDataVisibility(progressing: Boolean)
    fun showErrorMessage()
}
