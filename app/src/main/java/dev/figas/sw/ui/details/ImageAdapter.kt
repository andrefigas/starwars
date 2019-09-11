package wiki.kotlin.starwars.ui.list

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.details_image_grid_item.view.*
import kotlinx.android.synthetic.main.person_item.view.*
import wiki.kotlin.starwars.R
import wiki.kotlin.starwars.ext.format
import wiki.kotlin.starwars.model.Person
import wiki.kotlin.starwars.model.StringArray
import java.lang.Exception

class ImageAdapter(val urls: StringArray) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    val TAG = "ImageAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.details_image_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = urls.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(urls.get(position))
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Picasso.get().cancelRequest(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        Picasso.get().load(holder.url).into(holder)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) , Target{

        var url : String = ""

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            itemView.grid_item_progress.visibility = View.VISIBLE
            itemView.grid_item_image.visibility = View.GONE
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            itemView.grid_item_progress.visibility = View.GONE
            itemView.grid_item_image.visibility = View.VISIBLE
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            itemView.grid_item_progress.visibility = View.GONE
            itemView.grid_item_image.visibility = View.VISIBLE
            itemView.grid_item_image.setImageBitmap(bitmap)
        }

        fun bind(url : String) {
            this.url = url
        }

    }

}