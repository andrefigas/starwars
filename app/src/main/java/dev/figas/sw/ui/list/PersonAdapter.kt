package wiki.kotlin.starwars.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.person_item.view.*
import wiki.kotlin.starwars.R
import wiki.kotlin.starwars.ext.format
import wiki.kotlin.starwars.model.Person

class PersonAdapter(val view: PersonActivityView) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    val TAG = "PersonAdapter"

    val items = ArrayList<Person>()
    var progressing = false
        set(progressing) {
            field = progressing
            notifyDataSetChanged()
        }

    fun injectItem(item: Person) {
        Log.d(TAG, "injectItem id=${item.id} , name=${item.name}");
        this.items.add(item)
        notifyDataSetChanged()
    }

    /**
     * @return layout id according view type
     */
    override fun getItemViewType(position: Int): Int {
        return if (position >= this.items.size) R.layout.person_progress else R.layout.person_item;
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(type, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = if (progressing) items.size + 1 else items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.person_progress) {
            return
        }

        val person = items.get(position)
        Log.d(TAG, "onBindViewHolder position=$position, person=${person.name}");
        holder.bind(person)
        holder.itemView.setOnClickListener {
            view.goToDetails(it, person)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(person: Person) {
            itemView.person_item_name.text = itemView.context.getString(R.string.name_details, person.name)
            itemView.person_item_species.text = itemView.context.getString(
                R.string.species_details, StringBuilder()
                    .format(person.species, {
                        it.name
                    }).toString()
            )
            itemView.person_item_vehicles_count.text = itemView.context.resources.getString(
                R.string.vehicles_count,
                person.vehicles.size
            )
            itemView.person_item_code.setText("${person.id}")
        }

    }

}